package project2;

import java.util.ArrayList;

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
		bFPermute(schedule, jobs);

		return schedule;

	}

	private void bFPermute(Schedule schedule, Job[] jobs) {
	

		bFPermuteHelper(schedule, jobs, 0);

	}

	private void bFPermuteHelper(Schedule schedule, Job[] jobs, int index) {
		int time = 0;
		int currentProfit = 0;
		Job temp;
		

		if (index >= jobs.length - 1) { // stop recursion, no permutations left

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

	}

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
			return "#" + jobNumber + ":(" + length + "," + deadline + "," + profit + "," + start + "," + finish + ")";
		}

	}// end class Job

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
