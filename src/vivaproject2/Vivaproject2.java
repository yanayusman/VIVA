
package vivaproject2;

import java.util.Scanner;
public class Vivaproject2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        
        System.out.print("Enter the lower limit of the range: ");
        int low = sc.nextInt();
        
        System.out.print("Enter the upper limit of the range: ");
        int up = sc.nextInt();
        
        int[] prime= generatePrimes(low,up);
        
        System.out.print("The prime within the range[" + low + "," + up + "] are: ");
        
        for(int p=0; p<prime.length; p++){
            System.out.print(prime[p] + " ");
    }
        System.out.println();
    }
    
    public static int[] generatePrimes(int low, int up){
       
        // declare array size depemding on user's input
        int[] getRange = new int[up-low+1];
          int z=0;
        
           // store numbers in the range to getRange array
           for(int q=low; q<=up; q++){
             
               int i=0;
               getRange[i]=q;
               i++;}
            
        
        int[] getPrime = new int[getRange.length];
          
          
          for(int i=low; i<=up; i++){ 
            if(i>1){
            boolean isPrime = true;
            for(int j=2; j<=Math.sqrt(i); j++){
                if (i%j==0){
                    isPrime = false;
                    break; 
                   }   
               }         
                  if(isPrime){
                    getPrime[z]= i;
                    z++;
                   }   
          } }   
            int[] tryjap = new int[3];
            for(int y=0; y<tryjap.length; y++){
                System.out.print(tryjap[y]);
            }
            
            int[] result = new int[z];
            for(int r=0 ; r<result.length; r++){
                 result[r] = getPrime[r];
              }
                  return result;
      } 
    
} 
