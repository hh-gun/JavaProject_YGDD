package com.test.ygdd.data;

/**
 * 결제 및 주문수락/거절 정보 클래스
 */
public class OrderInfo {
	
	private String storeUserCode;
	private String generalUserCode;
	private String orderCode;
	private String payStatus;
	private String agreeStatus;
	
	/**
	 * 결제 및 주문수락/거절 정보 클래스 생성자
	 * @param storeUserCode
	 * @param generalUserCode
	 * @param orderCode
	 * @param payStatus
	 * @param agreeStatus
	 */
	public OrderInfo(String storeUserCode, String generalUserCode, String orderCode, String payStatus,
			String agreeStatus) {
		this.storeUserCode = storeUserCode;
		this.generalUserCode = generalUserCode;
		this.orderCode = orderCode;
		this.payStatus = payStatus;
		this.agreeStatus = agreeStatus;
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
	/**
	 * 결제정보 데이터를 가져오는 메서드
	 * @return
	 */
	public String getPayStatus() {
		return payStatus;
	}
	/**
	 * 결제정보 데이터를 수정하는 메서드
	 * @param payStatus
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	/**
	 * 주문수락/거절 데이터를 가져오는 메서드
	 * @return
	 */
	public String getAgreeStatus() {
		return agreeStatus;
	}
	/**
	 * 주문수락/거절 데이터를 수정하는 메서드
	 * @param agreeStatus
	 */
	public void setAgreeStatus(String agreeStatus) {
		this.agreeStatus = agreeStatus;
	}
	
	@Override
	public String toString() {
		return "OrderInfo [storeUserCode=" + storeUserCode + ", generalUserCode=" + generalUserCode + ", orderCode="
				+ orderCode + ", payStatus=" + payStatus + ", agreeStatus=" + agreeStatus + "]";
	} 
	
}
