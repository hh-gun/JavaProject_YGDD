package com.test.ygdd.data;

/**
 * 리뷰정보 클래스
 */
public class Review {
	
	private String storeUserCode;
	private String generalUserCode;
	private String orderCode;
	private double rating;
	private String comment;
	
	/**
	 * 리뷰정보 클래스 생성자
	 * @param storeUserCode
	 * @param generalUserCode
	 * @param orderCode
	 * @param rating
	 * @param comment
	 */
	public Review(String storeUserCode, String generalUserCode, String orderCode, double rating, String comment) {
		this.storeUserCode = storeUserCode;
		this.generalUserCode = generalUserCode;
		this.orderCode = orderCode;
		this.rating = rating;
		this.comment = comment;	
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
	 * 평점 데이터를 가져오는 메서드
	 * @return
	 */
	public double getRating() {
		return rating;
	}
	/**
	 * 평점 데이터를 수정하는 메서드
	 * @param rating
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}
	/**
	 * 리뷰댓글 데이터를 가져오는 메서드
	 * @return
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * 리뷰댓글 데이터를 수정는 메서드
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Review [storeUserCode=" + storeUserCode + ", generalUserCode=" + generalUserCode + ", orderCode="
				+ orderCode + ", rating=" + rating + ", comment=" + comment + "]";
	}
}
