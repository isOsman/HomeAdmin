package com.HA;


import java.io.Serializable;



public class Work implements Serializable {
	private static final long serialVersionUID = 6512754042407963762L;
	private int ID;
	private String name;
	private String work;
	private String date;
	
	//getters
	public int getID(){
		return this.ID;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getWork(){
		return this.work;
	}
	
	public String getDate(){
		return this.date;
	}
	
	//setters
	public void setID(int ID){
		this.ID = ID;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setWork(String work){
		this.work = work;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	//constructor
	public Work(){
		
	}
	//constructor
	public Work(int ID,String name,String work,String date){
		this.ID = ID;
		this.name = name;
		this.work = work;
		this.date = date;
	}
	//displays information about the object (record)
	public void display(){
		System.out.println("ID:"+this.ID);
		System.out.println("Name:"+this.name);
		System.out.println("Work:"+this.work);
		System.out.println("Date:"+this.date);
	}
	

}

