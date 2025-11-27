package com.test.ygdd.data;

/**
 * 메뉴목록정보 클래스
 */
public class SelectedMenu {

	private int index;
	private int listSeq;
	private String storeUserCode;
	private String storeName;
	private String foodName;
	
	/**
	 * 메뉴목록정보 클래스 생성자
	 * @param index
	 * @param listSeq
	 * @param storeUserCode
	 * @param storeName
	 * @param foodName
	 */
	public SelectedMenu(int index, int listSeq, String storeUserCode, String storeName, String foodName) {
		this.index = index;
		this.listSeq = listSeq;
		this.storeUserCode = storeUserCode;
		this.storeName = storeName;
		this.foodName = foodName;
	}
	/**
	 * 길이 데이터를 가져오는 메서드
	 * @return
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * 길이 데이터를 수정하는 메서드
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * 목록순서 데이터를 가져오는 메서드
	 * @return
	 */
	public int getListSeq() {
		return listSeq;
	}
	/**
	 * 목록순서 데이터를 수정하는 메서드
	 * @param listSeq
	 */
	public void setListSeq(int listSeq) {
		this.listSeq = listSeq;
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
	/**
	 * 음식명 데이터를 가져오는 메서드
	 * @return
	 */
	public String getFoodName() {
		return foodName;
	}
	/**
	 * 음식명 데이터를 수정하는 메서드
	 * @param foodName
	 */
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	@Override
	public String toString() {
		return "SelectedMenu [index=" + index + ", listSeq=" + listSeq + ", storeUserCode=" + storeUserCode
				+ ", storeName=" + storeName + ", foodName=" + foodName + "]";
	}	
}
