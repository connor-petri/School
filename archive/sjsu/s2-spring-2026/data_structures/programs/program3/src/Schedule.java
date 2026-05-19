import java.util.*;

public class Schedule {
    public class Job {
        private int num;
        private final int timeToComplete;
        private final List<Job> outgoing;
        private int inDegree;

        private int timeEst; // This is the earliest START time of any job
        private int khanInDegree;

        private Job(int timeToComplete) {
            num = nextJobNum++;
            this.timeToComplete = timeToComplete;
            outgoing = new ArrayList<>();
            inDegree = 0;

            timeEst = 0;
            khanInDegree = 0;

            jobs.add(this);
        }

        public void requires(Job j) {
            j.outgoing.add(this);
            inDegree++;
            stale = true;
        }

        public int start() {
            if (stale) {
                computeSchedule();
            }
            return timeEst;
        }
    }

    private int nextJobNum = 0;
    private final ArrayList<Job> jobs = new ArrayList<>();
    private boolean stale = true;
    private boolean noLoops = true;
    private int finishTime = 0;

    private void computeSchedule() {
        Queue<Job> q = new LinkedList<>();

        // Initialize vertices
        for (Job job : jobs) {
            job.timeEst = 0;

            job.khanInDegree = job.inDegree;
            if (job.inDegree == 0) {
                q.add(job);
            }
        }

        Job current;
        while (!q.isEmpty()) {
            current = q.poll();

            for (Job job : current.outgoing) {
                // Relaxation
                if (current.timeEst + current.timeToComplete > job.timeEst) {
                    job.timeEst = current.timeEst + current.timeToComplete;
                }
                job.khanInDegree--;
                if (job.khanInDegree == 0) {
                    q.add(job);
                }
            }
        }

        // Check for loops. Set that vertex's timeEst to -1;
        noLoops = true;
        for (Job job : jobs) {
            if (job.khanInDegree != 0) {
                job.timeEst = -1;
                noLoops = false;
            }

            if (job.outgoing.isEmpty() && job.timeToComplete + job.timeEst > finishTime) {
                finishTime = job.timeEst + job.timeToComplete;
            }
        }
        stale = false;
    }

    public Job insert(int time) {
        stale = true;
        return new Job(time);
    }

    public Job get(int num) {
        return jobs.get(num);
    }

    public int finish() {
        // If loops exist, no valid finishing time exists for the entire schedule

        if (stale) {
            computeSchedule();
        }

        if (!noLoops) {
            return -1;
        }

        return finishTime;
    }
}
