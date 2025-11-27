package com.test.ygdd.ui;

/**
 * 결제 관련 UI 클래스
 */
public class PayUI {
	
	/**
	 * 결제 동의 상단 UI 출력 메서드
	 */
	public static void payAgreeHeader() {
		CommonUI.header("주문 결제확인");
		System.out.println("		      💲 PAY");
	}
	/**
	 * 결제 동의 하단 UI 출력 메서드
	 */
	public static void payAgreeFooter() {
		System.out.println("* 결제하시겠습니까? (Y/N)");
		System.out.println("🙆‍♂️ 결제		······························· [Y]");
		System.out.println("🔙 뒤로가기	······························· [N]");
		System.out.println("═════════════════════════════════════════════════════");
	}
	/**
	 * 결제 완료 UI 출력 메서드
	 */
	public static void payFinish() {
		CommonUI.header("결제완료");
		System.out.println("* 결제가 완료되었습니다!");
		System.out.println("* 뒤로 가시려면 엔터를 눌러주세요");
	}

}
