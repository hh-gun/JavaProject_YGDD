package com.test.ygdd.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.test.ygdd.Main;
import com.test.ygdd.file.FileManager;
import com.test.ygdd.user.GeneralUser;
import com.test.ygdd.user.LoginInfo;
import com.test.ygdd.user.StoreUser;

/**
 * 데이터 파일 리스트 저장 클래스
 */
public class Data {
	
	/**
	 * 가맹점회원 정보를 배열리스트에 저장
	 */
	public static ArrayList<StoreUser> storeUserList;
	/**
	 * 일반회원 정보를 배열리스트에 저장
	 */
	public static ArrayList<GeneralUser> generalUserList;
	/**
	 * 주소 정보를 배열리스트에 저장
	 */
	public static ArrayList<Address> addressList;
	/**
	 * 장바구니 정보를 배열리스트에 저장
	 */
	public static ArrayList<Cart> cartList;
	/**
	 * 카테고리 정보를 배열리스트에 저장
	 */
	public static ArrayList<Category> categoryList;
	/**
	 * 음식메뉴 정보를 배열리스트에 저장
	 */
	public static ArrayList<MenuInfo> menuInfoList;
	/**
	 * 주문목록 정보를 배열리스트에 저장
	 */
	public static ArrayList<Order> orderList;
	/**
	 * 주문 정보를 배열리스트에 저장
	 */
	public static ArrayList<OrderInfo> orderInfoList;
	/**
	 * 리뷰 정보를 배열리스트에 저장
	 */
	public static ArrayList<Review> reviewList;
	/**
	 * 가게 리뷰 정보를 배열리스트에 저장
	 */
	public static ArrayList<SelectStoreList> noReviewOrder;
	/**
	 * 일반회원 리뷰 정보를 배열리스트에 저장
	 */
	public static ArrayList<MyReview> myReviewList;
	
	static {
		
		storeUserList = new ArrayList<StoreUser>();
		generalUserList = new ArrayList<GeneralUser>();
		addressList = new ArrayList<Address>();
		cartList = new ArrayList<Cart>();
		categoryList = new ArrayList<Category>();
		menuInfoList = new ArrayList<MenuInfo>();
		orderList = new ArrayList<Order>();
		orderInfoList = new ArrayList<OrderInfo>();
		reviewList = new ArrayList<Review>();
		noReviewOrder = new ArrayList<SelectStoreList>();
		myReviewList = new ArrayList<MyReview>();
		
	}
	/**
	 * 데이터 파일 정보를 불러와 리스트에 담는 메서드
	 */
	public static void load() {
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(FileManager.가맹점회원));
			
			String lineStoreUser = null;
			
			while((lineStoreUser = reader.readLine()) != null) {
				
				String[] temp = lineStoreUser.split("■");
				
				StoreUser su = new StoreUser(temp[0], temp[1], temp[2], temp[3]
											,temp[4], temp[5], temp[6], temp[7]
											,temp[8], temp[9], temp[10]);
				storeUserList.add(su);
				
			}
			
			reader = new BufferedReader(new FileReader(FileManager.일반회원));
			
			String lineGeneralUser = null;
			
			while((lineGeneralUser = reader.readLine()) != null) {
				
				String[] temp = lineGeneralUser.split("■");
				
				GeneralUser gu = new GeneralUser(temp[0], temp[1], temp[2], temp[3]
											,temp[4], temp[5], temp[6], temp[7]
											,temp[8]);
				generalUserList.add(gu);
				
			}
			
			reader = new BufferedReader(new FileReader(FileManager.주소));
			
			String lineAddress = null;
			
			while((lineAddress = reader.readLine()) != null) {
				
				String[] temp = lineAddress.split("■");
				
				Address address = new Address(temp[0], temp[1]);
				addressList.add(address);
				
			}
			
			reader = new BufferedReader(new FileReader(FileManager.카테고리));
			
			String lineCategory = null;
			
			while((lineCategory = reader.readLine()) != null) {
				
				String[] temp = lineCategory.split("■");
				
				Category category = new Category(temp[0], temp[1]);
				categoryList.add(category);
				
			}
			
			reader = new BufferedReader(new FileReader(FileManager.메뉴정보));
			
			String lineMenuInfo = null;
			
			while((lineMenuInfo = reader.readLine()) != null) {
				
				String[] temp = lineMenuInfo.split("■");
				
				MenuInfo menu = new MenuInfo(temp[0], temp[1], temp[2], Integer.parseInt(temp[3])
											,Integer.parseInt(temp[4]));
				menuInfoList.add(menu);
				
			}
			
			reader = new BufferedReader(new FileReader(FileManager.주문목록));
			
			String lineOrder = null;
			
			while((lineOrder = reader.readLine()) != null) {
				
				String[] temp = lineOrder.split("■");
				
				Order order = new Order(temp[0], temp[1], temp[2], temp[3]
										,Integer.parseInt(temp[4]));
				orderList.add(order);
				
			}
			
			reader = new BufferedReader(new FileReader(FileManager.장바구니));
			
			String lineCart = null;
			
			while((lineCart = reader.readLine()) != null) {
				
				String[] temp = lineCart.split("■");
				
				Cart cart = new Cart(temp[0], temp[1], temp[2]);
				cartList.add(cart);
				
			}
			
			reader = new BufferedReader(new FileReader(FileManager.리뷰정보));
			
			String lineReview = null;
			
