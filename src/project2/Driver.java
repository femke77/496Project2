package project2;

import project2.JobScheduler.*;

public class Driver {

	public static void main(String[] args) {

		int[] length = { 7, 4, 2, 5 };
		int[] deadline = { 7, 16, 8, 10 };
		int[] profit = { 10, 9, 14, 13 };
		JobScheduler js = new JobScheduler(length, deadline, profit);

		System.out.println("Jobs to be scheduled");
		System.out.println("Job format is " + "(length, deadline, profit, start, finish)");
		js.printJobs();

		// --------------------------------------------
		System.out.println("\nOptimal Solution Using Brute Force O(n!)");
		Schedule bestSchedule = js.bruteForceSolution();
		System.out.println(bestSchedule);

	}

}

