import java.util.Scanner;

public class VendingMachine {
	static int cup = 3; // 현재 남아있는 종이컵의 개수
	static long money; // 받을 돈
	static int sum = 0; //최종 지불 금액
	static int count; // 뽑을 잔 수
	static char ice = 0; //얼음 투입 여부
	static int con = -1; // 재료 수량 파악
	static int [] drinks_cost = { 300, 400, 500, 200}; // 음료 가격
	static String [] menu = { "밀크 커피" , "설탕 커피" , "율무차", "녹차" ,}; // 음료 종류
	static String [] ingres = { "물", "커피" , "프림" , "설탕", "율무차 가루", "녹차 가루" }; // 음료 재료
	static int [] stored_ingres = { 30, 10, 10, 10, 10, 10 }; //자판기에 남은 재료
	static double [][] recipe = { { 5, 1, 2, 1.5, 0, 0 }, { 5, 1, 0, 0.8, 0, 0 }, { 5, 0, 0, 0, 1, 0 }, { 5, 0, 0, 0, 0, 1 } };
	
	public static void main(String[] args) {
		
		
		char finished = 0; //자판기 종료 여부를 결정, 임의로 Y를 입력하면 종료된다고 가정.
		Scanner input = new Scanner(System.in);
		
		
			menu();
			System.out.println("\t\t 어서오세요!\n\t    음료 메뉴를 보시고");
			System.out.println("\t    ♬돈을 넣어주세요 ♬");
		
			
			money = input.nextInt();
			System.out.println("투입 된 돈 : "+ money);
			
			do {
				menu();
				System.out.println("원하는 메뉴의 번호를 입력해주세요");
				
				int order = input.nextInt();
				
				if( order >= 1 && order <= 4 ) {order(order);}
				else if (order == 5) {extraChange(); System.out.println("안녕히 가세요!"); return;}
				else if (order == 6) {new Manager();}
				else System.out.println("주문 내에서 골라주세요.");
			
			System.out.println("시스템 종료 여부를 선택해 주세요 (n 외의 아무 키나 누르면 시스템이 종료됩니다.)");
			finished = input.next().charAt(0);
			
		}while(finished == 'n');
		
		if(money>0)
		extraChange();
		
}
	static void menu() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("★★★★★5조의 커피 자판기★★★★");
		
