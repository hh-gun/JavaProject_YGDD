package com.test.ygdd.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.test.ygdd.data.Cart;
import com.test.ygdd.data.Data;
import com.test.ygdd.data.MenuInfo;
import com.test.ygdd.data.Review;
import com.test.ygdd.ui.CommonUI;
import com.test.ygdd.ui.GeneralUserUI;
import com.test.ygdd.ui.StoreUI;
import com.test.ygdd.user.LoginInfo;
import com.test.ygdd.user.GeneralUser;
import com.test.ygdd.user.StoreUser;
import com.test.ygdd.user.UserManager;
import com.test.ygdd.user.UserService;

/**
 * 가게 정보 서비스 관련. StoreInfoManager()를 상속 받는 클래스
 */
public class StoreInfoService implements StoreInfoManager {

	private StoreUser selectedStore;
	private String selectedCode = "";
	
	/**
	 * 카테고리 목록을 출력하고, 제공 된 카테고리를 선택 받는 메서드
	 */
	@Override
	public void getCategory() {
		
		GeneralUserUI.optFoodCategory();
		
		Scanner scan = new Scanner(System.in);
		
		int size = Data.categoryList.size();

		boolean loop = true;
		while(loop) {
			String inputCategoryNum = scan.nextLine();
			
			if (inputCategoryNum.matches("\\d+") == true) {
				int sel = Integer.parseInt(inputCategoryNum);

				if (sel >= 1 && sel <= size) {
					selectedCode = Data.categoryList.get(sel - 1).getCategoryCode();

					getStoreList();
					
				} else if (sel == 0) {
					// 뒤로가기
					UserManager um = new UserService();
					um.generalUserMenu();
					
				} else {
					System.out.println("번호를 잘못 입력했습니다. 다시 입력해 주세요.\n");
					continue;
				}
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력해 주세요.\n");
				continue;
			}
		}
	}

	/**
	 * 선택한 카테고리에 대한 가게 목록을 출력하고, 선택할 가게를 입력 받는 메서드
	 */
	@Override
	public void getStoreList() {
		
		// 현재 로그인한 일반회원의 주소 코드 가져오기
		String loginAddressCode = Data.generalUserList.stream()
				.filter(gu -> gu.getId().equals(LoginInfo.getCurrentId())).map(gu -> gu.getAddressCode()).findFirst()
				.orElse(null);

		
		// 일반회원-가게 거리 구하기
		int loginDistance = Data.generalUserList.stream().filter(gu -> gu.getId().equals(LoginInfo.getCurrentId()))
				.mapToInt(gu -> Integer.parseInt(gu.getDistance())).findFirst().orElse(0);

		
		List<StoreUser> inputStoreNum = new ArrayList<>();
		boolean found = false;
		int storeNum = 1;

		CommonUI.header("가게 목록");

		for (StoreUser su : Data.storeUserList) {
			if (selectedCode.equals(su.getCategoryCode()) && loginAddressCode != null
					&& loginAddressCode.equals(su.getAddressCode())) {

				int reviewCount = Long
						.valueOf(Data.reviewList.stream()
								.filter(review -> review.getStoreUserCode().equals(su.getUserCode())).count())
						.intValue();

				double averRating = 0.0;

				if (reviewCount > 0) {
					double[] rating = { 0.0 };
					Data.reviewList.stream()
							.filter(review -> review.getStoreUserCode().equals(su.getUserCode()))
							.forEach(review -> rating[0] += review.getRating());

					averRating = rating[0] / reviewCount;
				}

				int storeDistance = Integer.parseInt(su.getDistance());
				int totalDistance = Math.abs(loginDistance - storeDistance);

				// 거리) m -> km변환
				double totalDistanceKm = totalDistance / 1000.0;

				System.out.printf("%d. %s/평점: %.1f/리뷰수: %d/거리: %.1fkm\n", storeNum, su.getStoreName(),
						averRating, reviewCount, totalDistanceKm);
				storeNum++;

				inputStoreNum.add(su);
				found = true;
			}
		}
		
		Scanner scan = new Scanner(System.in);
		if (found == false) {
			System.out.println("주문 가능한 가게가 없습니다.");
			
			CommonUI.footer();
			
			System.out.print("입력: ");
			
			boolean loop = true;
			while (loop) {
				String back = scan.nextLine();
				
				if (back.equals("0")) {
					getCategory();
					loop = false;
				} else {
					System.out.println("잘못된 입력입니다. 다시 입력해 주세요.");
			
					continue;
				}
			}
		}
		CommonUI.footer();

		
		if (inputStoreNum.isEmpty() == false) {
			System.out.println("상세정보를 볼 가게 번호를 입력하세요. ");
			
			boolean loop = true;
			while (loop) {
				String input = scan.nextLine();
				
				if (input.matches("\\d+") == true) {
					int index = Integer.parseInt(input);
					if (index == 0) {
						getCategory();
						loop = false;
						
					} else if (index >= 1 && index <= inputStoreNum.size()) {
						selectedStore = inputStoreNum.get(index - 1);
						getStoreInfo();
						loop = false;
						
					} else {
						System.out.println("번호를 잘못 입력했습니다. 다시 입력해 주세요.\n");
						continue;
					}
				} else {
					System.out.println("잘못된 입력입니다. 다시 입력해 주세요.\n");
					continue;
				}
			}
		}
	}

