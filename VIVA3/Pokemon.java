package VIVA3;

public class Pokemon {
    //instance variable
    private String name;
    private String type;
    private Double strength;

    //constructor
    public Pokemon(String name, String type, Double strength){
        this.name = name;
        this.type = type;
        this.strength = strength;
    }

    //method
    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }

    public Double getStrength(){
        return strength;
    }
}
