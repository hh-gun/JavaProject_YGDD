package com.test.ygdd.user;

/**
 * 가맹점회원 정보. User 추상클래스를 상속 받는 클래스
 */
public class StoreUser extends User {

	private String categoryCode;
	private String categoryName;
	private String storeName;
	
	/**
	 * 가맹점회원 정보 클래스 생성자.
	 * @param userCode
	 * @param pwd
	 * @param email
	 * @param name
	 * @param phoneNum
	 * @param address
	 * @param addressCode
	 * @param distance
	 * @param categoryCode
	 * @param categoryName
	 * @param storeName
	 */
	public StoreUser(String userCode, String pwd, String email, String name, String phoneNum, String address,
			String addressCode, String distance, String categoryCode, String categoryName, String storeName) {
		
		super(userCode, pwd, email, name, phoneNum, address, addressCode, distance);
		
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.storeName = storeName;
	}

	/**
	 * 카테고리코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getCategoryCode() {
		return categoryCode;
	}
	/**
	 * 카테고리코드 데이터를 수정하는 메서드
	 * @param categoryCode
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * 카테고리명 데이터를 가져오는 메서드
	 * @return
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * 카테고리명 데이터를 수정오는 메서드
	 * @param categoryName
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * 가게명 데이터를 가져오는 메서드
	 * @return
	 */
	public String getStoreName() {
		return storeName;
	}
	/**
	 * 가게명 데이터를 수정하는 메서드
	 * @param storeName
	 */
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Override
	public String toString() {
		return "StoreUser [categoryCode=" + categoryCode + ", categoryName=" + categoryName + ", storeName=" + storeName
				+ "]";
	}
	
	
	
}
