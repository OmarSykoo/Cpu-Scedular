package core.Scedulers;

import java.util.*;
import core.ProcessCpu;
import core.IntervalLists.IntervalList;
import core.IntervalLists.SrtfIntervalList;
import core.IntevalCpus.SrtfIntervalCpu;

public class SrtfScheduler extends CpuSceduler {
    private SrtfIntervalList intervals;
    private int contextSwitching;

    public SrtfScheduler(LinkedList<ProcessCpu> process, int contextSwitching) {
        super(process);
        this.contextSwitching = contextSwitching;         
        this.intervals = new SrtfIntervalList();     
    }      

    @Override     
    public IntervalList Simulate() {         
        // Create a copy of the original processes to track original burst times
        LinkedList<ProcessCpu> originalProcesses = new LinkedList<>();
        for (ProcessCpu p : process) {
            originalProcesses.add(new ProcessCpu(p));
        }

        // Sort the processes by arrival time         
        process.sort(Comparator.comparingInt(p -> p.ArrivalTime));          

        // Ready queue for processes, sorted by shortest remaining time         
        PriorityQueue<ProcessCpu> readyQueue = new PriorityQueue<>(             
            Comparator.comparingInt(p -> p.BurstTime) // Shortest remaining time first         
        );          

        int currentTime = 0;
        int contextSwitchTime = 0;          

        while (!process.isEmpty() || !readyQueue.isEmpty()) {             
            // Add all processes that have arrived to the ready queue             
            while (!process.isEmpty() && process.peek().ArrivalTime <= currentTime) {                 
                readyQueue.add(process.poll());             
            }              

            // If no process is ready, advance time             
            if (readyQueue.isEmpty()) {                 
                currentTime++;                 
                continue;             
            }              

            // Pick the next process to execute             
            ProcessCpu currentProcess = readyQueue.poll();             
            SrtfIntervalCpu interval = new SrtfIntervalCpu();              

            // Set interval start time and other initial details             
            interval.startTime = currentTime + contextSwitchTime;             
            interval.Pnum = currentProcess.PNum;              

            int executedTime = 0;             
            boolean preempted = false;              

            // Execute the process             
            while (currentProcess.BurstTime > 0) {                 
                currentTime++;                 
                executedTime++;                 
                currentProcess.BurstTime--;                  

                // Check if a new process preempts the current one                 
                while (!process.isEmpty() && process.peek().ArrivalTime <= currentTime) {                     
                    ProcessCpu newProcess = process.poll();                     
                    if (newProcess.BurstTime < currentProcess.BurstTime) {                         
                        // Add preempting process to the queue                         
                        readyQueue.add(newProcess);                         
                        preempted = true;                         
                        break;                     
                    } else {                         
                        readyQueue.add(newProcess);                     
                    }                 
                }                  

                if (preempted) break;             
            }              

            // Complete interval details             
            interval.endTime = currentTime;             
            interval.RemainingBurstTime = currentProcess.BurstTime;             
            interval.ActionDetail = preempted                     
                ? "Preempted by P" + (readyQueue.peek() != null ? readyQueue.peek().PNum : "N/A")                     
                : "Process completed";              

            // Find the original process to calculate times correctly
            ProcessCpu originalProcess = originalProcesses.stream()
                .filter(p -> p.PNum == currentProcess.PNum)
                .findFirst()
                .orElseThrow();

            // Calculate waiting and turnaround times
            interval.turnAroundTime = interval.endTime - originalProcess.ArrivalTime;
            interval.waitingTime = interval.turnAroundTime - originalProcess.BurstTime;

            intervals.add(interval);              

            // If preempted, re-add the process with remaining burst time             
            if (preempted && currentProcess.BurstTime > 0) {                 
                readyQueue.add(currentProcess);             
            }              

            // Add context switching delay for next interval             
            contextSwitchTime = preempted ? contextSwitching : 0;         
        }          

        // Calculate averages         
        float avgWaitingTime = 0, avgTurnAroundTime = 0;         
        for (var it : intervals) {             
            avgWaitingTime += it.waitingTime;             
            avgTurnAroundTime += it.turnAroundTime;         
        }         
        intervals.averageWaitingTime = avgWaitingTime / intervals.size();         
        intervals.averageTurnAroundTime = avgTurnAroundTime / intervals.size();          

        return intervals;     
    } 
} 