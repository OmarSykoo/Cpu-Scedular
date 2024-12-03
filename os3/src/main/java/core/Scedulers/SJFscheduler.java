package core.Scedulers;

import core.IntervalLists.IntervalList;
import core.IntervalLists.SjfIntervalList;
import core.IntevalCpus.IntervalCpu;
import core.IntevalCpus.SjfIntervalCpu;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import core.ProcessCpu;

public class SJFscheduler extends CpuSceduler{
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
        int time = 0;
        while(!Q.isEmpty() || !sjfprocess.isEmpty()) {
            Iterator<ProcessCpu> iterator = sjfprocess.iterator();
            while(iterator.hasNext()) {
                ProcessCpu pcpu = iterator.next();
                if(pcpu.ArrivalTime <= time){
                    Q.add(pcpu);
                    iterator.remove();
                }
            }
            if(Q.isEmpty()) {
                Q.add(sjfprocess.getFirst());
                time = sjfprocess.getFirst().ArrivalTime;
                sjfprocess.remove();
            }
            ProcessCpu shortestBurst = Q.stream().min(Comparator.comparingInt(proc -> proc.BurstTime)).orElseThrow();
            Q.remove(shortestBurst);
            SjfIntervalCpu iCpu = new SjfIntervalCpu();
            iCpu.startTime = time;
            iCpu.Pnum = shortestBurst.PNum;
            iCpu.endTime = time + shortestBurst.BurstTime;
            iCpu.ActionDetail = "N\\A";
            iCpu.RemainingBurstTime = 0;
            iList.add(iCpu);
            time += shortestBurst.BurstTime;
        }
        return iList;
    }

    

}
