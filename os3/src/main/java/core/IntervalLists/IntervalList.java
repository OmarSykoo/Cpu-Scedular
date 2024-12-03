package core.IntervalLists;

import java.util.LinkedList;

import core.IntevalCpus.IntervalCpu;

public abstract class IntervalList extends LinkedList<IntervalCpu> {
    protected abstract void printHeader();

    public double averageWaitingTime = 0;
    public double averageTurnAroundTime = 0;

    public void print() {
        printHeader();
        for (IntervalCpu interval : this) {
            interval.Print();
        }
    }
}
