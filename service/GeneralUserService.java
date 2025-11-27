package com.test.ygdd.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import com.test.ygdd.data.Cart;
import com.test.ygdd.data.Data;
import com.test.ygdd.data.MenuInfo;
import com.test.ygdd.data.MyReview;
import com.test.ygdd.data.Order;
import com.test.ygdd.data.OrderInfo;
import com.test.ygdd.data.Review;
import com.test.ygdd.data.SelectStoreList;
import com.test.ygdd.data.UserCarts;
import com.test.ygdd.ui.CommonUI;
import com.test.ygdd.ui.DeleteUI;
import com.test.ygdd.ui.GeneralUserUI;
import com.test.ygdd.ui.OrderUI;
import com.test.ygdd.ui.PayUI;
import com.test.ygdd.user.GeneralUser;
import com.test.ygdd.user.LoginInfo;
import com.test.ygdd.user.StoreUser;
import com.test.ygdd.user.UserManager;
import com.test.ygdd.user.UserService;

/**
 * 일반유저 서비스 관련. GeneralUserManager 인터페이스를 상속 받는 클래스
 */
public class GeneralUserService implements GeneralUserManager {

	private StoreUser selectedStore;

	/**
	 * 장바구니에 담은 리스트를 출력하고, [주문하기 및 삭제하기] 메뉴 중 하나를 선택 받는 메서드
	 */
	@Override
	public void getCart(int callLocation, StoreUser s) {

		selectedStore = s;

		ArrayList<UserCarts> cartlist = new ArrayList<UserCarts>();
		ArrayList<String> strCartlist = new ArrayList<String>();

		int[] index = { 0 };
		Data.cartList.stream().filter(cart -> cart.getGeneralUserCode().equals(LoginInfo.getGeneralUserCode()))
				.forEach(cart -> {
					Data.menuInfoList.stream().filter(menu -> menu.getStoreUserCode().equals(cart.getStoreUserCode()))
							.filter(menu -> menu.getFoodName().equals(cart.getFoodName())).forEach(c -> {
								UserCarts uc = new UserCarts(index[0], c.getStoreUserCode(), c.getStoreName(),
										c.getFoodName(), c.getPrice());
								cartlist.add(uc);
								index[0]++;
					});
			});

		int totalPrice = 0;

		if (!cartlist.isEmpty()) {
			strCartlist.add(String.format("[ %s ]", cartlist.get(0).getStoreName()));

			int index2 = 1;
			for (UserCarts uc : cartlist) {
				strCartlist.add(String.format("%d. %s", index2, uc.getFoodName()));

				totalPrice = cartlist.stream().mapToInt(p -> p.getPrice()).sum();
				index2++;
			}
		}

		strCartlist.add("");
		strCartlist.add("총 금액: " + String.format("%,d원", totalPrice));

		OrderUI.optCart(strCartlist);

		Scanner scan = new Scanner(System.in);

		boolean loop = true;
		while (loop) {

			String sel = scan.nextLine();

			if (sel.equals("1")) {
				// 장바구니 주문하기
				doOrder();
				loop = false;

			} else if (sel.equals("2")) {
				// 장바구니 삭제하기
				doDelete(cartlist, callLocation);
				loop = false;

			} else if (sel.equals("0")) {
				// 뒤로가기
				if (callLocation == 1) {
					UserManager um = new UserService();
					um.generalUserMenu();
				} else {
					StoreInfoManager sim = new StoreInfoService();
					sim.getMenu(selectedStore);
				}

				loop = false;

			} else {
				System.out.println("다시 입력해 주세요.");
				continue;
			}
		}
	}

