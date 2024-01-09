import java.util.*;
import java.io.*;
import java.net.*;

public class tcp_server {
    public static void main(String []args) throws Exception{
        ServerSocket sersock = new ServerSocket(8000);
        System.out.println("server ready to connect");
        Socket sock = sersock.accept();
        System.out.println("Client connected..waiting for file name");
        InputStream istream = sock.getInputStream();
        BufferedReader nameread = new BufferedReader(new InputStreamReader(istream));
        String filename = nameread.readLine();
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream,true);

        try{
           BufferedReader contentread = new BufferedReader(new FileReader(filename));
           String str;
           while((str=contentread.readLine())!=null){
               pwrite.println(str);
           }
           contentread.close();
        }
        catch(FileNotFoundException e){
            System.out.println("file not present..");
        }
        finally{
            System.out.println("closing connection..");
            nameread.close();
            pwrite.close();
            sock.close();
            sersock.close();
        }
    }
}
