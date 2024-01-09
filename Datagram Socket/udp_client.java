import java.util.*;
import java.net.*;
import java.io.*;
public class udp_client {
    public static void main(String []args) throws Exception{
        DatagramSocket socket = new DatagramSocket(4000);
        String message;
        byte []buffer;
        DatagramPacket packet;
        while(true){
            buffer = new byte[65536];
            packet = new DatagramPacket(buffer,buffer.length);
            socket.receive(packet);
            message = new String(buffer).trim();
            System.out.println(message.toUpperCase());
            if(message.equalsIgnoreCase("exit")){
                socket.close();
                break;
            }
        }
    }
}
