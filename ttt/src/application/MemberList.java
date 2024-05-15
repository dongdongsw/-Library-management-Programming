package application;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MemberList {
	
	private static MemberList instance;
	private ObservableList<Member> Members = FXCollections.observableArrayList();
	
	public Member findMemberByName(String name) {
		for(Member member : Members) {
			if(member.getMember_Name().equals(name)) {
				return member;
			}
		}
		return null; // Return null if no member Found
	}
	public MemberList() {
        // 초기 멤버 데이터 추가
        Members.add(new Member("1", "Jihye01", "이지혜", "O", 5, "000000", "111"));
        Members.add(new Member("2", "Dongbin89", "손동빈", "O", 5,"000001", "114"));
        Members.add(new Member("3", "Hyemi98", "전혜미", "O", 5,"000002", "131"));
        Members.add(new Member("4", "Youngyoon10", "서영윤", "O", 5, "000003", "121"));
        Members.add(new Member("5", "Minsu05", "정민수", "O", 5, "000003", "121"));
        Members.add(new Member("6", "Jaesik04", "배재식", "X", 5, "000003", "121"));
        Members.add(new Member("7", "Jinju07", "황진주", "O", 5, "000003", "121"));
        Members.add(new Member("8", "MinsuK11", "김민수", "X", 5, "000003", "121"));
        Members.add(new Member("9", "DongJun68", "한동준", "O", 0, "000003", "121"));
        Members.add(new Member("10", "Yongil06", "문용일", "O", 5, "000003", "121"));
        Members.add(new Member("11", "Heywon99", "조혜원", "O", 5, "000003", "121"));
        Members.add(new Member("12", "Gwangmin00", "김광민", "O", 5, "000003", "121"));
        Members.add(new Member("13", "Gwangmin07", "김광민", "O", 5, "000003", "121"));
        Members.add(new Member("14", "Jihye98", "이지혜", "O", 5, "000003", "121"));
        Members.add(new Member("15", "Minu02", "최민우", "O", 5, "000003", "121"));
        Members.add(new Member("16", "Sohyen01", "풍소현", "X", 5, "000003", "121"));
        Members.add(new Member("17", "Yeseong02", "김예성", "O", 5, "000003", "121"));
        Members.add(new Member("18", "Minu97", "이민우", "O", 5, "000003", "121"));
        Members.add(new Member("19", "Yongu01", "고용우", "O", 5, "000003", "121"));
        Members.add(new Member("20", "Howon00", "강효원", "O", 5, "000003", "121"));
        
        
        
        updateCheckOutLimits();
	}

	public static synchronized MemberList getInstance() {
		if(instance == null) {
			instance = new MemberList();
		}
		return instance;
	}
	// 멤버들의 CheckOutLimit 업데이트
    private void updateCheckOutLimits() {
        for (Member member : Members) {
            if ("X".equals(member.getCheckOutEligible())) {
                member.setCheckOutLimit(0);
            }
        }
    }
	
    public ObservableList<Member> getMembers() {
        return Members;
    }
    

 
	 
}
