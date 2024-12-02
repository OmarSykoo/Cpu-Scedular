package core.IntervalLists;

import java.util.LinkedList;

import core.IntevalCpus.IntervalCpu;

public abstract class IntervalList extends LinkedList<IntervalCpu> {
    protected abstract void printHeader();

    public void print() {
        printHeader();
        for (IntervalCpu interval : this) {
            interval.Print();
        }
    }
}
