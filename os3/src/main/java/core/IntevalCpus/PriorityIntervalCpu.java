package core.IntevalCpus;

public class PriorityIntervalCpu extends IntervalCpu {
    public int Priority;

    @Override
    public void Print() {
        String formattedString = String.format("%-5d | P%-5d | %-14d | %-14d | %-8d | %-14s | %-12d | %-14d",
                startTime,
                Pnum,
                endTime - startTime,
                RemainingBurstTime,
                Priority,
                ActionDetail,
                waitingTime,
                turnAroundTime);
        System.out.println(formattedString);
    }
}