		for(int i=0;i<4;i++) {
			System.out.println("■  " + (i+1) +"."+menu[i]+" : "+drinks_cost[i]+"원");
		}
		System.out.println("■  5. 잔돈 반환");
		System.out.println("■  6. 관리자 호출");
		System.out.println("모든 메뉴 얼음 추가: 100원");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■");
	}
	
	static void extraChange() { // 잔돈 반환
		int coin5 = 0;
		long m = money;
		while ( money >= 500 ) {
			money = money - 500;
			coin5++;
		}
		System.out.println( "남은 동전을 반환합니다..." );
		System.out.println( "잔돈 " + m + "원이 500원 동전 " + coin5 + " 개와 100원 동전 " + ( money / 100 ) + " 개가 반환구로 나왔습니다." );
		money=0;
		}
	
	static public void lack(int c) { // 컵 채우기
		System.out.print("종이컵을 리필하시겠습니까?(Y/N)\n");
		Scanner sc = new Scanner(System.in);
		String ans = sc.next();
		if(ans.equals("Y")||ans.equals("y")) {
			cup=c;
			System.out.println("종이컵이 채워졌습니다.");
		}
		else if(ans.equals("N")||ans.equals("n")) {
			System.out.print("종이컵 수량이 부족하므로 돈을 반환합니다.\n");
			extraChange();
		}
		else {
			System.out.print("입력이 올바르지 않습니다. 다시 돌아갑니다.\n");
			lack(c);
		}
	
}
	static public void order(int a)
	{	
	if( (money >= drinks_cost[a-1]) && cup>0) 
		{ System.out.println( menu[a-1] + "를 선택하셨습니다.");
		System.out.print( menu[a-1]+"를 총 몇 잔 뽑으시겠습니까? : ");
		Scanner sc = new Scanner(System.in);
		count = sc.nextInt();
		for ( int i = 0; i < 6; i++ ) { // 재료의 량을 고려하는 코드
			if ( recipe[a - 1][i] * count > stored_ingres[i] ) {
				con = i;
				break;
			}
		}
		if ( con != -1 ){
			System.out.println(menu[a -1] + "에 필요한 재료 " + ingres[con] + "이/가 부족합니다.");
			System.out.println("관리자를 호출하시겠습니까?\n1. 예   /   2. 아니오");
			
		    int menu = sc.nextInt();
		    if ( menu == 1 )
		    	new Manager();
		    else {System.out.println("재료가 부족하므로 주문을 받을 수 없습니다. 돈을 반환합니다."); extraChange(); return;}
		}
		
		System.out.println("얼음을 추가하시겠습니까? ( y / n 입력)");
		ice = sc.next().charAt(0);
		
		if(ice == 'y')
			ice_yes(a);
		else
			ice_no(a);
		}
	else if (money < drinks_cost[a-1])
			{ System.out.println("금액이 부족합니다. 돈을 더 투입해주세요. \n투입 금액: ");
			Scanner sc = new Scanner(System.in);
			int monplus = sc.nextInt();
			money+=monplus; }
	else if (cup == 0)
		{ System.out.println("컵이 부족합니다. 컵을 채운 후 다시 이용해주세요.");
		System.out.println("관리자를 호출하시겠습니까?\n1. 예   /   2. 아니오");
	      Scanner sc = new Scanner(System.in);
	      int menu = sc.nextInt();
	      if(menu==1) new Manager();
	      else {System.out.print("종이컵 수량이 부족하므로 돈을 반환합니다.\n"); extraChange();}
	      }
	

	}
	static public void ice_yes(int a) {
		sum= (drinks_cost[a-1]+100)*count;
		if( money < sum ) {
			System.out.println("금액이 부족합니다. 돈을 더 투입해주세요. \n투입금액: ");
			Scanner sc= new Scanner(System.in);
			int monplus = sc.nextInt();
			money+=monplus;
			}	
		else if( cup < count ) {
				System.out.println("종이컵이 다 떨어졌습니다.");
				System.out.println("관리자를 호출하시겠습니까?\n1. 예   /   2. 아니오");
			      Scanner sc = new Scanner(System.in);
			      int menu = sc.nextInt();
			      if(menu==1) new Manager();
			      else {System.out.print("종이컵 수량이 부족하므로 돈을 반환합니다.\n"); extraChange();}
			}
		else {
			System.out.println("쪼르륵...");
			System.out.println( "아이스 "+ menu[a-1] + "가 " + count + "잔 나왔습니다.");
			for ( int i = 0; i < 6; i++ ) // 원료 사용 시 감소
				stored_ingres[i] -= count*recipe[a-1][i];
			money-=sum;
			cup-=count;
			System.out.println(" / 남은 돈: " + money);}
		}
	
	static public void ice_no(int a) {
		sum= (drinks_cost[a-1])*count;
		if( money < sum ) {
			System.out.print("금액이 부족합니다. 돈을 더 투입해주세요. \n투입 금액:");
			Scanner scanner = new Scanner(System.in);
			int monplus = scanner.nextInt();
			money+=monplus;
			}	
			else if( cup < count ) {
				System.out.println("종이컵이 다 떨어졌습니다.");
				System.out.println("관리자를 호출하시겠습니까?\n1. 예   /   2. 아니오");
			      Scanner sc = new Scanner(System.in);
			      int menu = sc.nextInt();
			      if(menu==1) new Manager();
			      else {System.out.print("종이컵 수량이 부족하므로 돈을 반환합니다.\n"); extraChange();}
			}
			else {

		System.out.println("쪼르륵...");
		System.out.println( menu[a-1] + "가 " + count + "잔 나왔습니다.");
		for ( int i = 0; i < 6; i++ ) { // 원료 사용 시 감소
			stored_ingres[i] -= (count*recipe[a-1][i]); }
		money-=sum;
		cup-=count;
		System.out.println(" / 남은 돈: " + money);}
	}
	
	static class Manager{
		static int passwd = 1234;
		
		Manager() { 
			System.out.print("관리자 번호 4자리를 입력해주세요 : ");
			Scanner scanner = new Scanner(System.in);
			int manNum = scanner.nextInt();
			if(manNum == passwd) {
				System.out.println("관리자 계정으로 접속 중 입니다...");
				System.out.println("원하는 메뉴를 선택해 주세요.");
				System.out.println("1. 컵 리필");
				System.out.println("2. 재료 리필");
				System.out.println("3. 비밀번호 변경");
				int menu = scanner.nextInt();
				if (menu==1) {
				    System.out.println("컵을 얼마나 리필하시겠습니까?");
				    Scanner s= new Scanner(System.in);
				    int cupNum = s.nextInt();
				    lack(cupNum); }
				else if (menu==2) {
					for(int i=0;i<6;i++) stored_ingres[i] = 100;
					System.out.println("재료를 모두 채워넣었습니다.");
				}
				else if (menu==3) {
					System.out.println("비밀 번호를 변경합니다.");
					chPasswd();
				}
			}
			else {System.out.println("관리자 번호를 잘못 입력하셨습니다."); new Manager();}
		}
		static public void chPasswd() {//비번 변경
			System.out.print("바꿀 비밀번호 4자리를 입력해주세요 : ");
			Scanner p = new Scanner(System.in);
			int changeP = p.nextInt();
			System.out.print("다시 한번 입력해 주세요 : ");
			Scanner p2 = new Scanner(System.in);
			if(p2.nextInt() == changeP) {
				passwd = changeP;
			}
			else {
				System.out.println("변경하려는 비밀번호와 맞지 않습니다. 처음으로 돌아갑니다.");
				chPasswd();
			}
		}
	}
	}