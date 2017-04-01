package project2;

import project2.JobScheduler.*;

public class Driver {

	public static void main(String[] args) {

		int[] length = { 7, 4, 2, 5,9,10 ,14};
		int[] deadline = { 7, 16, 8, 10,20,30,25 };
		int[] profit = { 10, 9, 14, 13 ,2,1,18};
		JobScheduler js = new JobScheduler(length, deadline, profit);

		System.out.println("Jobs to be scheduled");
		System.out.println("Job format is " + "(length, deadline, profit, start, finish)");
		js.printJobs();

		// --------------------------------------------
		System.out.println("\nOptimal Solution Using Brute Force O(n!)");
		Schedule bestSchedule = js.bruteForceSolution();
		System.out.println(bestSchedule);
		// ---------------------------------------
		System.out.println("\nEDF with unprofitable jobs last ");
		Schedule EDFPSchedule = js.makeScheduleEDF();
		System.out.println(EDFPSchedule);

		// -------------------------------------
		System.out.println("\nSJF with unprofitable jobs last");
		Schedule SJFPSchedule = js.makeScheduleSJF();
		System.out.println(SJFPSchedule);

		// --------------------------------------------
		System.out.println("\nHPF with unprofitable jobs last");
		Schedule HPFSchedule = js.makeScheduleHPF();
		System.out.println(HPFSchedule);

		// ------------------------------
		System.out.println("\nYour own creative solution ");
		Schedule NASSchedule = js.newApproxSchedule();
		System.out.println(NASSchedule);
	}

}
