package com.test.ygdd.service;

import java.util.ArrayList;

import com.test.ygdd.data.UserCarts;
import com.test.ygdd.user.StoreUser;
/**
 * GeneralUserService()를 상속하는 인터페이스
 */
public interface GeneralUserManager {
	
	void getCart(int callLocation, StoreUser selectedStore);
	void getMyPage();
	void doDelete(ArrayList<UserCarts> cartlist, int callLocation);
	void doOrder();
	void reviewManagement();
	void writeReview();
	void orderHistory();
	double createRating();
	String createReview();
	
}
