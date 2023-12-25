package VIVA3;

public class Main{
    public static void main(String[] args){
    Aran aran1 = new Aran();
    System.out.println("PTM : " + Aran.isValid("PTM"));
    System.out.println("PTMMTP : " + Aran.isValid("PTMMTP"));
    System.out.println("BPTMBPTM : " + Aran.isValid("BPTMBPTM"));
    System.out.println("PT : " + Aran.isValid("PT"));
    System.out.println();
    System.out.println(aran1.toString());

    Aran aran2 = new Aran(5);
    aran2.setLevel(260);
    System.out.println(aran2.toString()); 
    }

}