package com.test.ygdd.user;

/**
 * UserService()를 상속하는 인터페이스
 */
public interface UserManager {

	void login();
	void loginGeneralUser();
	void generalUserMenu();
	void loginStoreUser();
	void storeUserMenu();
	void join();
	void joinGeneral();
	void joinStore();
	void findIdPw();
	void findId();
	void findPw();
	
}
