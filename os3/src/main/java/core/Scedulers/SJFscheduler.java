package core.Scedulers;

import core.IntervalLists.IntervalList;
import core.IntervalLists.SjfIntervalList;
import core.IntevalCpus.SjfIntervalCpu;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import core.ProcessCpu;

public class SJFscheduler extends CpuSceduler {
    private IntervalList iList;
    private LinkedList<ProcessCpu> sjfprocess;

    public SJFscheduler(LinkedList<ProcessCpu> cpu) {
        super(cpu);
        this.iList = new SjfIntervalList();
        this.sjfprocess = new LinkedList<>(process);
    }

    @Override
    public IntervalList Simulate() {
        LinkedList<ProcessCpu> Q = new LinkedList<>();
        Map<ProcessCpu, Integer> agingFactors = new HashMap<>();
        int time = 0;
        sjfprocess.sort(Comparator.comparingInt((ProcessCpu p) -> p.ArrivalTime).thenComparingInt(p -> p.BurstTime));
        while (!Q.isEmpty() || !sjfprocess.isEmpty()) {
            Iterator<ProcessCpu> iterator = sjfprocess.iterator();
            while (iterator.hasNext()) {
                ProcessCpu pcpu = iterator.next();
                if (pcpu.ArrivalTime <= time) {
                    Q.add(pcpu);
                    agingFactors.put(pcpu, 0);
                    iterator.remove();
                }
            }
            if (Q.isEmpty()) {
                time = sjfprocess.peek().ArrivalTime;
                continue;
            }
            for(ProcessCpu proc : Q) {
                agingFactors.put(proc, agingFactors.get(proc) + 1);
            }
            ProcessCpu shortestBurst = Q.stream().min(Comparator.comparingInt(proc -> (proc.BurstTime - agingFactors.get(proc)))).orElseThrow();
            Q.remove(shortestBurst);
            SjfIntervalCpu iCpu = new SjfIntervalCpu();
            iCpu.startTime = time;
            iCpu.waitingTime = time - shortestBurst.ArrivalTime - shortestBurst.BurstTime;
            if(iCpu.waitingTime < 0 ) {
                iCpu.waitingTime = 0;
            }
            iCpu.turnAroundTime = time - shortestBurst.ArrivalTime;
            iCpu.Pnum = shortestBurst.PNum;
            iCpu.endTime = time + shortestBurst.BurstTime;
            iList.add(iCpu);
            time += shortestBurst.BurstTime;
            agingFactors.remove(shortestBurst);
        }
        float avgWaiting = 0, avgTurnAround = 0;
        for(var it : iList) {
            avgWaiting += it.waitingTime;
            avgTurnAround += it.turnAroundTime;
        }
        avgWaiting /= iList.size();
        avgTurnAround /= iList.size();

        iList.averageWaitingTime = avgWaiting;
        iList.averageTurnAroundTime = avgTurnAround;
        return iList;
    }

}
