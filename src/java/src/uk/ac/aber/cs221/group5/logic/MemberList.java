package uk.ac.aber.cs221.group5.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class MemberList {
	//TODO implement updating memberList
	private ArrayList<Members> memberList = new ArrayList<Members>();
	
	public MemberList(){
		
	}
	
	public Members getMember(int index){
		return memberList.get(index);
	}
	
	public void addMember(Members member){
		memberList.add(member);
	}
	
	public void loadMembers(String filename) throws NumberFormatException, IOException{
		FileReader fileReader = new FileReader(filename);
		BufferedReader read = new BufferedReader(fileReader);
		String memberName;
		String memberEmail;
		int memberCount = Integer.parseInt(read.readLine());
		for(int loopCount = 0; loopCount < memberCount; loopCount++){
			memberEmail = read.readLine();
			memberName = read.readLine();
			Members member = new Members(memberName, memberEmail);
			memberList.add(member);
		}
		read.close();
	}
	
	public boolean memberExists(String email){
		for(int loopCount = 0; loopCount < memberList.size(); loopCount++){
			if((this.getMember(loopCount).getEmail()).equals(email)){
				return true;
			}
		}
		return false;
	}
}
