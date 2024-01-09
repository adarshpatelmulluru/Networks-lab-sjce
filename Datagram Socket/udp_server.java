import java.io.*;
import java.net.*;
import java.util.*;
public class udp_server {
    public static void main(String []args) throws Exception{
        Scanner in = new Scanner(System.in);
        DatagramSocket socket = new DatagramSocket();
        InetAddress clientadress = InetAddress.getByName("127.0.0.1");
        String message;
        byte buffer[];
        DatagramPacket packet;
        System.out.println("enter something to print:");
        while(true){
            message = in.nextLine();
            buffer = message.trim().getBytes();
            System.out.println(message);
            packet = new DatagramPacket(buffer,buffer.length,clientadress,4000);
            socket.send(packet);
            if(message.equalsIgnoreCase("exit")){
                socket.close();
                break;
            }
        }
    }
}
