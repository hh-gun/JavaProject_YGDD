package com.test.ygdd.service;

import com.test.ygdd.user.StoreUser;

/**
 * GeneralUserService()를 상속하는 인터페이스
 */
public interface StoreInfoManager {

	void getCategory();
	void getStoreList();
	void getStoreInfo();
	void getStoreDetail();
	void getReview();
	void getMenu(StoreUser su);
	
}
