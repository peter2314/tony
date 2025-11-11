class FIFO {
    public static void main(String[] args) {
        int incomingStream[] = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1};
        int frames = 3;
        int pages = incomingStream.length;
        int[] temp = new int[frames];
        int pageFaults = 0;
        int pos = 0; // to track FIFO position

        // Initialize all frames as empty
        for (int i = 0; i < frames; i++) {
            temp[i] = -1;
        }

        System.out.println("Incoming\tFrame 1\tFrame 2\tFrame 3\tStatus");
        System.out.println("--------------------------------------------------------");

        for (int i = 0; i < pages; i++) {
            int currentPage = incomingStream[i];
            boolean hit = false;

            // Check if page is already in any frame
            for (int j = 0; j < frames; j++) {
                if (temp[j] == currentPage) {
                    hit = true;
                    break;
                }
            }

            if (!hit) {
                // Replace page using FIFO order
                temp[pos] = currentPage;
                pos = (pos + 1) % frames;
                pageFaults++;
            }

            // Print current frame state
            System.out.printf("%-8d\t", currentPage);
            for (int j = 0; j < frames; j++) {
                if (temp[j] == -1)
                    System.out.print("-\t");
                else
                    System.out.print(temp[j] + "\t");
            }
            System.out.println(hit ? "Hit" : "Fault");
        }

        System.out.println("\nTotal Page Faults: " + pageFaults);
        System.out.println("Total Hits: " + (pages - pageFaults));
        System.out.printf("Page Fault Ratio: %.2f%n", (double) pageFaults / pages);
        System.out.printf("Hit Ratio: %.2f%n", (double) (pages - pageFaults) / pages);
    }
}
