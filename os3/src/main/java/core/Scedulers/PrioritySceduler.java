package core.Scedulers;

import java.util.LinkedList;

import core.ProcessCpu;
import core.IntervalLists.IntervalList;
import core.IntervalLists.PriorityIntervalList;
import core.IntevalCpus.PriorityIntervalCpu;

public class PrioritySceduler extends CpuSceduler {
    private PriorityIntervalList intervals;

    public PrioritySceduler(LinkedList<ProcessCpu> process) {
        super(process);
        intervals = new PriorityIntervalList();
    }

    @Override
    public IntervalList Simulate() {

        PriorityIntervalCpu intervalCpu = new PriorityIntervalCpu();
        intervalCpu.Pnum = 1;
        intervalCpu.ActionDetail = "ioasjpfpa";

        intervals.add(intervalCpu);
        return intervals;
    }

}
