import java.util.Scanner;

class crc {

    static String crc(String data, String poly, boolean errChk) {
        StringBuilder rem = new StringBuilder(data);
        if (!errChk) {
            for (int i = 0; i < poly.length() -1; i++)
                rem.append("0");
        }
        for (int i = 0; i < rem.length() - poly.length() + 1; i++) {
            if (rem.charAt(i) == '1') {
                for (int j = 0; j < poly.length(); j++) {
                    rem.setCharAt(i + j, (rem.charAt(i + j) == poly.charAt(j)) ? '0' : '1');
                }
            }
        }
        return rem.substring(rem.length() - poly.length() + 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String poly = "100001000100010101";

        // Enter Data to be sent
        System.out.print("Enter Data to be sent: ");
        String data = scanner.next();

        String rem = crc(data, poly, false);
        String codeword = data + rem;

        System.out.println("Remainder: " + rem);
        System.out.println("Codeword: " + codeword);

        // Checking error
        System.out.print("Enter received codeword: ");
        String recvCodeword = scanner.next();

        String recvRem = crc(recvCodeword, poly, true);
        long result = Long.parseLong(recvRem);

        if (result == 0) {
            System.out.println("No Error");
        } else {
            System.out.println("Error Detected");
        }

        scanner.close();
    }
}
