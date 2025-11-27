package com.test.ygdd;

import java.util.Scanner;

import com.test.ygdd.data.Data;
import com.test.ygdd.ui.CommonUI;
import com.test.ygdd.user.LoginInfo;
import com.test.ygdd.user.UserManager;
import com.test.ygdd.user.UserService;
/**
 * 실행 메인화면 클래스
 */
public class Main {
	
	/**
	 * 초기화면 메뉴를 출력하고, 메뉴를 입력 받는 메서드
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			Data.load();
			
			boolean loop = true;
			while (loop) {
			
				if (LoginInfo.getCurrentId() != null) {
					LoginInfo.logOut();
				}
				
				//초기화면
				CommonUI.mainMenu();
				
				Scanner scan = new Scanner(System.in);
				if (!scan.hasNextLine()) {
					scan.nextLine();
				}
				String sel = scan.nextLine();
				
				UserManager um = new UserService(); 
				
				if (sel.equals("1")) {
					//A01.로그인
					um.login();
					
				} else if (sel.equals("2")) {
					//A02.회원가입
					um.join();
					
				} else if (sel.equals("3")) {
					//A03.아이디,비밀번호 찾기
					um.findIdPw();
					
				} else if (sel.equals("4")) {
					//프로그램 종료
					loop = false;
					System.out.println("시스템 종료");
					
				} else {
					System.out.println("다시 입력해 주세요.");
				}
			}
		} catch (Exception e) {
			System.out.println("Main.main");
			e.printStackTrace();
		}
	}
}
