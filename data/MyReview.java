package com.test.ygdd.data;
/**
 * 나의 리뷰정보 클래스
 */
public class MyReview {

	private int index;
	private String storeUserCode;
	private String generalUserCode;
	private String orderCode;
	
	/**
	 * 나의 리뷰정보 클래스 생성자
	 * @param index
	 * @param storeUserCode
	 * @param generalUserCode
	 * @param orderCode
	 */
	public MyReview(int index, String storeUserCode, String generalUserCode, String orderCode) {
		this.index = index;
		this.storeUserCode = storeUserCode;
		this.generalUserCode = generalUserCode;
		this.orderCode = orderCode;
	}
	
	/**
	 * 리뷰길이 데이터를 가져오는 메서드
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * 리뷰길이 데이터를 수정하는 메서드
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * 가맹점회원코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getStoreUserCode() {
		return storeUserCode;
	}
	/**
	 * 가맹점회원코드 데이터를 수정하는 메서드
	 * @param storeUserCode
	 */
	public void setStoreUserCode(String storeUserCode) {
		this.storeUserCode = storeUserCode;
	}
	/**
	 * 일반회원코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getGeneralUserCode() {
		return generalUserCode;
	}
	/**
	 * 일반회원코드 데이터를 수정하는 메서드
	 * @param generalUserCode
	 */
	public void setGeneralUserCode(String generalUserCode) {
		this.generalUserCode = generalUserCode;
	}
	/**
	 * 주문코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getOrderCode() {
		return orderCode;
	}
	/**
	 * 주문코드 데이터를 수정하는 메서드
	 * @param orderCode
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

}
