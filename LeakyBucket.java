import java.util.*;
public class LeakyBucket {
    public static void main(String []args){
        Scanner in =new Scanner(System.in);
        int no_of_packets;
        int bucket_size;
        int output_rate;
        System.out.println("Enter the number of packets :");
        no_of_packets = in.nextInt();
        System.out.println("Enter the bucket size :");
        bucket_size = in.nextInt();
        System.out.println("enter the output rate :");
        output_rate = in.nextInt();

        int []packets = new int[no_of_packets];
        List<Integer> bucket = new ArrayList<>();

        System.out.println("enter the packets one by one :");
        for(int i=0;i<no_of_packets;i++){
           packets[i] = in.nextInt();
        }

        for(int packet:packets){
            if(packet <= bucket_size){
                bucket.add(packet);
            }
            else{
                System.out.println(packet + "  has been dropped.. of oversize");
            }
        }

        while(!bucket.isEmpty()){
            if(bucket.get(0) <= output_rate){
                System.out.println("packet "+ bucket.get(0)+ "sent..");
                bucket.remove(0);
            }
            else{
                System.out.println("packet is sent after fragmenting " + output_rate);
                bucket.set(0,bucket.get(0)-output_rate);
            }
        }
    }
}
