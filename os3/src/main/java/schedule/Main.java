package schedule;

import java.util.LinkedList;
import java.util.Scanner;

import core.ProcessCpu;

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

    }
}