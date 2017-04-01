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

		if (index >= jobs.length - 1) { //recursive base case
			
			for (int i = 0; i < jobs.length; i++) {
				jobs[i].start = time;
				time += jobs[i].length;
				jobs[i].finish = time;
				if (jobs[i].finish <= jobs[i].deadline) {
					currentProfit += jobs[i].profit;
				}				
			}
            
			time=0;
            for (int i = 0; i < schedule.schedule.size(); i++){
            	schedule.schedule.get(i).start = time;
            	time += schedule.schedule.get(i).length;
            	schedule.schedule.get(i).finish = time;
            }
            
			if (currentProfit >= maxProfit) {
				schedule.profit = currentProfit;
				maxProfit = currentProfit;
				if (!schedule.schedule.isEmpty()) { // clear the old schedule
					schedule.schedule.removeAll(schedule.schedule);
				}
				
		        time = 0;		        
				for (int j = 0; j < jobs.length; j++) { // create new schedule				
				    schedule.schedule.add(jobs[j]);
				    
				}
				
			}
            return;
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

		

	}// end

	public Schedule makeScheduleEDF() {

		Schedule scheduleEDF = new Schedule();
		DeadlineComparator dc = new DeadlineComparator();
		Arrays.sort(jobs, dc); // jobs array sorted by deadline in nondecreasing order
		int adjTime = 0;
		int maxTime = 0;
		int time = 0;
		int count = 0;
		
		for (int i = 0; i < jobs.length; i++) { // fill in start and finish times
			jobs[i].start = time;
			time += jobs[i].length;
			jobs[i].finish = time;

		}
		maxTime = jobs[jobs.length - 1].finish;

		for (int i = 0; i < jobs.length; i++) {
			
			count = jobs.length- i;
			
			
            //if a job is not meeting its deadline, move it to the end of the array, move remaining elements left 
			//if a job continues to not meet its deadline after swap, we have to swap again until all combinations are tried
			while (count >= 0 ^ (jobs[i].finish <= jobs[i].deadline) ) {
                
				Job temp = jobs[i];
				for (int j = i + 1; j < jobs.length; j++) {
					jobs[j - 1] = jobs[j];
				}
				jobs[jobs.length - 1] = temp;
                
				//re-do all the start,finish times and check if the elements' moving caused previously failing
				//deadlines to now succeed. 
				jobs[jobs.length - 1].finish = maxTime;
				adjTime = maxTime;

				for (int k = jobs.length - 1; k > 0; k--) {
					jobs[k].start = adjTime - jobs[k].length;
					jobs[k - 1].finish = jobs[k].start;
					adjTime -= jobs[k].length;
				}
				jobs[0].start = 0;
				count--;
			}
		}
        //the jobs array is now settled, transfer jobs and profit to the final arraylist schedule
		for (int i = 0; i < jobs.length; i++) { 

			scheduleEDF.schedule.add(jobs[i]);
			if (jobs[i].finish <= jobs[i].deadline)
				scheduleEDF.profit += jobs[i].profit;
		}

		return scheduleEDF;
	}
	public Schedule makeScheduleSJF() {

		Schedule scheduleSJF = new Schedule();
		LengthComparator lc = new LengthComparator();
		Arrays.sort(jobs, lc); // sort the job array by length in nondecreasing
		int adjTime = 0;
		int maxTime = 0;
		int time = 0;
		int count;
		
		//sort the array of jobs and fill in the start,finish times of each job
		Arrays.sort(jobs, lc);
		for (int i = 0; i < jobs.length; i++) { // fill in start and finish times
			jobs[i].start = time;
			time += jobs[i].length;
			jobs[i].finish = time;

		}
		maxTime = jobs[jobs.length - 1].finish;

		for (int i = 0; i < jobs.length; i++) {
			
			count = jobs.length - i;
            //if a job is not meeting its deadline, move it to the end of the array, move remaining elements left 
			while( count >= 0 ^ (jobs[i].finish <= jobs[i].deadline)) {

				Job temp = jobs[i];
				for (int j = i + 1; j < jobs.length; j++) {
					jobs[j - 1] = jobs[j];
				}

				jobs[jobs.length - 1] = temp;

				//redo all the start,finish times and check if the elements' moving caused previously failing
				//deadlines to now succeed. 
				jobs[jobs.length - 1].finish = maxTime;
				adjTime = maxTime;

				for (int k = jobs.length - 1; k > 0; k--) {
					jobs[k].start = adjTime - jobs[k].length;
					jobs[k - 1].finish = jobs[k].start;
					adjTime -= jobs[k].length;
				}
				jobs[0].start = 0;
				count--;
			}
		}
        //the jobs array is now settled, transfer jobs and profit to the final arraylist schedule
		for (int i = 0; i < jobs.length; i++) { 

			scheduleSJF.schedule.add(jobs[i]);
			if (jobs[i].finish <= jobs[i].deadline)
				scheduleSJF.profit += jobs[i].profit;
		}

		return scheduleSJF;
	}

	public Schedule makeScheduleHPF() {
		Schedule scheduleHPF = new Schedule();
		ProfitComparator pc = new ProfitComparator();
		int adjTime = 0;
		int maxTime = 0;
		int time = 0;
		int count = 0;
		//sort the array of jobs and fill in the start,finish times of each job
		Arrays.sort(jobs, pc);
		for (int i = 0; i < jobs.length; i++) { // fill in start and finish times
			jobs[i].start = time;
			time += jobs[i].length;
			jobs[i].finish = time;

		}
		maxTime = jobs[jobs.length - 1].finish;

		for (int i = 0; i < jobs.length; i++) {
			count = jobs.length - i;
            //if a job is not meeting its deadline, move it to the end of the array, move remaining elements left 
			while (count >= 0 ^ (jobs[i].finish <= jobs[i].deadline)) {

				Job temp = jobs[i];
				for (int j = i + 1; j < jobs.length; j++) {
					jobs[j - 1] = jobs[j];
				}

				jobs[jobs.length - 1] = temp;

				//redo all the start,finish times and check if the elements' moving caused previously failing
				//deadlines to now succeed. 
				jobs[jobs.length - 1].finish = maxTime;
				adjTime = maxTime;

				for (int k = jobs.length - 1; k > 0; k--) {
					jobs[k].start = adjTime - jobs[k].length;
					jobs[k - 1].finish = jobs[k].start;
					adjTime -= jobs[k].length;
				}
				jobs[0].start = 0;
				count--;
			}
		}
        //the jobs array is now settled, transfer jobs and profit to the final arraylist schedule
		for (int i = 0; i < jobs.length; i++) { 

			scheduleHPF.schedule.add(jobs[i]);
			if (jobs[i].finish <= jobs[i].deadline)
				scheduleHPF.profit += jobs[i].profit;
		}

		return scheduleHPF;
	}

	public Schedule newApproxSchedule() {
		int adjTime = 0;
		int maxTime =0;
		int time =0;
		int count =0;
		Schedule newSchedule = new Schedule();
	    LambdaComparator lambda = new LambdaComparator();
	    for (int i = 0; i < jobs.length; i++){
	    	jobs[i].lambda = jobs[i].profit/jobs[i].length;
	    }
	    Arrays.sort(jobs, lambda);
	    for (int i = 0; i < jobs.length; i++) { // fill in start and finish times
			jobs[i].start = time;
			time += jobs[i].length;
			jobs[i].finish = time;

		}
		maxTime = jobs[jobs.length - 1].finish;

		for (int i = 0; i < jobs.length; i++) {
			count = jobs.length - i;
            //if a job is not meeting its deadline, move it to the end of the array, move remaining elements left 
			while (count >= 0 ^ (jobs[i].finish <= jobs[i].deadline)) {

				Job temp = jobs[i];
				for (int j = i + 1; j < jobs.length; j++) {
					jobs[j - 1] = jobs[j];
				}

				jobs[jobs.length - 1] = temp;

				//redo all the start,finish times and check if the elements' moving caused previously failing
				//deadlines to now succeed. 
				jobs[jobs.length - 1].finish = maxTime;
				adjTime = maxTime;

				for (int k = jobs.length - 1; k > 0; k--) {
					jobs[k].start = adjTime - jobs[k].length;
					jobs[k - 1].finish = jobs[k].start;
					adjTime -= jobs[k].length;
				}
				jobs[0].start = 0;
				count--;
			}
		}
        //the jobs array is now settled, transfer jobs and profit to the final arraylist schedule
		for (int i = 0; i < jobs.length; i++) { 

			newSchedule.schedule.add(jobs[i]);
			if (jobs[i].finish <= jobs[i].deadline)
				newSchedule.profit += jobs[i].profit;
		}

		return newSchedule;
		
	}

	// -----------------------------------------------------------------------------------------------------
	private class LambdaComparator implements Comparator<Job> {

		@Override
		public int compare(Job arg0, Job arg1) {
			if (arg0.lambda <= arg1.lambda) {
				return 1;
			} else {
				return -1;
			}
		}

	}

	// -----------------------------------------------------------------------------------------------------
	private class DeadlineComparator implements Comparator<Job> {

		@Override
		public int compare(Job arg0, Job arg1) {
			if (arg0.deadline <= arg1.deadline) {
				return -1;
			} else {
				return 1;
			}
		}

	}

	// --------------------------------------------------------------------------------------------------------
	private class LengthComparator implements Comparator<Job> {

		@Override
		public int compare(Job arg0, Job arg1) {
			if (arg0.length < arg1.length) {
				return -1;
			} else {
				return 1;
			}
		}

	}

	// -----------------------------------------------------------------------------------------------------------
	private class ProfitComparator implements Comparator<Job> {

		@Override
		public int compare(Job arg0, Job arg1) {
			if (arg0.profit <= arg1.profit) {
				return 1;
			} else {
				return -1;
			}
		}

	}

	// --------------------------------------------------------------------------------------------------------------
	public class Job {

		int jobNumber, length, deadline, profit, start, finish;
		double lambda;

		public Job(int jn, int len, int d, int p) {

			jobNumber = jn;
			length = len;
			deadline = d;
			profit = p;
			start = -1;
			finish = -1;
			lambda = -1;
		}

		public String toString() {
			return "#" + jobNumber + ":(" + length + "," + deadline + "," + profit + "," + start + "," + finish + ")";
		}

	}// end class Job

	// ---------------------------------------------------------------------------------------------------------------

	public class Schedule {

		ArrayList<Job> schedule;
		int profit;

		public Schedule() {

			profit = 0;
			schedule = new ArrayList<Job>();
		}

		public void add(Job job) {
            //using util.arraylist add(Element e)
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
