import java.util.*;

class Process {
    int pid, arrivalTime, burstTime, priority;
    int completionTime, waitingTime, turnaroundTime;
}

public class FCFS_Priority {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] processes = new Process[n];
        for (int i = 0; i < n; i++) {
            processes[i] = new Process();
            processes[i].pid = i + 1;
            System.out.println("\nEnter details for Process " + (i + 1));
            System.out.print("Arrival Time: ");
            processes[i].arrivalTime = sc.nextInt();
            System.out.print("Burst Time: ");
            processes[i].burstTime = sc.nextInt();
            System.out.print("Priority (lower = higher priority): ");
            processes[i].priority = sc.nextInt();
        }

        simulateFCFS(processes);
        simulatePriority(processes);
        sc.close();
    }

    static void simulateFCFS(Process[] input) {
        Process[] p = Arrays.stream(input).map(FCFS_Priority::copy).toArray(Process[]::new);
        Arrays.sort(p, Comparator.comparingInt(a -> a.arrivalTime));

        int time = 0;
        for (Process pr : p) {
            if (time < pr.arrivalTime) time = pr.arrivalTime;
            pr.completionTime = time + pr.burstTime;
            pr.turnaroundTime = pr.completionTime - pr.arrivalTime;
            pr.waitingTime = pr.turnaroundTime - pr.burstTime;
            time = pr.completionTime;
        }

        System.out.println("\n=== FCFS Scheduling ===");
        printTable(p);
    }

    static void simulatePriority(Process[] input) {
        Process[] p = Arrays.stream(input).map(FCFS_Priority::copy).toArray(Process[]::new);

        int time = 0, completed = 0;
        boolean[] done = new boolean[p.length];

        while (completed != p.length) {
            int idx = -1, minPriority = Integer.MAX_VALUE;
            for (int i = 0; i < p.length; i++) {
                if (!done[i] && p[i].arrivalTime <= time && p[i].priority < minPriority) {
                    minPriority = p[i].priority;
                    idx = i;
                }
            }
            if (idx == -1) {
                time++;
                continue;
            }
            time += p[idx].burstTime;
            p[idx].completionTime = time;
            p[idx].turnaroundTime = p[idx].completionTime - p[idx].arrivalTime;
            p[idx].waitingTime = p[idx].turnaroundTime - p[idx].burstTime;
            done[idx] = true;
            completed++;
        }

        System.out.println("\n=== Priority Scheduling ===");
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
            totalTAT += pr.turnaroundTime;
            totalWT += pr.waitingTime;
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\t%d\n", pr.pid, pr.arrivalTime, pr.burstTime,
                    pr.priority, pr.completionTime, pr.turnaroundTime, pr.waitingTime);
        }
        System.out.printf("Average TAT = %.2f, Average WT = %.2f\n", totalTAT / p.length, totalWT / p.length);
    }
}
