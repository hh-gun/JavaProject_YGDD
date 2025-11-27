package com.test.ygdd.user;

/**
 * 일반회원 정보. User 추상클래스를 상속 받는 클래스
 */
public class GeneralUser extends User{

	private String id;

	/**
	 * 일반회원 정보 클래스의 생성자.
	 * @param userCode
	 * @param id
	 * @param pwd
	 * @param email
	 * @param name
	 * @param phoneNum
	 * @param address
	 * @param addressCode
	 * @param distance
	 */
	public GeneralUser(String userCode, String id, String pwd, String email, String name, String phoneNum, String address, String addressCode, String distance) {
		
		super(userCode, pwd, email, name, phoneNum, address, addressCode, distance);
		this.id = id;
		
	}
	/**
	 * 일반회원 id 데이터를 가져오는 메서드
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * 일반회원 id 데이터를 수정하는 메서드
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "GeneralUser [id=" + id + "]";
	}
	
}