	/**
	 * 선택한 가게의 정보를 출력하고, [음식보기, 리뷰보기, 가게 상세 정보] 중 하나를 선택 받는 메서드
	 */
	@Override
	public void getStoreInfo() {
		
		//가게정보
		StoreUI.optStoreInfo(selectedStore.getStoreName());
		Scanner scan = new Scanner(System.in);
		
		boolean loop = true;
		while (loop) {
			String sel = scan.nextLine();
			
			if (sel.equals("1")) {
				// 1.음식보기
				getMenu(selectedStore);
				loop = false;
				
			} else if (sel.equals("2")) {
				// 2.리뷰 보기
				getReview();
				loop = false;
				
			} else if (sel.equals("3")) {
				// 3.가게 상세 정보
				getStoreDetail();
				loop = false;
				
			} else if (sel.equals("0")) {
				// 0.뒤로가기
				getStoreList();
				loop = false;
				
			} else {
				System.out.println("다시 입력해 주세요.");
				continue;
			}	
		}
	}

	/**
	 * 가게상세정보(주소, 사용자와의 거리, 거리에 따른 배달팁)를 보여주는 메서드
	 */
	@Override
	public void getStoreDetail() {
		
		//가게 상세정보
		CommonUI.header("가게 상세정보");

		Scanner scan = new Scanner(System.in);

		for (StoreUser su : Data.storeUserList) {
			if (su.getUserCode().equals(selectedStore.getUserCode())) { //가맹점회원코드바꿔야함
				
				System.out.println("주소: " + su.getAddress());
				
				for(GeneralUser gu : Data.generalUserList) {
					if (gu.getUserCode().equals(LoginInfo.getGeneralUserCode())) {
						
						int storeDistance = Integer.parseInt(su.getDistance()); // 단위: m
			            int generalDistance = Integer.parseInt(gu.getDistance());

			            int distanceDiff = Math.abs(storeDistance - generalDistance);
		                
						if (storeDistance > generalDistance) {
						
							System.out.printf("거리: %.1fkm (%dm)\n", distanceDiff / 1000.0, distanceDiff);
						
						} else if (storeDistance < generalDistance) {
						
							System.out.printf("거리: %.1fkm (%dm)\n", distanceDiff / 1000.0, distanceDiff);
						
						} else if (storeDistance == generalDistance) {
						
							System.out.println("거리: " + 0 + "km");
						
						}
						
		            int deliveryTip = 2000;
		            if (distanceDiff > 2000) {
		                int extraBlocks = (int)Math.ceil((distanceDiff - 2000) / 500.0);
		                deliveryTip += extraBlocks * 500;
		            }
		                System.out.println("배달팁: " + deliveryTip + "원");
					}
				}
			}
		}
		
		StoreUI.storeDetailInfo();
		
		scan = new Scanner(System.in);
		
		boolean loop = true;
		while (loop) {
			
			String sel = scan.nextLine();
			
			if (sel.equals("0")) {
				
				getStoreInfo();
				loop = false;
				
			} else {
				
				System.out.print("다시 입력해 주세요.");
				continue;
				
			}
		}
		
	}

