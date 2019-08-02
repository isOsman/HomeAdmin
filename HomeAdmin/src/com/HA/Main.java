/*
 * created by Osman 
 *I know this program is written in a procedural style. I did not know how to apply OOP
 */
package com.HA;

import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.text.SimpleDateFormat;

public class Main {

	//variables used
	private static final String DIRPATH="./HomeAdmin";//creates a directory  
	private static final String RECORDPATH=DIRPATH+"/myFile1.HA";//creates a file in the DIRPATH directory
	private static final String IDPATH=DIRPATH+"/ID.HA";//	creates a file in the DIRPATH directory
	private static Scanner mainScanner = new Scanner(System.in);
	private static ArrayList<Work> mainList = new ArrayList<>();
	public static void main(String[] args)  {
		boolean exit=false;
	
		init();//call static method init();
		
		
		//main loop
		while(!exit){
			System.out.println("\t\tMenu");
			System.out.println("-----------------------------------");
			System.out.println("1-Add");
			System.out.println("2-Show ");
			System.out.println("3-Edit");
			System.out.println("4-Delete");
			System.out.println("5-Exit");
			System.out.println("-----------------------------------");
			switch(getCharFromUser()){//get user input
				case '1':
					Add();//call static method Add();
					System.out.println("-------------------");
					System.out.println("A_D_D_E_D");
					break;
				case '2':
					Show();//call static method Show();
					break;
				case '3':
					Edit();//call static method Edit();
					break;
				case '4':
					Delete();//call static method Delete();
					break;
				case '5':
					System.out.println("***EXIT***");//exit
					exit=true;
					break;
				default:
					System.out.println("***Please repeat enter...***");
					break;
			}
			
		}
		//main loop end
		

		mainScanner.close();
		
		
		
		
	}
	
	  static void init(){//initializes (creates) dir and files  in current directory
		  
		  if(!new File(DIRPATH).exists()){
			  try{
				  new File(DIRPATH).mkdir();
			  }catch(SecurityException e){
				  System.out.print("***UNABLE TO CREATE DIRECTORY***");
				  System.exit(1);
				  
			  }
		  }
		  
		  if(!new File(RECORDPATH).exists()){
			  try{
				  new File(RECORDPATH).createNewFile();
			  }catch(IOException e){
				  System.out.print("***UNABLE TO CREATE FILE***");
				  System.exit(1);
				  
			  }
		  }
		  
		  if(!new File(IDPATH).exists()){
			  try{
				  new File(IDPATH).createNewFile();
				  writeNumToFile(1);
			  }catch(IOException e){
				  System.out.print("***UNABLE TO CREATE FILE***");
				  System.exit(1);
			  }
		  }
	  }
	  
	  // adds a new record
  static void Add(){
	System.out.println("Add");
	System.out.println("-------------------");
	Work work = getWorkFromUser();
	if(getList()!=null){
	mainList = getList();
	}
	mainList.add(work);
	writeList(mainList);
		
}
	  
	//shows all records
	static void Show(){
		  if(getList()!=null){
			  if(getList().size()!=0){
				  System.out.println("RECORDS:"+getList().size());
				  for(Work work:getList()){
					  System.out.println("-------------------");
					  work.display();
					  System.out.println("-------------------");
				  }
			  }else System.out.println("***NO RECORDS***");
		  }else System.out.println("***NO RECORDS***");
	  }
	
	//edit record with ID
	static void Edit(){
		System.out.println("Edit");
		System.out.println("-------------------");
		if(!(getList()==null)){
			if(!(getList().size()==0)){
				int ID=getIntFromUser("ID of record");
				Work newWork= new Work();
				if(isIdExists(ID)){
					Work oldWork=getWorkWithID(ID);
					System.out.println("Old record:");
					System.out.println("-------------------");
					oldWork.display();
					System.out.println("-------------------");
					System.out.println("New record:");
					System.out.println("-------------------");
					newWork.setID(oldWork.getID());
					newWork.setDate(oldWork.getDate());
					newWork.setName(getUserStr("Name"));
					newWork.setWork(getUserStr("Work"));
					if(getList()!=null){
						mainList = getList();	
						mainList.set(getWorkWithIdIndex(oldWork.getID()),newWork);
						writeList(mainList);
						System.out.println("-------------------");
						System.out.println("E_D_I_T_E_D");	
					}
				}else System.out.println("***Records with this ID do not exists***");
			}else System.out.println("***No records***");
		}else System.out.println("***No records***");
		
	}
	
