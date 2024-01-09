
import java.util.*;
public class RED {
    public static void main(String []args){
        int max_queue_size = 20;
        int max_packets = 25;
        double max_probablity = 0.8;
        double min_probablity = 0.2;
        Random r =new Random();
        int queue_size =0;
        double drop_probablity =min_probablity;
        for(int i=0;i<max_packets;i++){
            if(queue_size == max_queue_size){
                System.out.println((i+1)+" th the queue is filled .. packet dropped");
                drop_probablity = min_probablity;
            }
            else if(r.nextDouble() < drop_probablity){
                System.out.println((i+1) +" th packet dropped..");
                drop_probablity = (max_probablity-min_probablity)/(max_queue_size-1);
            }
            else{
                System.out.println((i+1) +" th packet accepted");
                drop_probablity = min_probablity;
                queue_size++;
            }
        }
    }
}
