package core.Scedulers;

import core.IntervalLists.IntervalList;
import core.IntervalLists.SjfIntervalList;
import core.IntevalCpus.IntervalCpu;
import core.IntevalCpus.SjfIntervalCpu;

import java.util.Comparator;
import java.util.LinkedList;

import core.ProcessCpu;

public class SJFscheduler extends CpuSceduler{
    public SJFscheduler(LinkedList<ProcessCpu> cpu) {
        super(cpu);
    }

    @Override
    public IntervalList Simulate() {
        IntervalList iList = new SjfIntervalList();
        LinkedList<ProcessCpu> sjfprocess = process;
        sjfprocess.sort(Comparator.comparingInt((ProcessCpu p) -> p.BurstTime).thenComparingInt(p -> p.ArrivalTime));
        int time = 0;
        for(ProcessCpu p : sjfprocess) {
            if(time < p.ArrivalTime) {
                time = p.ArrivalTime;
            }
            SjfIntervalCpu iCpu = new SjfIntervalCpu();
            iCpu.startTime = time;
            iCpu.Pnum = p.PNum;
            iCpu.endTime = time + p.BurstTime;
            iCpu.ActionDetail = "N\\A";
            iCpu.RemainingBurstTime = 0;
            iList.add(iCpu);
            time += p.BurstTime;
        }
        return iList;
    }

    

}
