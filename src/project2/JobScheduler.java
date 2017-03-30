package project2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class JobScheduler {

	static int maxProfit = 0;
	private int nJobs;
	private Job[] jobs;

	public JobScheduler(int[] joblength, int[] deadline, int[] profit) {
		nJobs = joblength.length;
		jobs = new Job[nJobs];

		for (int i = 0; i < jobs.length; i++) {
			jobs[i] = new Job(i, joblength[i], deadline[i], profit[i]);
		}

	}

	public void printJobs() {
		for (int i = 0; i < jobs.length; i++) {
			System.out.println("#" + i + ": (" + jobs[i].length + ", " + jobs[i].deadline + ", " + jobs[i].profit + ", "
					+ jobs[i].start + ", " + jobs[i].finish + ")");
		}
	}

	public Schedule bruteForceSolution() {
		Schedule schedule = new Schedule();     
		bruteForcePermute(schedule, jobs);

		return schedule;

	}

	private void bruteForcePermute(Schedule schedule, Job[] jobs) {
		bFPermuteHelper(schedule, jobs, 0);

	}

	private void bFPermuteHelper(Schedule schedule, Job[] jobs, int index) {
		int time = 0;
		int currentProfit = 0;
		Job temp;		

		if (index >= jobs.length - 1) { // stop recursion

			for (int i = 0; i < jobs.length; i++) { 
				jobs[i].start = time;
				jobs[i].finish = time = time + jobs[i].length;
				if (jobs[i].finish <= jobs[i].deadline) { 
					currentProfit += jobs[i].profit;
				}
			}

			if (currentProfit >= maxProfit) { 

				schedule.profit = currentProfit;
			    maxProfit= currentProfit;
				if (!schedule.schedule.isEmpty()) { // clear the old schedule
					schedule.schedule.removeAll(schedule.schedule);
				}
				for (int j = 0; j < jobs.length; j++) { // create new schedule
					schedule.schedule.add(jobs[j]);
				}

			}
			
			
		}
		for (int k = index; k < jobs.length; k++) {
			temp = jobs[index];
			jobs[index] = jobs[k];
			jobs[k] = temp;

			bFPermuteHelper(schedule, jobs, index + 1);
            			
			temp = jobs[index];
			jobs[index] = jobs[k];
			jobs[k] = temp;

		}
	
	}//end algorithm
	
    public Schedule makeScheduleEDF(){
    	int time = 0;   	
    	Schedule scheduleEDF = new Schedule();
    	DeadlineComparator dc= new DeadlineComparator();
    	
    	Arrays.sort(jobs, dc);  //jobs array sorted by deadline
    	for (int i = 0; i < jobs.length; i++){   //fill in start and finish times
    		jobs[i].start = time;
			jobs[i].finish = time = time + jobs[i].length;
			
    	}	
    	for (int i = 0; i < jobs.length; i++){
    		if (jobs[i].finish <= jobs[i].deadline){
    			scheduleEDF.profit += jobs[i].profit;
    		}
    		else{  //do not add profit, move it to the end of the array and slide the others left
    			Job temp = jobs[i];
    			for (int j = i+1; j < jobs.length; j++){
    				jobs[j-1]= jobs[j];
    			}
    			jobs[jobs.length-1]=temp;
    		}
    	}
    	for (int i = 0; i < jobs.length; i++){   //move jobs array into the schedule arraylist. 
    	    scheduleEDF.schedule.add(jobs[i]);
    	}
    	return scheduleEDF;
    }
    
    public Schedule makeScheduleSJF(){
    	int time=0;
    	Schedule scheduleSJF = new Schedule();
    	LengthComparator lc = new LengthComparator();
    	Arrays.sort(jobs, lc);  //sort the job array by length in nondecreasing order
    	for (int i = 0; i < jobs.length; i++){   //fill in start and finish times
    		jobs[i].start = time;
			jobs[i].finish = time = time + jobs[i].length;
			
    	}	
    	for (int i = 0; i < jobs.length; i++){
    		if (jobs[i].finish <= jobs[i].deadline){
    			scheduleSJF.profit += jobs[i].profit;
    		}
    		else{  //do not add profit, move it to the end of the array and slide the others left
    			Job temp = jobs[i];
    			for (int j = i+1; j < jobs.length; j++){
    				jobs[j-1]= jobs[j];
    			}
    			jobs[jobs.length-1]=temp;
    		}
    	}
    	for (int i = 0; i < jobs.length; i++){   //move jobs array into the schedule arraylist. 
    	    scheduleSJF.schedule.add(jobs[i]);
    	}
    	return scheduleSJF;
    	
    }
    
    public Schedule makeScheduleHPF(){
    	int time=0;
    	Schedule scheduleHPF = new Schedule();
    	ProfitComparator pc = new ProfitComparator();
    	Arrays.sort(jobs, pc);
    	for (int i = 0; i < jobs.length; i++){   //fill in start and finish times
    		jobs[i].start = time;
			jobs[i].finish = time = time + jobs[i].length;
			
    	}	
    	for (int i = 0; i < jobs.length; i++){
    		if (jobs[i].finish <= jobs[i].deadline){
    			scheduleHPF.profit += jobs[i].profit;
    		}
    		else{  //do not add profit, move it to the end of the array and slide the others left
    			Job temp = jobs[i];
    			for (int j = i+1; j < jobs.length; j++){
    				jobs[j-1]= jobs[j];
    			}
    			jobs[jobs.length-1]=temp;
    		}
    	}
    	for (int i = 0; i < jobs.length; i++){   //move jobs array into the schedule arraylist. 
    	    scheduleHPF.schedule.add(jobs[i]);
    	}
    	
    	return scheduleHPF;
    }
    
    public Schedule newApproxSchedule(){
    	Schedule mySchedule= new Schedule();
    	
    	
    	return mySchedule;
    }
    
    
    //-----------------------------------------------------------------------------------------------------
    class DeadlineComparator implements Comparator<Job>{ 

        @Override
        public int compare(Job arg0, Job arg1) {
            if(arg0.deadline <= arg1.deadline){
                return -1;
            }else{
                return 1;
            }
        }
        
    }
    //--------------------------------------------------------------------------------------------------------
    class LengthComparator implements Comparator<Job>{ 

        @Override
        public int compare(Job arg0, Job arg1) {
            if(arg0.length <= arg1.length){
                return -1;
            }else{
                return 1;
            }
        }
        
    }
    
    //-----------------------------------------------------------------------------------------------------------
    class ProfitComparator implements Comparator<Job>{ 

        @Override
        public int compare(Job arg0, Job arg1) {
            if(arg0.profit<= arg1.profit){
                return -1;
            }else{
                return 1;
            }
        }
        
    }
    
    //--------------------------------------------------------------------------------------------------------------
	public class Job { 

		int jobNumber, length, deadline, profit, start, finish;

		public Job(int jn, int len, int d, int p) {

			jobNumber = jn;
			length = len;
			deadline = d;
			profit = p;
			start = -1;
			finish = -1;
		}

		public String toString() {
			return "#" + jobNumber + ":(" + length + "," + deadline + "," + profit + "," 
		    + start + "," + finish + ")";
		}

	}// end class Job
	
	//---------------------------------------------------------------------------------------------------------------

	public class Schedule {

		ArrayList<Job> schedule;
		int profit;
      
        
		public Schedule() {

			profit = 0;
			schedule = new ArrayList<Job>();
		}

		public void add(Job job) {
           
		}

		public int getProfit() {
			return profit;
		}

		public String toString() {
			String s = "Schedule profit = " + profit;
			for (int k = 0; k < schedule.size(); k++) {
				s = s + "\n" + schedule.get(k);
			}
			return s;
		}
	}// end class Schedule

}// end main class JobScheduler
