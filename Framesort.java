import java.util.*;
public class Framesort {
    public static void main(String []args){
        Scanner in = new Scanner(System.in);
        Random r = new Random();
        List<int []> frames = new ArrayList<>();
        int no_of_packets;
        System.out.println("enter the number of packets");
        no_of_packets = in.nextInt();
        int i=0;
        while(i<no_of_packets){
            System.out.println("enter the data to send one by one : ");
            frames.add(new int []{r.nextInt(1000),in.nextInt()});
            i++;
        }
        System.out.println("\nBefore sorting\n");
        System.out.println("seqno\tdata");
        for(int []item:frames){
            System.out.println("seqno: "+item[0]+"\tdata: "+item[1]);
        }

        Collections.sort(frames,(a,b)->Integer.compare(a[0],b[0]));

        System.out.println("\nafter sorting of frames");
        System.out.println("seqno\tdata");
        for(int []item:frames){
            System.out.println("seqno: "+item[0]+"\tdata: "+item[1]);
        }

    }
}
