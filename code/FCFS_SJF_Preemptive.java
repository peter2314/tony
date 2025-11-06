import java.util.*;

public class FCFS_SJF_Preemptive {
    static class Process {
        int pid, arrivalTime, burstTime, remainingTime;
        int completionTime, waitingTime, turnaroundTime;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        Process[] p = new Process[n];
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            p[i].pid = i + 1;
            System.out.println("\nEnter details for Process " + (i + 1));
            System.out.print("Arrival Time: ");
            p[i].arrivalTime = sc.nextInt();
            System.out.print("Burst Time: ");
            p[i].burstTime = sc.nextInt();
            p[i].remainingTime = p[i].burstTime;
        }

        simulateFCFS(p);
        simulateSJFPreemptive(p);
        sc.close();
    }

    // ---------- FCFS ----------
    static void simulateFCFS(Process[] input) {
        Process[] p = Arrays.stream(input).map(FCFS_SJF_Preemptive::copy).toArray(Process[]::new);
        Arrays.sort(p, Comparator.comparingInt(a -> a.arrivalTime));
        int currentTime = 0;

        for (Process pr : p) {
            if (currentTime < pr.arrivalTime)
                currentTime = pr.arrivalTime;
            pr.completionTime = currentTime + pr.burstTime;
            pr.turnaroundTime = pr.completionTime - pr.arrivalTime;
            pr.waitingTime = pr.turnaroundTime - pr.burstTime;
            currentTime = pr.completionTime;
        }

        System.out.println("\n=== FCFS Scheduling ===");
        printTable(p);
    }

    // ---------- SJF (Preemptive) ----------
    static void simulateSJFPreemptive(Process[] input) {
        Process[] p = Arrays.stream(input).map(FCFS_SJF_Preemptive::copy).toArray(Process[]::new);
        int time = 0, completed = 0;
        boolean[] done = new boolean[p.length];

        while (completed != p.length) {
            int idx = -1, minRT = Integer.MAX_VALUE;
            for (int i = 0; i < p.length; i++) {
                if (!done[i] && p[i].arrivalTime <= time && p[i].remainingTime < minRT && p[i].remainingTime > 0) {
                    minRT = p[i].remainingTime;
                    idx = i;
                }
            }

            if (idx == -1) { // No process has arrived yet
                time++;
                continue;
            }

            p[idx].remainingTime--;
            time++;

            if (p[idx].remainingTime == 0) {
                done[idx] = true;
                completed++;
                p[idx].completionTime = time;
                p[idx].turnaroundTime = p[idx].completionTime - p[idx].arrivalTime;
                p[idx].waitingTime = p[idx].turnaroundTime - p[idx].burstTime;
            }
        }

        System.out.println("\n=== SJF (Preemptive / SRTF) Scheduling ===");
        printTable(p);
    }

    static Process copy(Process x) {
        Process p = new Process();
        p.pid = x.pid; p.arrivalTime = x.arrivalTime; p.burstTime = x.burstTime; p.remainingTime = x.remainingTime;
        return p;
    }

    static void printTable(Process[] p) {
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
        double totalTAT = 0, totalWT = 0;
        for (Process pr : p) {
            totalTAT += pr.turnaroundTime;
            totalWT += pr.waitingTime;
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\n",
                    pr.pid, pr.arrivalTime, pr.burstTime,
                    pr.completionTime, pr.turnaroundTime, pr.waitingTime);
        }
        System.out.printf("Average TAT = %.2f, Average WT = %.2f\n",
                totalTAT / p.length, totalWT / p.length);
    }
}
