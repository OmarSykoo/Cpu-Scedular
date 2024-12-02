package core.IntevalCpus;

public class PriorityIntervalCpu extends IntervalCpu {
    public int Priority;

    @Override
    public void Print() {
        String formattedString = String.format("%d -> %d | P%d | %d | %d | %d | %s",
                startTime,
                endTime,
                Pnum,
                endTime - startTime,
                RemainingBurstTime,
                Priority,
                ActionDetail);
        System.out.println(formattedString);
    }
}
