package com.test.ygdd.service;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.test.ygdd.data.Data;
import com.test.ygdd.data.MenuInfo;
import com.test.ygdd.data.OrderDetail;
import com.test.ygdd.data.OrderInfo;
import com.test.ygdd.data.Review;
import com.test.ygdd.data.SelectedMenu;
import com.test.ygdd.ui.CommonUI;
import com.test.ygdd.ui.StoreUserUI;
import com.test.ygdd.user.LoginInfo;
import com.test.ygdd.user.StoreUser;
import com.test.ygdd.user.UserManager;
import com.test.ygdd.user.UserService;
/**
 * 가맹점회원 서비스 관련. toreUserManager 인터페이스를 상속 받는 클래스
 */
public class StoreUserService implements StoreUserManager {

	/**
	 * 등록 할 음식 메뉴에 대한 정보를 입력 받아 메뉴를 등록하는 메서드
	 */
	@Override
	public void createMenu() {
		
		try {
			Scanner scan = new Scanner(System.in);
			
			System.out.print("메뉴 이름 입력: ");
			String menuName = scan.nextLine();
			
			System.out.print("가격 입력: ");
			int price = scan.nextInt();
			
			System.out.print("소요 시간 입력: ");
			int time = scan.nextInt();
			
			String storeName = "";
			for (StoreUser su : Data.storeUserList) {
				if (su.getUserCode().equals(LoginInfo.getCurrentId())) {
					storeName = su.getStoreName();
				}
			}
			
			MenuInfo mi = new MenuInfo(LoginInfo.getCurrentId(), storeName, menuName, price, time);
			
			Data.menuInfoList.add(mi);
			
			
			Data.saveMenu();
			
			System.out.println("메뉴가 등록 되었습니다.");
			
			CommonUI.pause();
			
			UserManager um = new UserService();
			um.storeUserMenu();
			
		} catch (Exception e) {
			System.out.println("StoreUserService.createMenu");
			e.printStackTrace();
		}
		
	}

	/**
	 * 메뉴 변경 메서드를 호출하는 메서드
	 */
	@Override
	public void modifyMenu() {
		
		getMenuList("메뉴수정");
		
	}

