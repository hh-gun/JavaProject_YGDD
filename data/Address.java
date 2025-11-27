package com.test.ygdd.data;

/**
 * 주소 코드 정보 클래스
 */
public class Address {
	
	private String addressCode;
	private String city;
	
	/**
	 * 주소 코드 정보 클래스 생성자
	 * @param addressCode
	 * @param city
	 */
	public Address(String addressCode, String city) {
		this.addressCode = addressCode;
		this.city = city;
	}
	/**
	 * 주소 코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getAddressCode() {
		return addressCode;
	}
	/**
	 * 주소 코드 데이터를 수정 메서드
	 * @param addressCode
	 */
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	/**
	 * 도/시 데이터를 가져오는 메서드
	 * @return
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 도/시 데이터를 수정하는 메서드
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 객체의 정보를 문자열로 반환하는 메서드
	 */
	@Override
	public String toString() {
		return "Address [addressCode=" + addressCode + ", city=" + city + "]";
	}
	
}
