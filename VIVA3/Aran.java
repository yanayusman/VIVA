package VIVA3;

public class Aran{
    private int level;
    private int jobAdvancement;
    private static final int[] jobAdvMap = {-1, 10, 60, 100, 200, 260, 300};

    //empty constructor
    public Aran(){
        this.level = 300;
        this.jobAdvancement = -1;
    }

    //constructor w parameter
    public Aran(int jobAdvancement){
        if(jobAdvancement < 0 || jobAdvancement >= jobAdvMap.length)
            System.out.println("Invalid job advancement");
        this.level = jobAdvMap[jobAdvancement];
        this.jobAdvancement = jobAdvancement;
    }

    //accessor
    public int getLevel(){
        return level;
    }

    //mutator 
    public void setLevel(int level){
        if(level < 1 || level > 300){
            System.out.println("Invalid level");
        }
        for (int i = 1; i < jobAdvMap.length; i++) {
            if (level < jobAdvMap[i]) {
                this.jobAdvancement = i - 1;
                break;
            }
        }

        this.level = level;
    }

    //method for validation
    public static boolean isValid(String input){
        boolean valid = true;

        char[] character = input.toCharArray();

        for(int i = 0; i < character.length - 1; i++){
            char current = character[i];
            char next = character[i + 1];
            if (current == 'B' && (next != 'P' && next != 'T')) {
                valid = false;
                break;
            } else if (current == 'P' && next != 'T') {
                valid = false;
                break;
            } else if (current == 'T' && next != 'M') {
                valid = false;
                break;
            } else if (current != 'B' && current != 'P' && current != 'T' && current != 'M') {
                valid = false;
                break;
            }
        }

        return valid;
    }

    //return all info
    public String toString(){
        StringBuilder result = new StringBuilder("Aran Info\n");
        result.append("Level :").append(level).append("\n");
        result.append("Job Adv : ").append(jobAdvancement).append("\n");
        return result.toString();
    }


}