	private void getMenuList(String title) {
		
		ArrayList<SelectedMenu> selectedMenuList = new ArrayList<SelectedMenu>();
		
		int index = 0;
		for (int i = 0; i < Data.menuInfoList.size(); i++) {
			if (Data.menuInfoList.get(i).getStoreUserCode().equals(LoginInfo.getCurrentId())) {
				selectedMenuList.add(new SelectedMenu(index+1, i, Data.menuInfoList.get(i).getStoreUserCode(), Data.menuInfoList.get(i).getStoreName()
									, Data.menuInfoList.get(i).getFoodName()));
				index++;
			}
		}
		
		
		CommonUI.header(title);
		for (SelectedMenu sm : selectedMenuList) {
			System.out.printf("%d. %s" + "\n", sm.getIndex(), sm.getFoodName());
		}
		CommonUI.footer();
		
		
		Scanner scan = new Scanner(System.in);
		
		boolean loop = true;
		while (loop) {
			
			String strSel = scan.nextLine();
			boolean result = strSel.matches("[-+]?\\d*\\.?\\d+");
			
			if (result == true) {
				int sel = Integer.parseInt(strSel);
				
				if(sel >= 1 && sel <= selectedMenuList.size()) {
					int seq = 0;
					for (SelectedMenu sm : selectedMenuList) {
						if (sel == sm.getIndex()) {
							seq = sm.getListSeq();
						}
					}
					updateMenu(title, selectedMenuList, seq);
					loop = false;
				} else if (sel == 0) {
					UserManager um = new UserService();
					um.storeUserMenu();
					loop = false;
				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			} else {
				System.out.println("숫자가 아닙니다. 다시 입력해 주세요.");
				continue;
			}
		}
	}

	private void updateMenu(String title, ArrayList<SelectedMenu> selectedMenuList, int seq) {
		
		try {
			String storeName = selectedMenuList.get(0).getStoreName();
			
			for (int i = 0; i < Data.menuInfoList.size(); i++) {
				if (i == seq) {
					Data.menuInfoList.remove(i);
					break;
				}
			}
			
			if (title == "메뉴수정") {
				Scanner scan = new Scanner(System.in);
				
				System.out.print("메뉴 이름 입력: ");
				String menuName = scan.nextLine();
				
				System.out.print("가격 입력: ");
				int price = scan.nextInt();
				
				System.out.print("소요 시간 입력: ");
				int time = scan.nextInt();
				
				MenuInfo mi = new MenuInfo(LoginInfo.getCurrentId(), storeName, menuName, price, time);
				Data.menuInfoList.add(mi);
				
				System.out.println("메뉴가 수정 되었습니다.");
				
			} else {
				System.out.println("메뉴가 삭제 되었습니다.");
			}
			
			Data.saveMenu();
			
			CommonUI.pause();
			
			UserManager um = new UserService();
			um.storeUserMenu();
			
			
		} catch (Exception e) {
			System.out.println("StoreUserService.updateMenu");
			e.printStackTrace();
		}
	}

	/**
	 * 메뉴를 삭제했을 시 해당 메뉴를 삭제시키는 메서드
	 */
	@Override
	public void deleteMenu() {
		
		getMenuList("메뉴삭제");
	}

	/**
	 * 일반회원이 결제한 메뉴에 대한 주문 내역을 출력하고, 
	 * 내역을 하나 입력 받아 [주문 수락 및 주문 거절] 메뉴 중 하나를 선택 받는 메서드
	 */
	@Override
	public void agreeOrder() {
		
		try {
			ArrayList<String> orderCodeList = new ArrayList<String>();
			ArrayList<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			ArrayList<String> strOrderList = new ArrayList<String>();
			
			UserManager um = new UserService();
			
			Data.orderInfoList.stream().filter(order -> order.getStoreUserCode().equals(LoginInfo.getCurrentId()))
															.filter(order -> order.getPayStatus().equals("true"))
															.filter(order -> order.getAgreeStatus().equals("false"))
															.forEach(s -> orderCodeList.add(s.getOrderCode()));
			
			
			orderCodeList.forEach(orderCode -> {
			    Data.orderList.stream()
			        .filter(order -> order.getOrderCode().equals(orderCode))
			        .forEach(order -> orderDetailList.add(
			            new OrderDetail(
			                order.getOrderCode(),
			                order.getGeneralUserCode(),
			                order.getFoodName(),
			                order.getPrice()
			            )
			        ));
			});
			

			orderDetailList.stream().collect(Collectors.groupingBy(OrderDetail::getOrderCode))
									.forEach((orderCode, list) -> {
										String userCode = list.get(0).getGeneralUserCode(); 
										String menu = list.stream().map(s -> s.getFoodName()).collect(Collectors.joining(","));
										int price = list.stream().mapToInt(n -> n.getPrice()).sum();
										
										strOrderList.add(orderCode + "] " + userCode + "/" + menu + "/" + price);
									});
			
			
			StoreUserUI.optAgreeFoodList(strOrderList);
			
			
			Scanner scan = new Scanner(System.in);
			String strSel = scan.nextLine();
			
			boolean result = strSel.matches("[-+]?\\d*\\.?\\d+");
			
			boolean loop = true;
			
			while (loop) {
				if (result == true) {
					int sel = Integer.parseInt(strSel);
					
					if (sel >= 1 && sel <= strOrderList.size()) {
						
						String[] temp = strOrderList.get(sel-1).split("]");
						
						boolean loop2 = true;
						while (loop2) {
							
							StoreUserUI.optAgreeOrder();
							strSel = scan.nextLine();
							
							if (strSel.equals("1")) {
								actionOrder("true", temp[0]);
								
								loop2 = false;
								
							} else if (strSel.equals("2")) {
								actionOrder("false", temp[0]);
								
								loop2 = false;
								
							} else if (strSel.equals("0")) {
								
								um.storeUserMenu();
								
								loop2 = false;
								
							} else {
								
								System.out.println("다시 입력해 주세요.");
								continue;
							}
						}
						
						loop = false;
					} else {
						System.out.println("다시 입력해 주세요.");
						continue;
					}
				} else {
					System.out.println("숫자가 아닙니다. 다시 입력해 주세요.");
					continue;
				}
			}
			
			um.storeUserMenu();
			
		} catch (Exception e) {
			System.out.println("StoreUserService.agreeOrder");
			e.printStackTrace();
		}
		
	}

	private void actionOrder(String Status, String orderCode) {

		String storeUser = "";
		String generalUser = "";
		
		for (OrderInfo oi : Data.orderInfoList) {
			if (oi.getOrderCode().equals(orderCode)) {
				storeUser = oi.getStoreUserCode();
				generalUser = oi.getGeneralUserCode();
				Data.orderInfoList.remove(oi);
				
				
				if (Status == "true") {
					Data.orderInfoList.add(new OrderInfo(storeUser, generalUser, orderCode, "true", Status));
					System.out.println("주문이 수락 되었습니다.");
				} else {
					System.out.println("주문이 거절되었습니다.");
				}
				break;
			}
		}
		
		Data.saveOrderInfo();

		CommonUI.pause();
	}

	/**
	 * 마이페이지 메뉴를 출력하여 [주문 목록 및 리뷰 쓰기, 리뷰 관리] 메뉴 중 하나를 선택 받는 메서드
	 * 점포관리 메뉴를 출력하여 [리뷰 관리, 매출 확인] 메뉴 중 하나를 선택 받는 메서드
	 */
	@Override
	public void storeManagement() {
		
		try {
			UserManager um = new UserService();
			
			StoreUserUI.optStoreManagement(); //B02-05.점포관리
			Scanner scan = new Scanner(System.in);
			
			boolean loop = true;
			while (loop) {
				
				String sel = scan.nextLine();
				
				if (sel.equals("1")) {
					//1.가게 리뷰 관리
					reviewManagement();
					loop = false;
					
				} else if (sel.equals("2")) {
					//2.가게 매출 관리
					salesManagement();
					loop = false;
					
				} else if (sel.equals("0")) {
					//0.뒤로가기
					um.storeUserMenu();
					loop = false;
					
				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println("StoreUserService.storeManagement");
			e.printStackTrace();
		}
	}
	
	/**
	 * 가게에 대한 총 매출을 계산하여 출력하는 메서드
	 */
	@Override
	public void salesManagement() {
		
		ArrayList<String> orderCodeList = new ArrayList<String>();
		ArrayList<Integer> sales = new ArrayList<Integer>();
		String[] storeInfo = {"", ""};
		
		Data.orderInfoList.stream().filter(orderInfo -> orderInfo.getStoreUserCode().equals(LoginInfo.getCurrentId()))
									.filter(orderInfo -> orderInfo.getPayStatus().equals("true"))
									.filter(orderInfo -> orderInfo.getAgreeStatus().equals("true"))
									.forEach(orderInfo -> orderCodeList.add(orderInfo.getOrderCode()));
		
		orderCodeList.forEach(orderCode -> {
			Data.orderList.stream()
				.filter(order -> order.getOrderCode().equals(orderCode))
				.forEach(order -> {
					sales.add(order.getPrice());
					storeInfo[0] = order.getStoreUserCode();
				});
		});
		
		
		Data.storeUserList.stream().filter(storeUser -> storeUser.getUserCode().equals(storeInfo[0]))
									.forEach(storeUser -> storeInfo[1] = storeUser.getStoreName());;
		
									
		int sum = 0;
		for (int price : sales) {
			sum += price;
		}
		
		StoreUserUI.showStoreSales(storeInfo[1], sum);
		
		CommonUI.pause();
		storeManagement();
	}
	
	/**
	 * 가게에 대한 일반 회원이 작성한 리뷰 리스트를 출력하고, 내역을 하나 입력 받는 메서드
	 */
	@Override
	public void reviewManagement() {
		
		try {
			ArrayList<String> strReview = new ArrayList<String>();
			double[] rating = {0.0};
			
			Data.reviewList.stream().filter(review -> review.getStoreUserCode().equals(LoginInfo.getCurrentId()))
									.forEach(review -> {
										strReview.add(review.getOrderCode() + "] " + review.getGeneralUserCode() + "/" + review.getRating() + "/" + review.getComment());
										rating[0] += review.getRating();
									});
		
			StoreUserUI.optReviewManagement(rating[0] / strReview.size(), strReview);
			
			Scanner scan = new Scanner(System.in);
			
			boolean loop = true;
			while (loop) {
				
				String strSel = scan.nextLine();
				boolean result = strSel.matches("[-+]?\\d*\\.?\\d+");
				
				if (result == true) {
					int sel = Integer.parseInt(strSel);
					
					if (sel >= 1 && sel <= strReview.size()) {
						
						String[] temp = strReview.get(sel-1).split("]");
						
						showReviewDetail(temp[0]);
							
						loop = false;
						
					} else if (sel == 0) {
						storeManagement();
						loop = false;
					} else {
						System.out.println("번호를 잘못 입력했습니다. 다시 입력해 주세요.");
						continue;
					}
				} else {
					System.out.println("숫자가 아닙니다. 다시 입력해 주세요.");
					continue;
				}
			}
			
		} catch (Exception e) {
			System.out.println("StoreUserService.reviewManagement");
			e.printStackTrace();
		}
		
	}

	private void showReviewDetail(String orderCode) {		
		try {
			ArrayList<String> reviewDetail = new ArrayList<String>();
			Data.reviewList.stream().filter(review -> review.getOrderCode().equals(orderCode))
									.forEach(review -> {
										reviewDetail.add("[" + review.getGeneralUserCode() + "]");
										reviewDetail.add("★" + Double.toString(review.getRating()));
										reviewDetail.add(review.getComment());
										reviewDetail.add("");
									});
			
			StoreUserUI.optShowReviewDatail(reviewDetail);
			
			Scanner scan = new Scanner(System.in);
			
			boolean loop = true;
			while (loop) {
				
				String sel = scan.nextLine();
				
				if (sel.equals("1")) {
					
					deleteReview(orderCode);
					loop = false;
					
				} else if (sel.equals("0")) {
					reviewManagement();
					loop = false;
					
				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			}
			
		} catch (Exception e) {
			System.out.println("StoreUserService.showReviewDetail");
			e.printStackTrace();
		}
	}

	private void deleteReview(String orderCode) {
		
		try {
			System.out.println("삭제하시겠습니까? (Y/N)");
			
			Scanner scan = new Scanner(System.in);
			
			boolean loop = true;
			while (loop) {
				
				String sel = scan.nextLine().toUpperCase();
				
				if (sel.equals("Y")) {
					for (Review review : Data.reviewList) {
						if (review.getOrderCode().equals(orderCode)) {
						
							Data.reviewList.remove(review);
							System.out.println("리뷰가 삭제되었습니다.");
							
							Data.saveReview();
							break;
						}
					}
					
					loop = false;
					
				} else if (sel.equals("N")) {
					System.out.println("취소되었습니다.");
					
					showReviewDetail(orderCode);
					loop = false;
					
				} else {
					System.out.println("다시 입력해 주세요.");
					continue;
				}
			}
			
			CommonUI.pause();
			
			reviewManagement();
			
		} catch (Exception e) {
			System.out.println("StoreUserService.deleteReview");
			e.printStackTrace();
		}
		
	}

}
