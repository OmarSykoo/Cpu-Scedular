package core.Scedulers;

import core.IntervalLists.IntervalList;

import java.util.LinkedList;

import core.ProcessCpu;

public class SJFscheduler extends CpuSceduler{
    public SJFscheduler(LinkedList<ProcessCpu> cpu) {
        super(cpu);
    }

    @Override
    public IntervalList Simulate() {
        
        throw new UnsupportedOperationException("Unimplemented method 'Simulate'");
    }

    

}
