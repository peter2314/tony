import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OptimalReplacement {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int frames, pointer = 0, hit = 0, fault = 0, strng_size;
        boolean isFull = false;
        int[] buffer;
        int[] ref;
        int[][] mem_layout;

        // Enter the number of frames
        System.out.print("Enter the total number of Frames: ");
        frames = Integer.parseInt(br.readLine());

        // Enter the reference string size
        System.out.print("Enter the reference string size: ");
        strng_size = Integer.parseInt(br.readLine());

        ref = new int[strng_size];
        mem_layout = new int[strng_size][frames];
        buffer = new int[frames];
        for (int j = 0; j < frames; j++)
            buffer[j] = -1;

        // Input reference string
        System.out.println("Enter the reference string:");
        for (int i = 0; i < strng_size; i++) {
            ref[i] = Integer.parseInt(br.readLine());
        }

        System.out.println();

        for (int i = 0; i < strng_size; i++) {
            int search = -1;
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == ref[i]) {
                    search = j;
                    hit++;
                    break;
                }
            }

            if (search == -1) {
                if (isFull) {
                    int[] index = new int[frames];
                    boolean[] index_flag = new boolean[frames];
                    for (int j = i + 1; j < strng_size; j++) {
                        for (int k = 0; k < frames; k++) {
                            if ((ref[j] == buffer[k]) && (!index_flag[k])) {
                                index[k] = j;
                                index_flag[k] = true;
                                break;
                            }
                        }
                    }

                    int pos = -1, farthest = i + 1;
                    for (int j = 0; j < frames; j++) {
                        if (!index_flag[j]) {
                            pos = j;
                            break;
                        } else if (index[j] > farthest) {
                            farthest = index[j];
                            pos = j;
                        }
                    }

                    buffer[pos] = ref[i];
                } else {
                    buffer[pointer] = ref[i];
                    pointer++;
                    if (pointer == frames) {
                        pointer = 0;
                        isFull = true;
                    }
                }
                fault++;
            }

            for (int j = 0; j < frames; j++)
                mem_layout[i][j] = buffer[j];
        }

        System.out.println("Page Replacement Process:\n");
        for (int i = 0; i < strng_size; i++) {
            System.out.printf("Page %2d -> ", ref[i]);
            for (int j = 0; j < frames; j++) {
                if (mem_layout[i][j] == -1)
                    System.out.print("[ ] ");
                else
                    System.out.print("[" + mem_layout[i][j] + "] ");
            }
            System.out.println();
        }

        System.out.println("\nTotal Hits: " + hit);
        System.out.println("Total Faults: " + fault);
        System.out.printf("Hit Ratio: %.2f%n", (float) hit / strng_size);
        System.out.printf("Fault Ratio: %.2f%n", (float) fault / strng_size);
    }
}
