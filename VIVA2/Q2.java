package VIVA2;

import java.util.Scanner;
public class Q2 {
    
    public static void main(String[] args) {
        
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter a sentence or number");
        String input=sc.nextLine();
        
        //call the removeSpecialCharacters method
        String cleanString=removeSpecialCharacters(input);
        System.out.println("String after remove special characters,uppercase and spacing :"+ cleanString);
        
        //call the isPalindrome method
        
        if(isPalindrome(cleanString)) {
            System.out.println("True,it is a palindrome");
        }
        else {
            System.out.println("False,it is not a palindrome");
        }
    }
    
    public static String removeSpecialCharacters(String str){
        
        //remove special characters
        String noSpecialCharacters=str.replaceAll("[^a-zA-Z0-9]", "");
        
        //turn to lowercase and remove spaces
        return noSpecialCharacters.toLowerCase().replaceAll("\\s", "");
        
    }
    
    public static boolean isPalindrome(String str){
        
        String newString="";
        char ch;
        
        for(int i=0;i<str.length();i++){
            ch=str.charAt(i); //extracts each character
            newString=ch+newString;//adds each character in front of the existing string
        }
        System.out.println("Reversed word: " +newString);
        return str.equals(newString); //check if the reversed string is equals to the original string
    }
}