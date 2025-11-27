package com.test.ygdd.user;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.test.ygdd.data.Address;
import com.test.ygdd.data.Category;
import com.test.ygdd.data.Data;
import com.test.ygdd.service.GeneralUserManager;
import com.test.ygdd.service.GeneralUserService;
import com.test.ygdd.service.StoreInfoManager;
import com.test.ygdd.service.StoreInfoService;
import com.test.ygdd.service.StoreUserManager;
import com.test.ygdd.service.StoreUserService;
import com.test.ygdd.ui.CommonUI;
import com.test.ygdd.ui.UserUI;

/**
 * 회원 서비스 관련. UserManager 인터페이스를 상속받는 클래스
 */
public class UserService implements UserManager {

	/**
	 * 로그인 메뉴를 출력하고, 메뉴를 입력 받는 메서드
	 */
	@Override
	public void login() {
		UserUI.optLogin(); // A01.로그인

		Scanner scan = new Scanner(System.in);

		boolean loop = true;
		while (loop) {
			String sel = scan.nextLine();

			if (sel.equals("1")) {
				// A01-01.일반회원 로그인
				loginGeneralUser();
				loop = false;

			} else if (sel.equals("2")) {
				// A01-02.가맹점회원 로그인
				loginStoreUser();
				loop = false;

			} else if (sel.equals("3")) {
				// A01-03.아이디,비밀번호 찾기
				findIdPw();
				loop = false;

			} else if (sel.equals("0")) {
				return;

			} else {
				System.out.println("다시 입력해 주세요.");
				continue;

			}
		}
	}

	/**
	 * 일반회원 로그인 정보를 받아 로그인하는 메서드
	 */
	@Override
	public void loginGeneralUser() {

		Scanner scan = new Scanner(System.in);

		boolean loop = true;
		while (loop) {

			CommonUI.header("일반회원 로그인");

			System.out.print("아이디 입력: ");
			String inputGeneralUserId = scan.nextLine();

			System.out.print("비밀번호 입력: ");
			String inputGeneralUserPwd = scan.nextLine();

			boolean checkId = false;
			boolean checkPwd = false;

			for (GeneralUser gu : Data.generalUserList) {
				if (gu.getId().equals(inputGeneralUserId)) {
					checkId = true;

					if (gu.getPwd().equals(inputGeneralUserPwd)) {
						checkPwd = true;
					}
					break;
				}
			}

			if (checkId == true && checkPwd == true) {
				LoginInfo.setCurrentId(inputGeneralUserId);
				LoginInfo.setGeneralUserCode(inputGeneralUserId);
				CommonUI.infoFooter("로그인에 성공하였습니다.\n");
				generalUserMenu();

				loop = false;

			} else {

				UserUI.optLoginFail();

				boolean loop2 = true;
				while (loop2) {
					String sel = scan.nextLine();

					if (sel.equals("1")) {
						// 다시 입력하기
						loop2 = false;
					} else if (sel.equals("0")) {
						// 뒤로가기 : [A01. 로그인]
						login();
						loop2 = false;

					} else {
						System.out.print("유효한 숫자를 입력해 주세요.");
						continue;
					}
				}
			}
		}
	}

	/**
	 * 일반회원 메뉴를 출력하고, 메뉴를 입력 받는 메서드
	 */
	@Override
	public void generalUserMenu() {
		UserUI.optGeneralUser();

		StoreInfoManager sim = new StoreInfoService();
		GeneralUserManager gum = new GeneralUserService();

		Scanner scan = new Scanner(System.in);

		boolean loop = true;
		while (loop) {

			String sel = scan.nextLine();

			if (sel.equals("1")) {
				// 1.음식 카테고리
				sim.getCategory();
				loop = false;

			} else if (sel.equals("2")) {
				// 2.장바구니
				StoreUser s = null;
				gum.getCart(1, s);
				loop = false;

			} else if (sel.equals("3")) {
				// 3.마이페이지
				gum.getMyPage();
				loop = false;

			} else if (sel.equals("0")) {
				// 0.로그아웃
				LoginInfo.logOut();
				return;
				
			} else {
				System.out.println("다시 입력해주세요.");
				continue;
			}
		}
	}

