
import java.util.Scanner;
public class Tokenbucket{

    public static void main(String[] args) throws InterruptedException {
        int tokens = 0; // initial number of tokens in the bucket
        int rate = 5;  // rate at which tokens are added to the bucket
        int capacity = 10; // maximum number of tokens the bucket can hold

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of requests: ");
        int n = scanner.nextInt();

        int[] requests = new int[n];

        System.out.print("Enter no. of packets per request: ");
        for (int i = 0; i < n; i++) {
            requests[i] = scanner.nextInt();
        }

        for (int i = 0; i < n; i++) {
            // add tokens to the bucket at a fixed rate
            tokens = Math.min(tokens + rate, capacity);

            // wait for 1 second
            Thread.sleep(1000);

            if (tokens >= requests[i]) {
                // remove the requested tokens from the bucket
                tokens -= requests[i];
                System.out.println("Request for "+requests[i]+" granted, tokens remaining: " + tokens);
            } else {
                System.out.println("Request for "+requests[i]+" denied, not enough tokens: " + tokens);
            }
        }

        // Close the scanner
        scanner.close();
    }
}

