package schedule;

import java.util.LinkedList;
import java.util.Scanner;

import core.ProcessCpu;
import core.IntervalLists.IntervalList;
import core.Scedulers.SJFscheduler;

public class Main {
    public static void main(String[] args) {
        System.out.print("Enter number of processes : ");
        Scanner scanner = new Scanner(System.in);
        // enter the number of process to be taken from user
        int x = Integer.parseInt(scanner.nextLine());
        LinkedList<ProcessCpu> processCpus = new LinkedList<ProcessCpu>();
        System.out.println("Enter  : arrivalTime burstTime priority quantum");
        for (int i = 1; i <= x; i++) {
            String[] inpuStrings = scanner.nextLine().split(" ");
            ProcessCpu processCpu = new ProcessCpu();
            processCpu.PNum = i;
            processCpu.ArrivalTime = Integer.parseInt(inpuStrings[0]);
            processCpu.BurstTime = Integer.parseInt(inpuStrings[1]);
            processCpu.Priority = Integer.parseInt(inpuStrings[2]);
            processCpu.Quantum = Integer.parseInt(inpuStrings[3]);
            processCpus.add(processCpu);
        }
        while (true) {
            System.out.println("1) SJF  SCHEDULAR");
            System.out.println("2) SRTF SCHEDULAR");
            System.out.println("3) PRIORITY SCHEDULAR");
            System.out.println("4) FCAI SCHEDULAR");
            System.err.println("0) Exit");
            System.out.print("CHOOSE A SCHEDULAR : ");
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception ex) {
                choice = -1;
            }
            boolean exitFlag = false, InvalidInput = false;
            IntervalList intervals = null;
            switch (choice) {
                case 0:
                    exitFlag = true;
                    break;
                case 1:
                    intervals = new SJFscheduler(processCpus).Simulate();
                    break;
                case 2:
                    // add srtf later
                    break;
                case 3:
                    // add priority later
                    break;
                case 4:
                    // add fcai factor
                    break;
                default:
                    InvalidInput = true;
                    System.out.println("Invalid input!");
                    break;
            }
            if (InvalidInput)
                continue;
            if (exitFlag)
                break;
            intervals.print();

        }
        System.out.println("Have a great day :D");
    }
}