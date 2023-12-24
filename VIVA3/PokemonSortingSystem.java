package VIVA3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Arrays;

// public class PokemonSortingSystem {

//     // Instance variable
//     private Pokemon[] pokemonList;

//     // Constructor
//     public PokemonSortingSystem(Pokemon[] pokemonList) {
//         this.pokemonList = pokemonList;
//     }

//     // Method to sort
//     public void sortPokemonList() {
//         Arrays.sort(pokemonList, Comparator.comparingDouble(Pokemon::getStrength));
//     }

//     // Method to determine winner
//     public String[] determineWinner(String opponentName) {
//         ArrayList<String> winners = new ArrayList<>();
        
//         // Find the opponent Pokemon
//         Pokemon opponent = null;
//         for (Pokemon pokemon : pokemonList) {
//             if (pokemon.getName().equals(opponentName)) {
//                 opponent = pokemon;
//                 break;
//             }
//         }
    
//         // If opponent not found, return an empty array
//         if (opponent == null) {
//             return new String[]{};
//         }
    
//         // Determine winners based on both strength and type effectiveness
//         for (Pokemon pokemon : pokemonList) {
//             if (isWinner(pokemon, opponent)) {
//                 winners.add(pokemon.getName());
//             }
//         }
    
//         // Sort the winners alphabetically
//         Collections.sort(winners);
    
//         // Construct the final output in the desired format
//         StringBuilder output = new StringBuilder();
//         for (String winner : winners) {
//             output.append(winner).append(" ");
//         }
    
//         // Convert the final output to a String array
//         return new String[]{output.toString().trim()};
//     }
    
    
//     private boolean isWinner(Pokemon attacker, Pokemon defender) {
//         double effectiveness = calculateEffectiveness(attacker.getType(), defender.getType());
//         return attacker.getStrength() * effectiveness > defender.getStrength();
//     }

//     private double calculateEffectiveness(String attackerType, String defenderType) {
//         // Define type effectiveness buffs
//         double[][] typeEffectiveness = {
//                 {1.0, 1.5, 1.4},  // Flame
//                 {1.0, 1.0, 1.25}, // Grass
//                 {1.25, 1.4, 1.0}  // Water
//         };
    
//         int attackerIndex = getTypeIndex(attackerType);
//         int defenderIndex = getTypeIndex(defenderType);
    
//         if (attackerIndex != -1 && defenderIndex != -1) {
//             return typeEffectiveness[attackerIndex][defenderIndex];
//         }
    
//         // Return 1.0 if types are not recognized
//         return 1.0;
//     }
    
//     private int getTypeIndex(String type) {
//         switch (type.toLowerCase()) {
//             case "flame":
//                 return 0;
//             case "grass":
//                 return 1;
//             case "water":
//                 return 2;
//             default:
//                 return -1; // Unknown type
//         }
//     }
    
// }


class PokemonSortingSystem {
    
    // Instance variable
    private Pokemon[] pokemonList;

    // Constructor
    public PokemonSortingSystem(Pokemon[] pokemonList) {
        this.pokemonList = pokemonList;
    }

    // Method to sort
    public void sortPokemonList() {
        Arrays.sort(pokemonList, Comparator.comparingDouble(Pokemon::getStrength));
    }

    // public String[] determineWinner(String opponentName) {
    //     ArrayList<String> winners = new ArrayList<>();

    //     Pokemon opponent = findPokemonByName(opponentName);

    //     if (opponent == null) {
    //         return new String[]{};
    //     }

    //     for (Pokemon pokemon : pokemonList) {
    //         if (isWinner(pokemon, opponent)) {
    //             winners.add(pokemon.getName());
    //         }
    //     }

    //     Collections.sort(winners);
    //     return winners.toArray(new String[0]);
    // }

    // private boolean isWinner(Pokemon attacker, Pokemon defender) {
    //     double effectiveness = getTypeEffectiveness(attacker.getType(), defender.getType());
    //     return attacker.getStrength() * effectiveness > defender.getStrength();
    // }

    // private double getTypeEffectiveness(String attackerType, String defenderType) {
    //     double[][] typeEffectiveness = {
    //             {1.0, 1.5, 1.4},
    //             {1.25, 1.0, 1.0},
    //             {1.0, 1.25, 1.0}
    //     };

    //     int attackerIndex = getTypeIndex(attackerType);
    //     int defenderIndex = getTypeIndex(defenderType);

    //     if (attackerIndex != -1 && defenderIndex != -1) {
    //         return typeEffectiveness[attackerIndex][defenderIndex];
    //     }

    //     return 1.0;
    // }

    // private int getTypeIndex(String type) {
    //     switch (type.toLowerCase()) {
    //         case "flame":
    //             return 0;
    //         case "grass":
    //             return 1;
    //         case "water":
    //             return 2;
    //         default:
    //             return -1;
    //     }
    // }

    // private Pokemon findPokemonByName(String name) {
    //     for (Pokemon pokemon : pokemonList) {
    //         if (pokemon.getName().equalsIgnoreCase(name)) {
    //             return pokemon;
    //         }
    //     }
    //     return null;
    // }

    public String[] determineWinner(String opponentName){
        ArrayList<String> winners = new ArrayList<>();

        Pokemon opponent = findPokemon(opponentName);

        for(Pokemon pokemon : pokemonList){
            if(isWinner(pokemon, opponent))  //if pokemon > opponent then dia win
                winners.add(pokemon.getName());
        }

        return winners.toArray(new String[0]);
    }

    private Pokemon findPokemon(String opponame){
        for(Pokemon pokemon : pokemonList){
            if(pokemon.getName().equals(opponame))
                return pokemon;
        }
        return null;
    }

    private boolean isWinner(Pokemon pokemon, Pokemon opponent){
        double effective = effectiveness(pokemon.getType(), opponent.getType());
        if(pokemon.getStrength() * effective > opponent.getStrength())
            return true;
        else
            return false;
    }

    private double effectiveness(String pokemonType, String opponentType){

        if((pokemonType.equals("Flame")) && (opponentType.equals("Grass"))){
            return 1.5;
        }else if((pokemonType.equals("Grass")) && (opponentType.equals("Water"))){
            return 1.25;
        }else if((pokemonType.equals("Water")) && (opponentType.equals("Flame"))){
            return 1.4;
        }else
            return 1.0;
    }
}