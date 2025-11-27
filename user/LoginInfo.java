package com.test.ygdd.user;

import com.test.ygdd.data.Data;

/**
 * 비회원 메뉴 클래스
 */
public class LoginInfo {

	private static String currentId;
	private static String generalUserCode;

	/**
	 * 현재 로그인한 회원 데이터를 가져오는 메서드
	 * @return
	 */
	public static String getCurrentId() {
		return currentId;
	}
	/**
	 * 현재 로그인한 회원 데이터를 수정하는 메서드
	 * @param id
	 */
	public static void setCurrentId(String id) {
		currentId = id;
	}
	/**
	 * 일반회원을 로그아웃 시키는 메서드
	 */
	public static void logOut() {
		currentId = null;
		generalUserCode = null;
	}
	/**
	 * 일반회원 코드 데이터를 가져오는 메서드
	 * @return
	 */
	public static String getGeneralUserCode() {
		return generalUserCode;
	}
	/**
	 * 일반회원 코드 데이터를 수정하는 메서드
	 * @param generalUserId
	 */
	public static void setGeneralUserCode(String generalUserId) {
		String loginUserCode = "";

		for (GeneralUser gu : Data.generalUserList) {
			if (gu.getId().equals(generalUserId)) {
				loginUserCode = gu.getUserCode();
			}
		}
		
		if (loginUserCode != "") {
			generalUserCode = loginUserCode;
		}
	}

	@Override
	public String toString() {
		return "LoginInfo [id=" + currentId + "]";
	}
	
	
}