	/**
	 * 가맹점회원 로그인 정보를 받아 로그인하는 메서드
	 */
	@Override
	public void loginStoreUser() {

		Scanner scan = new Scanner(System.in);

		boolean loop = true;
		while (loop) {

			CommonUI.header("가맹점 회원 로그인");

			System.out.print("가맹점 코드 입력: ");
			String inputStoreUserId = scan.nextLine();

			System.out.print("비밀번호 입력: ");
			String inputStoreUserPwd = scan.nextLine();

			boolean checkId = false;
			boolean checkPwd = false;

			for (StoreUser su : Data.storeUserList) {
				if (inputStoreUserId.equals(su.getUserCode())) {
					checkId = true;

					if (inputStoreUserPwd.equals(su.getPwd())) {
						checkPwd = true;
					}
					break;
				}
			}

			if (checkId == true && checkPwd == true) {
				LoginInfo.setCurrentId(inputStoreUserId);
				CommonUI.infoFooter("로그인에 성공하였습니다.\n");
				storeUserMenu();
				
				loop = false;

			} else {

				UserUI.optLoginFail();

				boolean loop2 = true;
				while (loop2) {
					String storeSel = scan.nextLine();

					if (storeSel.equals("1")) {
						// 다시 입력
						loop2 = false;

					} else if (storeSel.equals("0")) {
						// 뒤로가기 : [A01. 로그인]
						login();
						loop2 = false;

					} else {
						System.out.print("유효한 숫자를 입력해 주세요.");
						continue;
					}
				}
			}
		}
	}

	/**
	 * 가맹점회원 메뉴를 출력하고, 메뉴를 입력 받는 메서드
	 */
	@Override
	public void storeUserMenu() {

		int count = Long.valueOf(
				Data.orderInfoList.stream().filter(order -> order.getStoreUserCode().equals(LoginInfo.getCurrentId()))
						.filter(order -> order.getPayStatus().equals("true"))
						.filter(order -> order.getAgreeStatus().equals("false")).count())
				.intValue();

		UserUI.optStoreUser(count);

		StoreUserManager sum = new StoreUserService();

		Scanner scan = new Scanner(System.in);

		boolean loop = true;
		while (loop) {

			String sel = scan.nextLine();

			if (sel.equals("1")) {
				// 1.메뉴 등록
				sum.createMenu();
				loop = false;

			} else if (sel.equals("2")) {
				// 2.메뉴 수정
				sum.modifyMenu();
				loop = false;

			} else if (sel.equals("3")) {
				// 3.메뉴 삭제
				sum.deleteMenu();
				loop = false;

			} else if (sel.equals("4")) {

				if (count == 0) {
					System.out.println("주문이 없습니다. 다시 입력해주세요.");
					continue;
				}
				
				// 4.주문 수락/거절
				sum.agreeOrder();
				loop = false;

			} else if (sel.equals("5")) {
				// 5.점포 관리
				sum.storeManagement();
				loop = false;

			} else if (sel.equals("0")) {
				// 0.로그아웃
				LoginInfo.logOut();
				return;

			} else {
				System.out.println("다시 입력해주세요.");
				continue;
			}
		}
	}

	/**
	 * 회원가입 메뉴를 출력하고, 메뉴를 입력 받는 메서드
	 */
	@Override
	public void join() {
		UserUI.optJoin();
		
		Scanner scan = new Scanner(System.in);
		
		boolean loop = true;
		while (loop) {
			String sel = scan.nextLine();

			if (sel.equals("1")) {
				// A02-01.일반회원 회원가입
				joinGeneral();
				loop = false;
			} else if (sel.equals("2")) {
				// A02-02.가맹점회원 회원가입
				joinStore();
				loop = false;
			} else if (sel.equals("0")) {
				// 뒤로가기
				return;
			} else {
				System.out.println("다시 입력해 주세요.");
				continue;
			}
		}
	}

