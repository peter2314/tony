import java.util.*;

public class FirstFit_NextFit {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of memory blocks: ");
        int m = sc.nextInt();
        int[] blocks = new int[m];
        System.out.println("Enter size of each memory block:");
        for (int i = 0; i < m; i++) blocks[i] = sc.nextInt();

        System.out.print("\nEnter number of processes: ");
        int n = sc.nextInt();
        int[] process = new int[n];
        System.out.println("Enter size of each process:");
        for (int i = 0; i < n; i++) process[i] = sc.nextInt();

        firstFit(Arrays.copyOf(blocks, m), process);
        nextFit(Arrays.copyOf(blocks, m), process);
        sc.close();
    }

    static void firstFit(int[] blocks, int[] process) {
        int[] allocate = new int[process.length];
        Arrays.fill(allocate, -1);
        for (int i = 0; i < process.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[j] >= process[i]) {
                    allocate[i] = j;
                    blocks[j] -= process[i];
                    break;
                }
            }
        }
        System.out.println("\n=== First Fit Allocation ===");
        printResult(process, allocate);
    }

    static void nextFit(int[] blocks, int[] process) {
        int[] allocate = new int[process.length];
        Arrays.fill(allocate, -1);
        int pos = 0;

        for (int i = 0; i < process.length; i++) {
            int count = 0;
            while (count < blocks.length) {
                if (blocks[pos] >= process[i]) {
                    allocate[i] = pos;
                    blocks[pos] -= process[i];
                    break;
                }
                pos = (pos + 1) % blocks.length;
                count++;
            }
        }
        System.out.println("\n=== Next Fit Allocation ===");
        printResult(process, allocate);
    }

    static void printResult(int[] process, int[] allocate) {
        System.out.println("Process No.\tProcess Size\tBlock No.");
        for (int i = 0; i < process.length; i++) {
            System.out.print("P" + (i + 1) + "\t\t" + process[i] + "\t\t");
            System.out.println((allocate[i] != -1) ? (allocate[i] + 1) : "Not Allocated");
        }
    }
}
