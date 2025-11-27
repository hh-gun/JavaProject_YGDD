package com.test.ygdd.user;

/**
 * 회원들의 공통정보 추상 클래스
 */
public abstract class User {

	private String userCode;
	private String pwd;
	private String email;
	private String name;
	private String phoneNum;
	private String address;
	private String addressCode;
	private String distance;
	
	/**
	 * 회원들의 공통정보 클래스 생성자
	 * @param userCode
	 * @param pwd
	 * @param email
	 * @param name
	 * @param phoneNum
	 * @param address
	 * @param addressCode
	 * @param distance
	 */
	public User(String userCode, String pwd, String email, String name, String phoneNum, String address,
			String addressCode, String distance) {

		this.userCode = userCode;
		this.pwd = pwd;
		this.email = email;
		this.name = name;
		this.phoneNum = phoneNum;
		this.address = address;
		this.addressCode = addressCode;
		this.distance = distance;
		
	}
	/**
	 * 회원 코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getUserCode() {
		return userCode;
	}
	/**
	 * 회원 코드 데이터를 수정하는 메서드
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**
	 * 회원 비밀번호 데이터를 가져오는 메서드
	 * @return
	 */
	public String getPwd() {
		return pwd;
	}
	/**
	 * 회원 비밀번호 데이터를 수정하는 메서드
	 * @param pwd
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * 회원 이메일 데이터를 가져오는 메서드
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 회원 이메일 데이터를 수정하는 메서드
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 회원 이름 데이터를 가져오는 메서드
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 회원 이름 데이터를 수정하는 메서드
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 회원 전화번호 데이터를 가져오는 메서드
	 * @return
	 */
	public String getPhoneNum() {
		return phoneNum;
	}
	/**
	 * 회원 전화번호 데이터를 수정하는 메서드
	 * @param phoneNum
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	/**
	 * 회원 주소 데이터를 가져오는 메서드
	 * @return
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 회원 주소 데이터를 수정하는 메서드
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 회원 주소코드 데이터를 가져오는 메서드
	 * @return
	 */
	public String getAddressCode() {
		return addressCode;
	}
	/**
	 * 회원 주소코드 데이터를 수정하는 메서드
	 * @param addressCode
	 */
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	/**
	 * 거리 데이터를 가져오는 메서드
	 * @return
	 */
	public String getDistance() {
		return distance;
	}
	/**
	 * 거리 데이터를 수정하는 메서드
	 * @param distance
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}


	
	
	
}
