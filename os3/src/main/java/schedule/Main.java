package schedule;

import java.util.LinkedList;
import java.util.Scanner;

import core.ProcessCpu;
import core.IntervalLists.IntervalList;
import core.Scedulers.SJFscheduler;
import gui.DisplayService;

public class Main {
    public static void main(String[] args) {
        LinkedList<ProcessCpu> processCpus = new LinkedList<ProcessCpu>();
        Scanner scanner = new Scanner(System.in);
        // System.out.print("Enter number of processes : ");
        // enter the number of process to be taken from user
        // int x = Integer.parseInt(scanner.nextLine());
        // System.out.println("Enter : arrivalTime burstTime priority quantum");
        // for (int i = 1; i <= x; i++) {
        // String[] inpuStrings = scanner.nextLine().split(" ");
        // ProcessCpu processCpu = new ProcessCpu();
        // processCpu.PNum = i;
        // processCpu.ArrivalTime = Integer.parseInt(inpuStrings[0]);
        // processCpu.BurstTime = Integer.parseInt(inpuStrings[1]);
        // processCpu.Priority = Integer.parseInt(inpuStrings[2]);
        // processCpu.Quantum = Integer.parseInt(inpuStrings[3]);
        // processCpus.add(processCpu);
        // }
        String[] TestInput = new String[] {
                "17 4 4 4",
                "6 3 9 3",
                "3 4 3 5",
                "4 29 8 2" };
        for (int i = 0; i < TestInput.length; i++) {
            String[] inpuStrings = TestInput[i].split(" ");
            ProcessCpu processCpu = new ProcessCpu();
            processCpu.PNum = i + 1;
            processCpu.BurstTime = Integer.parseInt(inpuStrings[0]);
            processCpu.ArrivalTime = Integer.parseInt(inpuStrings[1]);
            processCpu.Priority = Integer.parseInt(inpuStrings[2]);
            processCpu.Quantum = Integer.parseInt(inpuStrings[3]);
            processCpus.add(processCpu);
        }
        while (true) {
            System.out.println("1) SJF SCHEDULER");
            System.out.println("2) SRTF SCHEDULER");
            System.out.println("3) PRIORITY SCHEDULER");
            System.out.println("4) FCAI SCHEDULER");
            System.err.println("0) Exit");
            System.out.print("CHOOSE A SCHEDULER: ");
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
            System.out.println("Display Gui ? Y/N : ");
            if (scanner.nextLine().toLowerCase().equals("y")) {
                DisplayService displayService = new DisplayService();
                displayService.CreateDisplay(intervals, processCpus);
            }

        }
        System.out.println("Have a great day :D");
    }
}