	/**
	 * 장바구니에 담은 메뉴를 출력하고, [개별 삭제 또는 전체 삭제] 메뉴 중 하나를 선택 받는 메서드
	 */
	@Override
	public void doDelete(ArrayList<UserCarts> cartlist, int callLocation) {

		// 삭제하기
		int count = 1;

		ArrayList<String> strCartlist = new ArrayList<String>();

		if (!cartlist.isEmpty()) {
			strCartlist.add(String.format("[ %s ]", cartlist.get(0).getStoreName()));

			int index = 1;
			for (UserCarts uc : cartlist) {
				strCartlist.add(String.format("%d. %s | %s", index, uc.getFoodName(), uc.getPrice()));
				index++;
			}
		} else {
			strCartlist.add("* 삭제할 음식이 없습니다.");
			count = 0;
		}

		OrderUI.optCartDelete(strCartlist, count);

		if (strCartlist.isEmpty()) {
			CommonUI.pause();

			getCart(2, selectedStore);
			return;
		}

		Scanner scan = new Scanner(System.in);

		boolean loop = true;
		while (loop) {
			String strSel = scan.nextLine().toUpperCase();

			boolean result = strSel.matches("[-+]?\\d*\\.?\\d+");

			if (result == true) {
				int sel = Integer.parseInt(strSel);

				if (sel >= 1 && sel <= cartlist.size()) {
					// 단일 삭제
					confirmDeleteItem(cartlist, cartlist.get(sel - 1), callLocation);
					loop = false;
				} else if (sel == 0) {
					// 뒤로가기
					getCart(callLocation, selectedStore);
					loop = false;
				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			} else if (strSel.equals("D")) {
				// 전체 삭제
				Data.cartList.removeIf(ct -> ct.getGeneralUserCode().equals(LoginInfo.getGeneralUserCode()));

				Data.saveCart();

				System.out.println();
				System.out.println("* 전체 삭제되었습니다. 장바구니로 이동합니다");

				getCart(callLocation, selectedStore);
				loop = false;

			} else {
				System.out.println("다시 입력해 주세요.");
				continue;
			}
		}
	}

	/**
	 * 장바구니에서 삭제하기 위해 선택한 메뉴를 삭제할건지 재확인하는 메서드
	 * @param cartlist
	 * @param selected
	 * @param callLocation
	 */
	private void confirmDeleteItem(ArrayList<UserCarts> cartlist, UserCarts selected, int callLocation) {

		Scanner scan = new Scanner(System.in);

		CommonUI.header("장바구니 삭제 확인");

		String foodName = selected.getFoodName();
		int price = selected.getPrice();

		System.out.println("선택한 메뉴: " + foodName + " | " + String.format("%,d원", price));
		System.out.println();

		DeleteUI.deleteAgreeFooter();

		boolean loop = true;
		while (loop) {
			String sel = scan.nextLine().toUpperCase();

			if (sel.equals("Y")) {

				Iterator<Cart> iterator = Data.cartList.iterator();

				while (iterator.hasNext()) {
					Cart item = iterator.next();
					if (item.getGeneralUserCode().equals(LoginInfo.getGeneralUserCode())
							&& item.getStoreUserCode().equals(selected.getStoreCode())
							&& item.getFoodName().equals(foodName)) {
						iterator.remove();
						break;
					}
				}

				Data.saveCart();

				Iterator<UserCarts> iterator2 = cartlist.iterator();

				while (iterator2.hasNext()) {
					UserCarts item = iterator2.next();
					if (item.getFoodName().equals(foodName)) {
						iterator2.remove();
						break;
					}
				}

				System.out.println("* 삭제 되었습니다. 장바구니로 돌아갑니다.");
				CommonUI.pause();

				doDelete(cartlist, callLocation);

				loop = false;

			} else if (sel.equals("N")) {
				doDelete(cartlist, callLocation);
				loop = false;

			} else {
				System.out.println("Y 또는 N으로 다시 입력해주세요.");
			}
		}
	}

	/**
	 * 장바구니에 담은 메뉴에 따른 메뉴와 금액을 보여주고, 담은 메뉴를 주문한 뒤 결제창으로 이동하는 메서드
	 */
	@Override
	public void doOrder() {

		// 주문하기
		ArrayList<String> foodlist = new ArrayList<String>();
		ArrayList<Integer> pricelist = new ArrayList<Integer>();
		ArrayList<String> contents = new ArrayList<String>();

		int deliveryTip = 2000;

		String generalUserCode = "";
		String storeUserCode = "";

		int generalD = 0;
		int storeD = 0;
		String storeName = "";

		for (Cart ct : Data.cartList) {
			if (ct.getGeneralUserCode().equals(LoginInfo.getGeneralUserCode())) {

				generalUserCode = ct.getGeneralUserCode();
				storeUserCode = ct.getStoreUserCode();

				for (MenuInfo mif : Data.menuInfoList) {
					if (ct.getStoreUserCode().equals(mif.getStoreUserCode())
							&& ct.getFoodName().equals(mif.getFoodName())) {
						foodlist.add(ct.getFoodName());
						pricelist.add(mif.getPrice());
					}
				}
			}
		}

		for (GeneralUser gu : Data.generalUserList) {
			if (gu.getUserCode().equals(generalUserCode)) {
				generalD = Integer.parseInt(gu.getDistance());
			}
		}

		for (StoreUser su : Data.storeUserList) {
			if (su.getUserCode().equals(storeUserCode)) {
				storeD = Integer.parseInt(su.getDistance());
				storeName = su.getStoreName();
			}
		}

		contents.add(String.format("[ %s ]", storeName));
		
		int count = 0;
		int countPrice = 0;
		for (int st : pricelist) {
			contents.add(String.format("%s: %,d원", foodlist.get(count), pricelist.get(count)));
			countPrice += pricelist.get(count);
			count++;
		}
		
		contents.add("");

		contents.add(String.format("메뉴 금액 : %,d원", countPrice));
		
		int distanceDiff = Math.abs(generalD - storeD);

		if (distanceDiff > 2000) {
			int extraBlocks = (int) Math.ceil((distanceDiff - 2000) / 500.0);
			deliveryTip += extraBlocks * 500;
		}

		contents.add(String.format("배달 금액 : %,d원", deliveryTip));
		contents.add(String.format("총 금액 : %,d원", countPrice + deliveryTip));
		
		GeneralUserUI.optOrder(contents);

		Scanner scan = new Scanner(System.in);

		boolean loop = true;
		while (loop) {
			String sel = scan.nextLine();

			if (sel.equals("1")) {
				PayUI.payAgreeHeader();

				System.out.printf("메뉴금액: %,d원\n", countPrice);
				System.out.printf("배달팁: %,d원\n", deliveryTip);
				System.out.printf("총금액: %,d원\n", (deliveryTip + countPrice));
				System.out.println();

				PayUI.payAgreeFooter();

				String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

				boolean loopPay = true;
				while (loopPay) {
					String selpay = scan.nextLine().toUpperCase();

					if (selpay.equals("Y")) {
						int countOrder = 0;

						for (Order or : Data.orderList) {
							if (or.getOrderCode().indexOf(today) >= 0) {
								if (countOrder < Integer.parseInt(or.getOrderCode().substring(8, 11))) {
									int currentOrderNum = Integer.parseInt(or.getOrderCode().substring(8, 11));

									if (currentOrderNum > countOrder) {
										countOrder = currentOrderNum;
									}
								}
							}
						}

						countOrder++;

						String orderCode = String.format("%s%03d", today, countOrder);

						int countOr = 0;
						for (int st : pricelist) {

							Order or = new Order(storeUserCode, generalUserCode, orderCode, foodlist.get(countOr),
									pricelist.get(countOr));
							Data.orderList.add(or);
							countOr++;
						}

						OrderInfo ori = new OrderInfo(storeUserCode, generalUserCode, orderCode, "true", "false");
						Data.orderInfoList.add(ori);

						ArrayList<Cart> updatedCartList = new ArrayList<>();

						for (Cart cr : Data.cartList) {
							if (!cr.getGeneralUserCode().equals(generalUserCode)) {
								updatedCartList.add(cr);
							}
						}
						Data.cartList = updatedCartList;

						Data.saveOrder();
						Data.saveOrderInfo();
						Data.saveCart();

						payComplete();
						loop = false;
					} else if (sel.equals("N")) {
						doOrder();
						loop = false;

					} else {
						System.out.println("다시 입력해 주세요.");
						continue;
					}
				}
				loop = false;

			} else if (sel.equals("0")) {
				getCart(2, selectedStore);
				loop = false;

			} else {
				System.out.println("다시 입력해 주세요.");
				continue;
			}
		}
	}

	/**
	 * 결제가 완료가 되었다는 것을 출력하는 메서드
	 */
	private void payComplete() {

		// 결제 완료
		PayUI.payFinish();
		Scanner scan = new Scanner(System.in);

		boolean loop = true;
		while (loop) {
			String sel = scan.nextLine();

			if (sel.isEmpty()) {
				UserService us = new UserService();

				us.generalUserMenu();
				loop = false;

			} else {
				CommonUI.pause();
				continue;
			}
		}
	}

	/**
	 * 마이페이지 메뉴를 출력하여 [주문 목록 및 리뷰 쓰기, 리뷰 관리] 메뉴 중 하나를 선택 받는 메서드
	 */
	@Override
	public void getMyPage() {
		// 마이페이지
		String luc = LoginInfo.getGeneralUserCode(); // 로그인 userID 검사 후 userCode 반환
		if (luc != "") {

			GeneralUserUI.optMyPage();

			Scanner scan = new Scanner(System.in);

			boolean loop = true;
			while (loop) {
				String sel = scan.nextLine();

				if (sel.equals("1")) {
					// 주문 목록
					orderHistory();
					loop = false;

				} else if (sel.equals("2")) {
					// 리뷰 쓰기
					writeReview();
					loop = false;

				} else if (sel.equals("3")) {
					// 리뷰 관리
					reviewManagement();
					loop = false;

				} else if (sel.equals("0")) {
					// 뒤로가기
					UserManager um = new UserService();
					um.generalUserMenu();
					loop = false;

				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			}
		}
	}

	/**
	 * 사용자가 주문한 목록의 리스트가 제공되는 메서드
	 */
	@Override
	public void orderHistory() {
		// 주문목록
		String luc = LoginInfo.getGeneralUserCode();
		if (luc != "") {
			CommonUI.header("주문 목록");
			int count = 0;

			for (StoreUser su : Data.storeUserList) {
				String sc = su.getUserCode();
				String sn = su.getStoreName();
				String ofn = "";
				int op = 0;

				for (Order ol : Data.orderList) {
					if (sc.equals(ol.getStoreUserCode()) && luc.equals(ol.getGeneralUserCode())) {
						if (ofn != "") {
							ofn += ", ";
						}
						ofn += String.format("%s", ol.getFoodName());
						op += ol.getPrice();
					}
				}
				if (ofn != "") {
					count++;
					System.out.printf("%d.%s/ ", count, sn);
					if (ofn.length() > 16) {
						System.out.printf(ofn.substring(0, 6) + " ··· " 
									+ ofn.substring(ofn.length()-6, ofn.length()));
					} else {
						System.out.printf(ofn);
					}
					System.out.printf("/ %,d원\r\n", op);
				}
			}

			CommonUI.footer();

			Scanner scan = new Scanner(System.in);
			boolean loop = true;

			while (loop) {
				String sel = scan.nextLine();

				if (sel.equals("0")) {
					getMyPage(); // 뒤로가기(마이페이지)
					loop = false;

				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			}
		}
	}

	/**
	 * 주문한 메뉴에 대한 작성하지 않은 리뷰 리스트를 출력하고, 작성할 리뷰를 선택 받는 메서드
	 */
	@Override
	public void writeReview() {
		// 리뷰 쓰기
		String luc = LoginInfo.getGeneralUserCode();
		ArrayList<String> noReviewList = new ArrayList<>();

		if (luc != "") {
			CommonUI.header("리뷰 쓰기");

			Set<String> reviewedOrderCodes = Data.reviewList.stream()
					.map(review -> review.getOrderCode())
					.collect(Collectors.toSet());
			
			int[] index = { 1 };
			Data.orderList.stream().filter(order -> order.getGeneralUserCode().equals(luc))
					.collect(Collectors.groupingBy(order -> order.getOrderCode()))
					.forEach((orderCode, list) -> {
						
						if (reviewedOrderCodes.contains(orderCode)) {
							return;
						}
						
						Data.storeUserList.stream()
								.filter(store -> store.getUserCode().equals(list.get(0).getStoreUserCode()))
								.forEach(store -> {
									String menu = list.stream().map(Order::getFoodName)
											.collect(Collectors.joining(", "));

									SelectStoreList ssl = new SelectStoreList(0, "", "", "");
									ssl = new SelectStoreList(index[0], list.get(0).getStoreUserCode(),
											list.get(0).getGeneralUserCode(), list.get(0).getOrderCode());
									Data.noReviewOrder.add(ssl);

									if (menu.length() > 22) {
										noReviewList.add(index[0] + "." + store.getStoreName() + "/ " 
												+ menu.substring(0, 9) + " ··· " 
												+ menu.substring(menu.length()-9, menu.length()));
									} else {
										noReviewList.add(index[0] + "." + store.getStoreName() + "/ " + menu);
									}
									index[0]++;
								});
					});

			if (noReviewList.size() > 0) {
				for(String n : noReviewList) {
					System.out.println(n);
				}
			} else {
				System.out.println("작성할 리뷰가 없습니다.");
			}


			CommonUI.footer();

			Scanner scan = new Scanner(System.in);
			boolean loop = true;

			while (loop) {
				String sel = scan.nextLine();

				if (Integer.parseInt(sel) > 0 && Integer.parseInt(sel) <= noReviewList.size()) {
					int n = Integer.parseInt(sel) - 1;
					String suc = Data.noReviewOrder.get(n).getStoreUserCode();
					String guc = Data.noReviewOrder.get(n).getGeneralUserCode();
					String oc = Data.noReviewOrder.get(n).getOrderCode();
					double rating = createRating();
					String review = createReview();

					Review rv = new Review(suc, guc, oc, rating, review);
					Data.reviewList.add(rv);

					Data.saveReview();
					System.out.print("리뷰가 등록되었습니다.");
					CommonUI.pause();
					getMyPage();

					loop = false;

				} else if (sel.equals("0")) {
					getMyPage(); // 뒤로가기(마이페이지)
					loop = false;

				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			}
		}
	}

	/**
	 * 리뷰를 작성할 주문에 대한 평점을 작성하는 메서드
	 */
	@Override
	public double createRating() {
		//평점 작성
		String luc = LoginInfo.getGeneralUserCode();
		if (luc != "") {
			CommonUI.header("평점 작성");
			System.out.printf("%-12s%-12s%-12s%-12s%-12s\n", "[1]", "[2]", "[3]", "[4]", "[5]");

			CommonUI.footer();

			Scanner scan = new Scanner(System.in);
			boolean loop = true;

			while (loop) {
				double sel = scan.nextDouble();

				if (sel == 1 || sel == 2 || sel == 3 || sel == 4 || sel == 5) {
					return sel;

				} else if (sel == 0) {
					writeReview(); // 뒤로가기(리뷰 쓰기)
					loop = false;

				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			}
		}
		return 0;
	}

	/**
	 * 리뷰를 작성할 주문에 대한 내용을 작성하는 메서드
	 */
	@Override
	public String createReview() {
		// 리뷰 작성
		String luc = LoginInfo.getGeneralUserCode();
		if (luc != "") {
			CommonUI.header("리뷰 작성");
			CommonUI.footer();
			System.out.println("리뷰를 작성하세요.");

			Scanner scan = new Scanner(System.in);
			boolean loop = true;

			while (loop) {
				String review = scan.nextLine();

				if (!review.equals(null) && !review.equals("0")) {
					return review;

				} else if (review.equals("0")) {
					// 뒤로가기(리뷰 쓰기)
					writeReview();
					loop = false;

				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			}
		}
		return null;
	}

	/**
	 * 이전에 작성한 리뷰를 관리하는 메서드
	 */
	@Override
	public void reviewManagement() {
		// 리뷰 관리
		String luc = LoginInfo.getGeneralUserCode();
		ArrayList<String> lineList = new ArrayList<>();
		ArrayList<String> orderCodeList = new ArrayList<>();
		String line = "";
		int index = 0;
		String sn = "";

		if (luc != "") {
			CommonUI.header("리뷰 관리");
			
			for (Review rl : Data.reviewList) {
				if (luc.equals(rl.getGeneralUserCode())) {
					String selOrderCode = rl.getOrderCode();

					Data.orderList.stream().filter(order -> order.getOrderCode().equals(selOrderCode))
											.collect(Collectors.groupingBy(Order::getOrderCode)).keySet()
											.forEach(order -> orderCodeList.add(order));

					for (int i = 0; i < orderCodeList.size(); i++) {
						if (orderCodeList.get(i).equals(selOrderCode)) {
							String rsuc = rl.getStoreUserCode();
							double rr = rl.getRating();
							String rv = rl.getComment();

							for (StoreUser su : Data.storeUserList) {
								if (rsuc.equals(su.getUserCode())) {
									sn = su.getStoreName();
								}
							}

							index++;
							line = String.format("%d. %s / %3.1f %s\n", index, sn, rr, rv);

							System.out.printf(line);
							lineList.add(line);

							MyReview ssr = new MyReview(index, rsuc, luc, selOrderCode);
							Data.myReviewList.add(ssr);
						}
					}
				}
			}
			CommonUI.footer();

			Scanner scan = new Scanner(System.in);
			boolean loop = true;

			while (loop) {
				String sel = scan.nextLine();

				if (Integer.parseInt(sel) > 0 && Integer.parseInt(sel) <= index) {
					int n = Integer.parseInt(sel) - 1;
					String oc = Data.myReviewList.get(n).getOrderCode();
					String suc = Data.myReviewList.get(n).getStoreUserCode();
					reviewInfo(oc, suc); // 리뷰 보기
					loop = false;

				} else if (sel.equals("0")) {
					// 뒤로가기(마이페이지)
					getMyPage();
					loop = false;

				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			}
		}
	}

	/**
	 * 이전에 작성한 리뷰를 확인한 후 삭제할 수 있는 메서드
	 * @param orderCode
	 * @param storeUserCode
	 */
	public void reviewInfo(String orderCode, String storeUserCode) {
		// 리뷰 보기
		String luc = LoginInfo.getGeneralUserCode();
		if (luc != "") {
			String sn = "";
			double rr = 0;
			String rv = "";

			CommonUI.header("리뷰 보기");

			for (StoreUser sul : Data.storeUserList) {
				if (storeUserCode.equals(sul.getUserCode())) {
					sn = String.format(sul.getStoreName());

					for (Review rc : Data.reviewList) {
						if (orderCode.equals(rc.getOrderCode())) {
							rr = rc.getRating();
							rv = rc.getComment();
						}
					}
				}
			}
			
			System.out.printf("%s / %3.1f\r\n%s\r\n", sn, rr, rv);
			System.out.println("\n[1] 리뷰 삭제");
			
			CommonUI.footer();

			Scanner scan = new Scanner(System.in);
			boolean loop = true;

			while (loop) {
				String sel = scan.nextLine();

				if (sel.equals("1")) {
					reviewDelete(orderCode);

					loop = false;

				} else if (sel.equals("0")) {
					// 뒤로가기(리뷰 관리)
					reviewManagement();
					loop = false;

				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			}
		}
	}

	/**
	 * 선택한 리뷰에 대한 삭제 여부를 재확인하는 메서드
	 * @param oc
	 */
	public void reviewDelete(String oc) {
		// 리뷰 삭제 확인
		String luc = LoginInfo.getGeneralUserCode();
		if (luc != "") {
			CommonUI.header("리뷰 삭제 확인");
			System.out.printf("삭제 하시겠습니까?\r\n( Y / N )");

			Scanner scan = new Scanner(System.in);
			boolean loop = true;

			while (loop) {
				String sel = scan.nextLine().toUpperCase();

				if (sel.equals("Y")) {
					for (Review rl : Data.reviewList) {

						if (rl.getOrderCode().equals(oc)) {
							CommonUI.header("리뷰 삭제");
							Data.reviewList.remove(rl);
							Data.saveReview();
							System.out.println("삭제 되었습니다.");
							CommonUI.pause();
							getMyPage();
							break;
						}
					}
					loop = false;

				} else if (sel.equals("N")) {
					reviewManagement(); // 뒤로가기(리뷰 관리)
					loop = false;

				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			}
		}
	}
}
