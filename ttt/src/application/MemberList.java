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
        Members.add(new Member("1", "Jihye01", "이지혜", "O", 3, "011112", "010-1111-1111"));
        Members.add(new Member("2", "Dongbin89", "손동빈", "O", 3,"890525", "010-2222-2222"));
        Members.add(new Member("3", "Hyemi98", "전혜미", "O", 3,"981111", "010-3333-3333"));
        Members.add(new Member("4", "Youngyoon10", "서영윤", "O", 3, "100730", "010-4444-4444"));
        Members.add(new Member("5", "Minsu05", "정민수", "O", 3, "050405", "010-5555-5555"));
        Members.add(new Member("6", "Jaesik04", "배재식", "X", 3, "040502", "010-6666-6666"));
        Members.add(new Member("7", "Jinju07", "황진주", "O", 3, "070806", "010-7777-7777"));
        Members.add(new Member("8", "MinsuK11", "김민수", "X", 3, "111015", "010-8888-8888"));
        Members.add(new Member("9", "DongJun68", "한동준", "O", 3, "680516", "010-9999-9999"));
        Members.add(new Member("10", "Yongil06", "문용일", "O", 3, "061217", "010-1112-1112"));
        Members.add(new Member("11", "Heywon99", "조혜원", "O", 3, "990225", "010-2223-2223"));
        Members.add(new Member("12", "Gwangmin00", "김광민", "O", 3, "000111", "010-3334-3334"));
        Members.add(new Member("13", "Gwangmin07", "김광민", "O", 3, "070608", "010-4445-4445"));
        Members.add(new Member("14", "Jihye98", "이지혜", "O", 3, "981217", "010-5556-5556"));
        Members.add(new Member("15", "Minu02", "최민우", "O", 3, "020407", "010-6667-6667"));
        Members.add(new Member("16", "Sohyen01", "풍소현", "X", 3, "011112", "010-7778-7778"));
        Members.add(new Member("17", "Yeseong02", "김예성", "O", 3, "020717", "010-8889-8889"));
        Members.add(new Member("18", "Minu97", "이민우", "O", 3, "970315", "010-9990-9990"));
        Members.add(new Member("19", "Yongu01", "고용우", "O", 3, "010129", "010-1125-4468"));
        Members.add(new Member("20", "Howon00", "강효원", "O", 3, "000506", "010-4465-7568"));
        
        
        
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
