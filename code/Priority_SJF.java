import java.util.*;

public class Priority_SJF {
    static class Process {
        int pid, arrivalTime, burstTime, priority, completion, waiting, turnaround;
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
            System.out.print("Priority (lower = higher): ");
            p[i].priority = sc.nextInt();
        }

        simulatePriority(p);
        simulateSJF(p);
        sc.close();
    }

    static void simulatePriority(Process[] input) {
        Process[] p = Arrays.stream(input).map(Priority_SJF::copy).toArray(Process[]::new);
        int time = 0, completed = 0;
        boolean[] done = new boolean[p.length];

        while (completed != p.length) {
            int idx = -1, minPr = Integer.MAX_VALUE;
            for (int i = 0; i < p.length; i++) {
                if (!done[i] && p[i].arrivalTime <= time && p[i].priority < minPr) {
                    minPr = p[i].priority;
                    idx = i;
                }
            }
            if (idx == -1) {
                time++;
                continue;
            }
            time += p[idx].burstTime;
            p[idx].completion = time;
            p[idx].turnaround = p[idx].completion - p[idx].arrivalTime;
            p[idx].waiting = p[idx].turnaround - p[idx].burstTime;
            done[idx] = true;
            completed++;
        }

        System.out.println("\n=== Priority Scheduling ===");
        printTable(p);
    }

    static void simulateSJF(Process[] input) {
        Process[] p = Arrays.stream(input).map(Priority_SJF::copy).toArray(Process[]::new);
        int time = 0, completed = 0;
        boolean[] done = new boolean[p.length];

        while (completed != p.length) {
            int idx = -1, minBT = Integer.MAX_VALUE;
            for (int i = 0; i < p.length; i++) {
                if (!done[i] && p[i].arrivalTime <= time && p[i].burstTime < minBT) {
                    minBT = p[i].burstTime;
                    idx = i;
                }
            }
            if (idx == -1) {
                time++;
                continue;
            }
            time += p[idx].burstTime;
            p[idx].completion = time;
            p[idx].turnaround = p[idx].completion - p[idx].arrivalTime;
            p[idx].waiting = p[idx].turnaround - p[idx].burstTime;
            done[idx] = true;
            completed++;
        }

        System.out.println("\n=== Shortest Job First (SJF) Scheduling ===");
        printTable(p);
    }

    static Process copy(Process x) {
        Process p = new Process();
        p.pid = x.pid; p.arrivalTime = x.arrivalTime; p.burstTime = x.burstTime; p.priority = x.priority;
        return p;
    }

    static void printTable(Process[] p) {
        System.out.println("PID\tAT\tBT\tPR\tCT\tTAT\tWT");
        double totalTAT = 0, totalWT = 0;
        for (Process pr : p) {
            totalTAT += pr.turnaround;
            totalWT += pr.waiting;
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\t%d\n", pr.pid, pr.arrivalTime, pr.burstTime,
                    pr.priority, pr.completion, pr.turnaround, pr.waiting);
        }
        System.out.printf("Average TAT = %.2f, Average WT = %.2f\n", totalTAT / p.length, totalWT / p.length);
    }
}
