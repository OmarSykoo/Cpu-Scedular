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
        LinkedList<ProcessCpu> originalProcesses = new LinkedList<>();
        for (ProcessCpu p : process) {
            originalProcesses.add(new ProcessCpu(p));
        }

        process.sort(Comparator.comparingInt(p -> p.ArrivalTime));          

        PriorityQueue<ProcessCpu> readyQueue = new PriorityQueue<>(             
            Comparator.comparingInt(p -> p.BurstTime)        
        );          

        int currentTime = 0;
        int contextSwitchTime = 0;          

        while (!process.isEmpty() || !readyQueue.isEmpty()) {             
            while (!process.isEmpty() && process.peek().ArrivalTime <= currentTime) {                 
                readyQueue.add(process.poll());             
            }              

            if (readyQueue.isEmpty()) {                 
                currentTime++;                 
                continue;             
            }              

            ProcessCpu currentProcess = readyQueue.poll();           
            SrtfIntervalCpu interval = new SrtfIntervalCpu();              

            interval.startTime = currentTime + contextSwitchTime;             
            interval.Pnum = currentProcess.PNum;              

            int executedTime = 0;             
            boolean preempted = false;              

            while (currentProcess.BurstTime > 0) {                 
                currentTime++;                 
                executedTime++;                 
                currentProcess.BurstTime--;                  

                while (!process.isEmpty() && process.peek().ArrivalTime <= currentTime) {                     
                    ProcessCpu newProcess = process.poll();                     
                    if (newProcess.BurstTime < currentProcess.BurstTime) {                         
                        readyQueue.add(newProcess);                         
                        preempted = true;                         
                        break;                     
                    } else {                         
                        readyQueue.add(newProcess);                     
                    }                 
                }                  

                if (preempted) break;             
            }              

            interval.endTime = currentTime;             
            interval.RemainingBurstTime = currentProcess.BurstTime;             
            interval.ActionDetail = preempted                     
                ? "Preempted by P" + (readyQueue.peek() != null ? readyQueue.peek().PNum : "N/A")                     
                : "Process completed";              

            ProcessCpu originalProcess = originalProcesses.stream()
                .filter(p -> p.PNum == currentProcess.PNum)
                .findFirst()
                .orElseThrow();

            interval.turnAroundTime = interval.endTime - originalProcess.ArrivalTime;
            interval.waitingTime = interval.turnAroundTime - originalProcess.BurstTime;

            intervals.add(interval);              

            if (preempted && currentProcess.BurstTime > 0) {                 
                readyQueue.add(currentProcess);             
            }              

            contextSwitchTime = preempted ? contextSwitching : 0;         
        }          

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