			while((lineReview = reader.readLine()) != null) {
				
				String[] temp = lineReview.split("■");
				
				Review review = new Review(temp[0], temp[1], temp[2], Double.parseDouble(temp[3]), temp[4]);
				reviewList.add(review);
				
			}
			
			reader = new BufferedReader(new FileReader(FileManager.주문정보));
			
			String lineOrderInfo = null;
			
			while((lineOrderInfo = reader.readLine()) != null) {
				
				String[] temp = lineOrderInfo.split("■");
				
				OrderInfo orderInfo = new OrderInfo(temp[0], temp[1], temp[2], temp[3]
													,temp[4]);
				orderInfoList.add(orderInfo);
				
			}
			
			reader.close();
			
		} catch (Exception e) {
			
			System.out.println("Data.load");
			e.printStackTrace();
			
		}

	}

	/**
	 * 음식 메뉴 정보를 메뉴 정보 데이터 파일에 출력하는 메서드
	 */
	public static void saveMenu() {
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(FileManager.메뉴정보));
			
			for (MenuInfo mi : Data.menuInfoList) {
						
				writer.write(String.format("%s■%s■%s■%d■%d\r\n"
									, mi.getStoreUserCode()
									, mi.getStoreName()
									, mi.getFoodName()
									, mi.getPrice()
									, mi.getTime()
				));
			}
			
			writer.close();
					
		} catch (Exception e) {
			System.out.println("Data.save");
			e.printStackTrace();
		}		
		
	}
	
	/**
	 * 장바구니 정보를 장바구니 데이터 파일에 출력하는 메서드
	 */
	public static void saveCart() {
		
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(FileManager.장바구니));
			
			for (Cart ct : Data.cartList) {
				
				writer.write(String.format("%s■%s■%s\r\n"
									, ct.getStoreUserCode()
									, ct.getGeneralUserCode()
									, ct.getFoodName()
				));
			}
			
			writer.close();
					
		} catch (Exception e) {
			System.out.println("Data.save");
			e.printStackTrace();
		}		
		
	}
	
	/**
	 * 주문목록 정보를 주문 목록 데이터 파일에 출력하는 메서드
	 */
	public static void saveOrder() {
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(FileManager.주문목록));
			
			for(Order or : Data.orderList) {
				writer.write(String.format("%s■%s■%s■%s■%d\r\n"
						, or.getStoreUserCode()
						, or.getGeneralUserCode()
						, or.getOrderCode()
						, or.getFoodName()
						, or.getPrice()
					));
				}
			
		writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 주문 정보를 주문 정보 데이터 파일에 출력하는 메서드
	 */
	public static void saveOrderInfo() {
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(FileManager.주문정보));
			
			for(OrderInfo ori : Data.orderInfoList) {
				writer.write(String.format("%s■%s■%s■%s■%s\r\n"
						, ori.getStoreUserCode()
						, ori.getGeneralUserCode()
						, ori.getOrderCode()
						, ori.getPayStatus()
						, ori.getAgreeStatus()	
					));
				}
			
		writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String generateOrderNumber(String today, String userCode, String storeCode) {

		int count = 0;
		
		for(OrderInfo od : Data.orderInfoList) {
			if(od.getGeneralUserCode().equals(userCode) && od.getStoreUserCode().equals(storeCode)) {
				count++;
			}
		}

	    return today + String.format("%03d", count + 1);
		
	}
	/**
	 * 리뷰 정보를 리뷰 정보 데이터 파일에 출력하는 메서드
	 */
	public static void saveReview() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(FileManager.리뷰정보));
			
			for (Review review : Data.reviewList) {
						
				writer.write(String.format("%s■%s■%s■%3.1f■%s\r\n"
									, review.getStoreUserCode()
									, review.getGeneralUserCode()
									, review.getOrderCode()
									, review.getRating()
									, review.getComment()
				));
			}
			
			writer.close();
					
		} catch (Exception e) {
			System.out.println("Data.save");
			e.printStackTrace();
		}
	}
	/**
	 * 일반회원 정보를 일반회원 데이터 파일에 출력하고, 
	 * 가맹점회원 정보를 가맹점회원 데이터 파일에 출력하는 메서드
	 */
	public static void saveUser() {
	      
		try {
	         
			BufferedWriter writer = new BufferedWriter(new FileWriter(FileManager.일반회원));
	         
			for(GeneralUser gu : Data.generalUserList) {
	            
				writer.write(String.format("%s■%s■%s■%s■%s■%s■%s■%s■%s\r\n"
									,gu.getUserCode()
									,gu.getId()
									,gu.getPwd()
									,gu.getEmail()
									,gu.getName()
									,gu.getPhoneNum()
									,gu.getAddress()
									,gu.getAddressCode()
									,gu.getDistance()
				));
	         }
			
	         writer.close();
	         
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(FileManager.가맹점회원));

			for(StoreUser su : Data.storeUserList ) {
		            
			writer.write(String.format("%s■%s■%s■%s■%s■%s■%s■%s■%s■%s■%s\r\n"
								,su.getUserCode()
								,su.getPwd()
								,su.getEmail()
								,su.getName()
								,su.getPhoneNum()
								,su.getAddress()
								,su.getAddressCode()
								,su.getDistance()
								,su.getCategoryCode()
								,su.getCategoryName()
								,su.getStoreName()
				));
			}
			writer.close();
		         
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
