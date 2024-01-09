import java.util.*;
public class Bellmanford {
    private static int n;
    private static int [][]graph;

    private static void bellaman(int source){
        int []distance = new int[n];

        Arrays.fill(distance,Integer.MAX_VALUE);
        distance[source] =0;

        for(int i=0;i<n;i++){
            for(int u=0;u<n;u++){
                for(int v=0;v<n;v++){
                    if(graph[u][v]!=0 && distance[u]!=Integer.MAX_VALUE && distance[u]+distance[v] < distance[v] ){
                        distance[v] = distance[u]+distance[v];
                    }
                }
            }
        }
        for(int u=0;u<n;u++){
            for(int v=0;v<n;v++){
                if(graph[u][v]!=0 && distance[u]!=Integer.MAX_VALUE && distance[u]+distance[v] < distance[v] ){
                    System.out.println("negative weight cycle detected");
                }
            }
        }
        System.out.println("the minimum distance of each node from source node"+(source+1)+ " are as follows");
        System.out.println("node \t distance");
        for(int i=0;i<distance.length;i++){
            System.out.println((i+1)+ "\t"+ distance[i]);
        }
    }
    public static void main(String[] args){
        Scanner in =new Scanner(System.in);
        System.out.println("enter the number of vertices: ");
        n = in.nextInt();
        graph = new int[n][n];
        System.out.println("enter the weight matrix:");
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                graph[i][j] = in.nextInt();
            }
        }
        System.out.println("enter the source vertex :");
        int source = in.nextInt();
        bellaman(source-1);
    }
}