	/**
	 * 가맹점회원 회원 가입 정보를 받아 회원 가입하는 메서드
	 */
	@Override
	public void joinGeneral() {
		String[] jg = new String[9];
		Scanner scan = new Scanner(System.in);
		Random rnd = new Random();

		boolean loop = true;
		while (loop) {

			System.out.print("아이디 입력:");
			String sel = scan.nextLine();

			if (sel.length() >= 4 && sel.length() <= 16) {
				int count = 0;
				for (int i = 0; i < sel.length(); i++) {
					char c = sel.charAt(i);
					if ((c < 'a' || c > 'z') && (c < '0' || c > '9')) {
						count++;
					}
				}
				if (count > 0) {
					System.out.println("유효하지 않은 아이디입니다. 처음부터 다시 입력해주세요.");
					continue;
				}
				jg[1] = sel;

			} else {
				System.out.println("유효하지 않은 아이디입니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			System.out.print("비밀번호 입력: ");
			sel = scan.nextLine();

			if (sel.length() >= 4 && sel.length() <= 16) {
				int count = 0;
				for (int i = 0; i < sel.length(); i++) {
					char c = sel.charAt(i);
					if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9')) {
						count++;
					}
				}

				if (count > 0) {
					System.out.println("유효하지 않은 비밀번호입니다. 처음부터 다시 입력해주세요.");
					continue;
				}
				jg[2] = sel;

			} else {
				System.out.println("유효하지 않은 비밀번호입니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			System.out.print("비밀번호 확인 입력: ");
			sel = scan.nextLine();

			if (!sel.equals(jg[2])) {
				System.out.println("비밀번호가 일치하지 않습니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			System.out.print("이름 입력: ");
			sel = scan.nextLine();

			if (sel.length() >= 2 && sel.length() <= 5) {
				int count = 0;
				for (int i = 0; i < sel.length(); i++) {
					char c = sel.charAt(i);
					if ((c < '가' || c > '힣')) {
						count++;
					}
				}

				if (count > 0) {
					System.out.println("유효하지 않은 이름입니다. 처음부터 다시 입력해주세요.");
					continue;
				}
				jg[4] = sel;

			} else {
				System.out.println("유효하지 않은 이름입니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			System.out.print("이메일 입력: ");
			sel = scan.nextLine();

			String regex = "([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})";

			Pattern p1 = Pattern.compile(regex);

			Matcher m1 = p1.matcher(sel);

			if (!m1.matches()) {
				System.out.println("유효하지 않은 이메일입니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			jg[3] = sel;

			System.out.print("전화번호 입력: ");
			sel = scan.nextLine();
			String num = sel.replace("-", "");

			String regexPhone = "(010)([0-9]{3,4})([0-9]{4})";

			Pattern p2 = Pattern.compile(regexPhone);

			Matcher m2 = p2.matcher(num);

			if (!m2.matches()) {
				System.out.println("유효하지 않은 전화번호입니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			String endNum = "";

			if (num.length() == 10) {
				endNum = num.substring(0, 3) + "-" + num.substring(3, 6) + "-" + num.substring(6);
			} else if (num.length() == 11) {
				endNum = num.substring(0, 3) + "-" + num.substring(3, 7) + "-" + num.substring(7);
			}
			jg[5] = endNum;

			// 주소 입력시 정확하게 입력받아야함
			System.out.print("주소 입력: ");
			sel = scan.nextLine();
			boolean adexit = true;

			for (Address ad : Data.addressList) {
				// 모든 유니코드 공백(\s), 모든 유니코드 제어문자(\p{Cntrl}), 그리고 U+200E를 명시적으로 제거
				String originalCity = ad.getCity();
				String cleanedCity = originalCity.replaceAll("[\\s\\p{Cntrl}‎]", "");

				if ((sel.replace(" ", "")).indexOf(cleanedCity) >= 0) {
					jg[7] = ad.getAddressCode();
					jg[6] = sel;
					adexit = false;
					break;
				}
			}
			if (adexit) {
				System.out.println("주소가 올바르지 않습니다. 처음부터 다시 입력해주세요.");
				continue;
			}
			jg[8] = String.valueOf((rnd.nextInt(100) + 1) * 100);

			// 거리
			String[] codeTemp = jg[5].split("-");
			String phonemid = "DD" + codeTemp[2];
			int codecount = 0;
			for (GeneralUser gu : Data.generalUserList) {
				String originalCity = gu.getUserCode();
				String cleanedCity = originalCity.replaceAll("[\\s\\p{Cntrl}‎]", "");
				if (cleanedCity.indexOf(phonemid) >= 0) {
					codecount++;
				}
			}

			phonemid += String.format("%02d", codecount + 1);

			jg[0] = phonemid;

			System.out.println("회원가입이 완료되었습니다.");

			GeneralUser op = new GeneralUser(jg[0], jg[1], jg[2], jg[3], jg[4], jg[5], jg[6], jg[7], jg[8]);

			Data.generalUserList.add(op);

			Data.saveUser();
			loop = false;
		}
	}
	/**
	 * 가맹점회원 회원 가입 정보를 받아 회원 가입하는 메서드
	 */
	@Override
	public void joinStore() {
		String[] jg = new String[11];
		Scanner scan = new Scanner(System.in);
		Random rnd = new Random();

		boolean loop = true;
		while (loop) {

			System.out.print("비밀번호 입력: ");
			String sel = scan.nextLine();

			if (sel.length() >= 4 && sel.length() <= 16) {
				int count = 0;
				for (int i = 0; i < sel.length(); i++) {
					char c = sel.charAt(i);
					if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9')) {
						count++;
					}
				}

				if (count > 0) {
					System.out.println("유효하지 않은 비밀번호입니다. 처음부터 다시 입력해주세요");
					continue;
				}

				jg[1] = sel;

			} else {
				System.out.println("유효하지 않은 비밀번호입니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			System.out.print("비밀번호 확인 입력: ");
			sel = scan.nextLine();

			if (!sel.equals(jg[1])) {
				System.out.println("비밀번호가 일치하지 않습니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			System.out.print("이름 입력: ");
			sel = scan.nextLine();

			if (sel.length() >= 2 && sel.length() <= 5) {
				int count = 0;
				for (int i = 0; i < sel.length(); i++) {
					char c = sel.charAt(i);
					if ((c < '가' || c > '힣')) {
						count++;
					}
				}

				if (count > 0) {
					System.out.println("유효하지 않은 이름입니다. 처음부터 다시 입력해주세요.");
					continue;
				}

				jg[3] = sel;

			} else {
				System.out.println("유효하지 않은 이름입니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			System.out.print("이메일 입력: ");
			sel = scan.nextLine();

			String regex = "([a-zA-Z0-9._%+-]+)@([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})";

			Pattern p1 = Pattern.compile(regex);

			Matcher m1 = p1.matcher(sel);

			if (!m1.matches()) {
				System.out.println("유효하지 않은 이메일입니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			jg[2] = sel;

			System.out.print("전화번호 입력: ");
			sel = scan.nextLine();
			String num = sel.replace("-", "");

			String regexPhone = "(010)([0-9]{3,4})([0-9]{4})";

			Pattern p2 = Pattern.compile(regexPhone);

			Matcher m2 = p2.matcher(num);

			if (!m2.matches()) {
				System.out.println("유효하지 않은 전화번호입니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			String endNum = "";

			if (num.length() == 10) {
				endNum = num.substring(0, 3) + "-" + num.substring(3, 6) + "-" + num.substring(6);
			} else if (num.length() == 11) {
				endNum = num.substring(0, 3) + "-" + num.substring(3, 7) + "-" + num.substring(7);
			}

			jg[4] = endNum;

			System.out.print("가맹점명 입력: ");
			sel = scan.nextLine();

			if (sel.length() > 20 || sel != null) {
				int count = 0;
				for (int i = 0; i < sel.length(); i++) {
					char c = sel.charAt(i);
					if ((c < '0' || c > '9') && (c < '가' || c > '힣') && (c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
						count++;
					}
				}

				if (count > 0) {
					System.out.println("유효한 가맹점명이 아닙니다. 처음부터 다시 입력해주세요.");
					continue;
				}

				jg[10] = sel;

			} else {
				System.out.println("유효한 가맹점명이 아닙니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			// 주소 입력시 정확하게 입력받아야함
			System.out.print("주소 입력: ");
			sel = scan.nextLine();
			boolean adexit = true;

			for (Address ad : Data.addressList) {
				// 모든 유니코드 공백(\s), 모든 유니코드 제어문자(\p{Cntrl}), 그리고 U+200E를 명시적으로 제거
				String originalCity = ad.getCity();
				String cleanedCity = originalCity.replaceAll("[\\s\\p{Cntrl}‎]", "");
				if ((sel.replace(" ", "")).indexOf(cleanedCity) >= 0) {
					jg[6] = ad.getAddressCode();
					jg[5] = sel;
					adexit = false;
					break;
				}
			}

			if (adexit) {
				System.out.println("주소가 올바르지 않습니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			int cgCount = 0;

			UserUI.optJoinStoreCategory();

			System.out.print("카테고리 코드 입력: ");
			sel = scan.nextLine();

			for (Category cg : Data.categoryList) {
				String originalCode = cg.getCategoryCode().toUpperCase();
				String cleanedCode = originalCode.replaceAll("[\\s\\p{Cntrl}‎]", "");

				if ((sel.trim().toUpperCase()).equals(cleanedCode)) {
					jg[8] = cg.getCategoryCode().toUpperCase();
					jg[9] = cg.getCategoryName();
					cgCount++;
					break;
				}
			}

			if (cgCount == 0) {
				System.out.println("입력받은 카테고리가 올바르지 않습니다. 처음부터 다시 입력해주세요.");
				continue;
			}

			jg[7] = String.valueOf((rnd.nextInt(100) + 1) * 100);

			String[] codeTemp = jg[4].split("-");
			String phonemid = "YG" + codeTemp[2];
			int codeCount = 0;
			for (StoreUser su : Data.storeUserList) {
				String originalCode = su.getUserCode();
				String cleanedCode = originalCode.replaceAll("[\\s\\p{Cntrl}‎]", "");
				if (cleanedCode.indexOf(phonemid) >= 0) {
					codeCount++;
				}
			}

			phonemid += String.format("%02d", codeCount + 1);

			jg[0] = phonemid;

			System.out.printf("회원가입이 완료되었습니다. 가맹점 코드는 %s입니다\r\n", jg[0]);

			StoreUser su = new StoreUser(jg[0], jg[1], jg[2], jg[3], jg[4], jg[5], jg[6], jg[7], jg[8], jg[9], jg[10]);
			Data.storeUserList.add(su);
			Data.saveUser();
			loop = false;
		}
	}

	/**
	 * 아이디/비밀번호 찾기 메뉴를 출력하고, 메뉴를 입력 받는 메서드
	 */
	@Override
	public void findIdPw() {
		UserUI.optFindIdPw(); // A03.아이디,비밀번호찾기

		Scanner scan = new Scanner(System.in);
		String sel = scan.nextLine();

		if (sel.equals("1")) {
			findId(); // A03-01.아이디 찾기
		} else if (sel.equals("2")) {
			findPw(); // A03-02.비밀번호 찾기
		} else if (sel.equals("0")) {
			return; // 초기화면
		} else {
			System.out.println("다시 입력해 주세요.");
		}
	}

	/**
	 * 아이디 찾기 정보를 받아 아이디를 찾는 메서드
	 */
	@Override
	public void findId() {
		String select;
		Scanner scan = new Scanner(System.in);

		boolean loop = true;
		while (loop) {
			UserUI.optSelectId();

			select = scan.nextLine();
			String id = "";

			if (select.equals("1")) {
				System.out.print("이름 입력:");
				String name = scan.nextLine();
				System.out.print("이메일 입력:");
				String email = scan.nextLine();

				int error = 0;

				for (GeneralUser gu : Data.generalUserList) {
					String originalName = gu.getName();
					String cleanedName = originalName.replaceAll("[\\s\\p{Cntrl}‎]", "");
					String originalEmail = gu.getEmail();
					String cleanedEmail = originalEmail.replaceAll("[\\s\\p{Cntrl}‎]", "");

					if (name.equals(cleanedName) && email.equals(cleanedEmail)) {
						id = gu.getId();
						error++;
						break;
					}
				}
				if (error == 0) {
					System.out.println("이름/이메일이 유효하지않습니다. 다시 입력해주세요.");
					continue;
				}

			} else if (select.equals("2")) {

				System.out.print("이름 입력:");
				String name = scan.nextLine();

				System.out.print("이메일 입력:");
				String email = scan.nextLine();

				int error = 0;

				for (StoreUser su : Data.storeUserList) {
					String originalName = su.getName();
					String cleanedName = originalName.replaceAll("[\\s\\p{Cntrl}‎]", "");
					String originalEmail = su.getEmail();
					String cleanedEmail = originalEmail.replaceAll("[\\s\\p{Cntrl}‎]", "");
					if (name.equals(cleanedName) && email.equals(cleanedEmail)) {
						id = su.getUserCode();
						error++;
						break;
					}
				}

				if (error == 0) {
					System.out.println("이름/이메일이 유효하지않습니다. 다시 입력해주세요.");
					continue;
				}

			} else if (select.equals("0")) {
				break;
			} else {
				System.out.println("잘못된 입력값입니다 다시 입력해주세요.");
				continue;
			}

			System.out.printf("해당 아이디/가맹점코드는 %s 입니다\r\n", id);
			loop = false;

		}
	}

	/**
	 * 비밀번호 찾기 정보를 받아 아이디를 찾는 메서드
	 */
	@Override
	public void findPw() {
		Scanner scan = new Scanner(System.in);
		String select;

		boolean loop = true;
		while (loop) {
			UserUI.optSelectPw();
			select = scan.nextLine();

			if (select.equals("1")) {

				System.out.print("이름 입력:");
				String name = scan.nextLine();

				System.out.print("아이디 입력:");
				String id = scan.nextLine();

				System.out.print("이메일 입력:");
				String email = scan.nextLine();

				int error = 0;

				for (GeneralUser gu : Data.generalUserList) {
					String originalName = gu.getName();
					String cleanedName = originalName.replaceAll("[\\s\\p{Cntrl}‎]", "");
					String originalId = gu.getId();
					String cleanedId = originalId.replaceAll("[\\s\\p{Cntrl}‎]", "");
					String originalEmail = gu.getEmail();
					String cleanedEmail = originalEmail.replaceAll("[\\s\\p{Cntrl}‎]", "");

					if (name.equals(cleanedName) && email.equals(cleanedEmail) && id.equals(cleanedId)) {
						error++;

						System.out.println("변경할 비밀번호를 입력하세요.");

						System.out.print("비밀번호 입력: ");
						String pass = scan.nextLine();

						System.out.print("비밀번호 확인: ");
						String passCheck = scan.nextLine();

						if (pass.equals(passCheck)) {

							if (passCheck.length() >= 4 && passCheck.length() <= 16) {
								int count = 0;
								for (int i = 0; i < passCheck.length(); i++) {
									char c = passCheck.charAt(i);
									if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9')) {
										count++;
									}
								}

								if (count > 0) {
									System.out.println("유효하지 않은 비밀번호입니다. 처음부터 다시 입력해주세요");
									continue;
								}

								gu.setPwd(passCheck);
							} else {
								System.out.println("유효하지 않은 비밀번호입니다. 처음부터 다시 입력해주세요.");
								continue;
							}

						} else {
							System.out.println("유효하지 않은 비밀번호입니다. 처음부터 다시 입력해주세요.");
							continue;
						}

						Data.saveUser();
						System.out.println("비밀번호가 변경되었습니다.");

						break;
					}
				}

				if (error == 0) {
					System.out.println("입력값이 잘못되었습니다 다시 입력해주세요.");
				}
			} else if (select.equals("2")) {

				System.out.print("이름 입력:");
				String name = scan.nextLine();

				System.out.print("가맹점코드 입력:");
				String code = scan.nextLine();

				System.out.print("이메일 입력:");
				String email = scan.nextLine();

				int error = 0;

				for (StoreUser su : Data.storeUserList) {
					String originalName = su.getName();
					String cleanedName = originalName.replaceAll("[\\s\\p{Cntrl}‎]", "");
					String originalCode = su.getUserCode();
					String cleanedCode = originalCode.replaceAll("[\\s\\p{Cntrl}‎]", "");
					String originalEmail = su.getEmail();
					String cleanedEmail = originalEmail.replaceAll("[\\s\\p{Cntrl}‎]", "");

					if (name.equals(cleanedName) && email.equals(cleanedEmail) && code.equals(cleanedCode)) {
						error++;

						System.out.println("변경할 비밀번호를 입력하세요.");

						System.out.print("비밀번호 입력: ");
						String pass = scan.nextLine();

						System.out.print("비밀번호 확인: ");
						String passCheck = scan.nextLine();

						if (pass.equals(passCheck)) {

							if (passCheck.length() >= 4 && passCheck.length() <= 16) {
								int count = 0;

								for (int i = 0; i < passCheck.length(); i++) {
									char c = passCheck.charAt(i);
									if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c < '0' || c > '9')) {
										count++;
									}
								}

								if (count > 0) {
									System.out.println("유효하지 않은 비밀번호입니다. 처음부터 다시 입력해주세요");
									continue;
								}

								su.setPwd(passCheck);

							} else {
								System.out.println("유효하지 않은 비밀번호입니다. 처음부터 다시 입력해주세요.");
								continue;
							}

						} else {
							System.out.println("유효하지 않은 비밀번호입니다. 처음부터 다시 입력해주세요.");
							continue;
						}

						Data.saveUser();
						System.out.println("비밀번호가 변경되었습니다.");

						break;
					}
				}

				if (error == 0) {
					System.out.println("입력값이 잘못되었습니다 다시 입력해주세요.");
				}

			} else if (select.equals("0")) {
				break;
			} else {
				System.out.println("잘못된 입력값입니다 다시 입력해주세요.");
				continue;
			}
		}
	}
}