	/**
	 * 가게에 작성되어 있는 전체 평점과 리뷰를 출력하는 메서드
	 */
	@Override
	public void getReview() {
		
		//리뷰 보기
		CommonUI.header("가게 리뷰 확인");

		Scanner scan = new Scanner(System.in);
		
		for (Review re : Data.reviewList) {
		    if (re.getStoreUserCode().equals(selectedStore.getUserCode())) {
		    	for (GeneralUser gu : Data.generalUserList) {
		    		if (gu.getUserCode().equals(re.getGeneralUserCode())) {
		    			
		    			System.out.println("> " + gu.getId() + ":" + re.getComment() + " [🌟" + re.getRating() + "]");
		    		
		    		}
		    	}
		    }
		}

		StoreUI.storeReviewCheck();
		
		scan = new Scanner(System.in);
		
		boolean loop = true;
		while (loop) {
			
			String sel = scan.nextLine();
			
			if (sel.equals("0")) {
				getStoreInfo();
				loop = false;
				
			} else {
				
				System.out.println("다시 입력해 주세요.");
				System.out.print("번호 입력:");
				continue;
			}
		}
	}

	/**
	 * 해당 가게가 제공하는 음식 목록을 출력하고, 
	 * 음식을 선택하여 장바구니에 담거나 장바구니로 이동하는 메서드
	 */
	@Override
	public void getMenu(StoreUser su) {
		
		if (selectedStore == null) {
			selectedStore = su;
		}
		
		//음식보기
		ArrayList<MenuInfo> selectedMenuList = new ArrayList<>();
		ArrayList<String> contents = new ArrayList<String>();
		
		for (MenuInfo mi : Data.menuInfoList) {
	        if (mi.getStoreUserCode().equals(selectedStore.getUserCode())) {
	            selectedMenuList.add(mi);
	        }
	    }
		
	    if (selectedMenuList.isEmpty()) {
	        CommonUI.infoFooter("해당 가게의 메뉴가 없습니다.");
	        getStoreInfo();
	    }
	    
	    contents.add(String.format("[ %s ]", selectedMenuList.get(0).getStoreName()));
	    System.out.println("음식을 선택하세요.");
        for (int i = 0; i < selectedMenuList.size(); i++) {
            MenuInfo mi = selectedMenuList.get(i);
            
            contents.add(String.format("%d. %s | %d원", i + 1, mi.getFoodName(), mi.getPrice()));
        }

        
        StoreUI.optMenuSelect(contents);
        
        Scanner scan = new Scanner(System.in);
        
		boolean loop = true;
		while (loop) {
			
	        String input = scan.nextLine().toUpperCase();
	        boolean result = input.matches("[-+]?\\d*\\.?\\d+");
	        
	        if (result == true) {
	        	int sel = Integer.parseInt(input);
	        	
	        	if (sel >= 1 && sel <= selectedMenuList.size()) {
	        		MenuInfo selected = selectedMenuList.get(sel - 1);
	        		
	        		Data.cartList.removeIf(cart -> cart.getGeneralUserCode().equals(LoginInfo.getGeneralUserCode())
	        									&& !cart.getStoreUserCode().equals(selected.getStoreUserCode()));
	        		
	                Cart ca = new Cart(selected.getStoreUserCode(), LoginInfo.getGeneralUserCode(), selected.getFoodName());
	               
	                Data.cartList.add(ca);
	                
	                Data.saveCart();
	                
	                System.out.println("✅ " + selected.getFoodName() + " 을(를) 장바구니에 담았습니다.");
	        	} else if (sel == 0) {
	        		getStoreInfo();
	        		loop = false;
	        	} else {
	        		System.out.println("번호를 잘못 입력했습니다. 다시 입력해 주세요.");
					continue;
	        	}
	        } else {
	        	if (input.equals("C")) {
	        		// 장바구니 이동
		            GeneralUserManager gum = new GeneralUserService();
		            gum.getCart(2, selectedStore);
		            
		            loop = false;
		            
		        } else {
		        	System.out.println("숫자가 아닙니다. 다시 입력해 주세요.");
					continue;
		        }
	        }
	    }
	}
}
