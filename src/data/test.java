package data;

import java.util.ArrayList;
import java.util.LinkedList;

public class test {
    public static void main(String args[]){
        int sizes[] = new int[10];
        int breite=5;
        int hoehe=5;
        int occupancy=breite*hoehe*30/100;
        int currentsize=2;
        int prev=0;
        while(occupancy>0){
            for(int i=2; i<= currentsize; i++){
                if(occupancy-i>=0){
                    occupancy-=i;
                    sizes[i-2]++;
                    prev=i;
                }
                else if(occupancy-i < 0){
                    if(occupancy >=2){
                        sizes[occupancy-2]++;
                        occupancy-=occupancy;
                    }
                    else if(occupancy-i == -1){
                        sizes[prev-2]--;
                        occupancy += prev;
                        sizes[prev-1]++;
                        occupancy -= prev+1;
                    }
                }
                //System.out.print(i+" ");
            }
            currentsize++;
            System.out.println();
        }
        for(int i=0; i<sizes.length;i++){
            System.out.println((i+2)+": "+sizes[i]);
        }
        System.out.println(occupancy);
    }
}
