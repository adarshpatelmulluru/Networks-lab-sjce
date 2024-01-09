import java.util.*;
import java.io.*;
import java.net.*;
public class tcp_client {
    public static void main(String []args) throws Exception{
        Socket socket = new Socket("127.0.0.1",8000);
        System.out.println("enter the file name :");
        BufferedReader nameread = new BufferedReader(new InputStreamReader(System.in));
        String filename = nameread.readLine();
        OutputStream ostream = socket.getOutputStream();
        PrintWriter pwrite  = new PrintWriter(ostream,true);
        pwrite.println(filename);

        InputStream istream = socket.getInputStream();
        BufferedReader contentread = new BufferedReader(new InputStreamReader(istream));
        String str;
        while((str=contentread.readLine())!=null){
            System.out.println(str);
        }
        contentread.close();
        nameread.close();
        pwrite.close();
        socket.close();
    }
}