	static void Delete(){//delete record with ID
		System.out.println("Delete");
		System.out.println("-------------------");
		if(!(getList()==null)){
			if(!(getList().size()==0)){
				int ID=getIntFromUser("ID of record");
				Work work = new Work();
				String answer="";
				if(isIdExists(ID)){
					work = getWorkWithID(ID);
					System.out.println("-------------------");
					work.display();
					System.out.println("-------------------");
					answer=getUserStr("***(y) for delete or any key for cancel***");
					switch(answer){
					case"Y":
					case"y":
						if(getList()!=null){
							mainList=getList();
							mainList.remove(getWorkWithIdIndex(ID));
							writeList(mainList);
							System.out.println("-------------------");
							System.out.println("D_E_L_E_T_E_D");
						}
						break;
					default:
						return;	
					}		
				}else System.out.println("***Records with this ID do not exists***");
			}else System.out.println("***No records***");
		}else System.out.println("***No records***");
	}
	
	//return Line from user
	static String getUserStr(String str){ 
		boolean exit=false;	
		String userStr="";
		while(!exit){//a loop is needed to get a non-empty string
			System.out.println("Enter "+str);
			userStr=mainScanner.nextLine().trim();
			if(userStr.length()==0){
				System.out.println("***Repeat enter...***");
			}else{
				exit=true;				
			}
		}
		return userStr;		
	}
	
	//return formatted date when record added.Example "29/07/19 14:53"
	static String getDateString(){
		return new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());	
		
	}
	
	//generates a new ID for the next record
	static int getNewID(){
		int ID=getNumFromFile();
		writeNumToFile(ID+1);
		return ID;
			
	}
	
	//return Arraylist with all records
	@SuppressWarnings("unchecked")
	static ArrayList<Work> getList() {
		ArrayList<Work> list=null;
		if(new File(RECORDPATH).length()!=0){//if file not empty,then
			try(//try-with-resources
			FileInputStream fis = new FileInputStream(RECORDPATH);
			ObjectInputStream ois = new ObjectInputStream(fis);){
				list = (ArrayList<Work>) ois.readObject();//return list
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	    return list;
	}
	
	
	//writes a finished list with record to a file
	static void writeList(ArrayList<Work> list){
			try(
			FileOutputStream fos = new FileOutputStream(RECORDPATH);
			ObjectOutputStream ois = new ObjectOutputStream(fos);){
				ois.writeObject(list);
			}catch (Exception e) {
				e.printStackTrace();
			}
					
	}
	
	//read ID number from file
	static int getNumFromFile(){
		int num=-1;
		if(new File(IDPATH).length()!=0){//if file not empty,then
			try (FileReader fr = new FileReader(IDPATH);){
				Scanner scan = new Scanner(fr);
				num=scan.nextInt();
				scan.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return num; 
		
	}
	
	//writes a ID number to a file
	static void writeNumToFile(int ID){
		try(FileWriter fw = new FileWriter(IDPATH);){
			fw.write(String.valueOf(ID));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

	

	//checks if there is a record with this ID
	static boolean isIdExists(int ID){
		boolean exists=false;
		if(getList()!=null && getList().size()!=0){
			for(Work work : getList()){
					if(work.getID()==ID){
						exists=true;
						break;
					}
				}
		}
		
		return exists;
	}
	
	//returns an object of type Work with parameter ID
	 static Work getWorkWithID(int ID){
		Work work=null;
		for (Work tempWork : getList()) {
			if(tempWork.getID()==ID){
				work=tempWork;
				break;
			}
		}
		return work;
	}
	 
	//returns an object of type work with completed fields
	static Work getWorkFromUser(){
		Work work=new Work(getNewID(),getUserStr("Name"),getUserStr("Work"),getDateString());
		return work;
	} 
	
	//returns the index of an object with parameter ID in an ArrayList
	static int getWorkWithIdIndex(int ID){
		int index=-1;
		int i=0;
		for(Work work:getList()){
			if(work.getID()==ID){
				index=i;
				break;
			}
			i++;
			
		}
		return index;
		
	}
	//return number from user
	static int getIntFromUser(String str){
		int num=-1;
		boolean exit=false;
		while(!exit){
			System.out.println("Enter "+str);		
			try{
			    num=Integer.parseInt(mainScanner.nextLine());	
			    exit=true;
			}catch(Exception e){
				continue;
			}		
			
		}
		return num;
	}
	//return char from user
	static char getCharFromUser(){
		String userStr="";
		char answer=1;
		boolean exit=false;
		while(!exit){
			userStr = mainScanner.nextLine().trim();
			if(userStr.length()==0){
				System.out.println("***Repeat enter...***");
				}else{
					answer = userStr.charAt(0);
					exit=true;				
				}
			}
			return answer;
		}
		
			

}
