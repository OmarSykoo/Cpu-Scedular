package core.IntevalCpus;

public class FcaiIntervalCpu extends IntervalCpu {
    public int UpdatedQuantam;
    public int Priority;
    public int FcaiFactor;

    @Override
    public void Print() {
        String formattedString = String.format("%d -> %d | P%d | %d | %d | %d | %d | %d | %s",
                startTime,
                endTime,
                Pnum,
                endTime - startTime,
                RemainingBurstTime,
                UpdatedQuantam,
                Priority,
                FcaiFactor,
                ActionDetail);
        System.out.println(formattedString);
    }
}
