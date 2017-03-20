package project2;

import java.util.ArrayList;

public class JobScheduler {
	
	private int nJobs = 0;
	private Job[] jobs;
	
	public JobScheduler(int[] joblength, int[] deadline, int[] profit){
		
		nJobs++;
		
	}
    
	public void printJobs(){
		
		
	}
	
	//insert 5 methods for making schedules 
	
	
	public static void main(String[] args) {
		

	}

		
	
	
public class Job {	
	
	int jobNumber, length, deadline, profit, start, finish;
	
	public Job(int jn, int len, int d, int p){
		
		jobNumber = jn;
		length = len;
		deadline = d;
		profit = p;
		start = -1;
		finish = -1;
	}
	
   public String toString(){
	   return "#" + jobNumber + ":("+ length + ","+ deadline + ","+profit + "," + start + ","+ finish + ")";
   }
	
}//end class Job

public class Schedule {
	
	ArrayList<Job> schedule;
	int profit;
	
	public Schedule(){
		
		profit = 0;
		schedule = new ArrayList<Job>();
	}
	
	public void add(Job job){
		
	}
	
	public int getProfit(){
		return profit;
	}
	
	public String toString(){
		String s = "Schedule profit = "+ profit;
		for (int k = 0; k < schedule.size(); k++){
			s = s + "\n" + schedule.get(k);
		}
		return s;
	}
}//end class Schedule
	
}//end main class JobScheduler
