// package VIVA3;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Collections;
// import java.util.HashMap;
// import java.util.Map;

// public class PokemonSortingSystem {
//     //instance variable
//     private List<Pokemon> pokemonList;

//     //constructor
//     public PokemonSortingSystem(List<Pokemon> pokemonList){
//         this.pokemonList = pokemonList;
//     }

//     //method to sort
//     public void sortPokemonList(){
//         Collections.sort(pokemonList, (p1, p2) -> Double.compare(p1.getStrength(), p2.getStrength()));
//     }

//     //to determine winner
//     public List<String> determineWinner(String opponentName){
//         List<String> winner = new ArrayList<>();
//         for(Pokemon pokemon : pokemonList){
//             if(effectiveness(pokemon.getType(), getOpponentType(opponentName)))
//                 winner.add(pokemon.getName());
//         }
//         return winner;
//     }

//     //
//     public boolean effectiveness(String pokeType, String oppoType){
//         Map<String, Double> effective = new HashMap<>();
//         effective.put("Flame : Grass", 1.5);
//         effective.put("Grass : Water", 1.25);
//         effective.put("Water : Flame", 1.4);
        
//         return effective.containsKey(pokeType + ":" + oppoType);
//     }

//     //check if opponent type 
//     public String getOpponentType(String opponentName){
//         for(Pokemon pokemon : pokemonList){
//             if(pokemon.getName().equals(opponentName))
//                 return pokemon.getType();
//         }
//         return null;
//     }
// }


package VIVA3;

import java.util.Arrays;

public class PokemonSortingSystem{
    //instance variable
    private Pokemon[] pokemonList;

    //constructor
    public PokemonSortingSystem(Pokemon[] pokemonList) {
        this.pokemonList = pokemonList;
    }

    //method to sort
    public void sorPokemonList(){
        Arrays.sort(this.pokemonList);
    }

    //method to determine winner
    public String[] determineWinner(String opponentName, Pokemon[] pokemonList){
        final Double[] opponentStrength = {0.0};

        Arrays.stream(pokemonList).findFirst().ifPresent(pokemon -> opponentStrength[0] = pokemon.getStrength());

        return Arrays.stream(pokemonList).filter(pokemon -> pokemon.getStrength() > opponentStrength[0] * getStrengthMultiplier(pokemon.getType())).map(Pokemon :: getName).toArray(String[] :: new);
    }

    public double getStrengthMultiplier(String type){
        switch(type){
            case "Flame":
                return 1.5;
            case "Grass":
                return 1.25;
            case "Water":
                return 1.4;
            default:
                return 1.0;
        }
    }
}