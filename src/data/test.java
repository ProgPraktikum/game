package data;

public class test {
    public static void main(String args[]){
        int breite=6;
        int hoehe=6;
        int occupancy=breite*hoehe*30/100;
        int currentsize=2;
        int maxsize;
        if(breite>hoehe){
            maxsize = breite/2;
        }
        else{
            maxsize= hoehe/2;
        }
        occupancy -= maxsize;
        while(occupancy>0){
            System.out.println();
            for(int i=2; i<=currentsize;i++){
                if(occupancy==0){
                    break;
                }
                if(occupancy-i <0){
                     int iz= i + (occupancy-i);
                    if(iz==1){
                        //remove previous element
                       // occupancy +=
                    }
                    i =iz;

                }
                System.out.print(i+" ");
                occupancy -=i;


            }
            currentsize++;

        }
        System.out.print(maxsize);
    }
}
