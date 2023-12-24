package VIVA3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;

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

    //method to find winner
    public String[] determineWinner(String opponentName){
        ArrayList<String> winners = new ArrayList<>();

        Pokemon opponent = findPokemon(opponentName);

        for(Pokemon pokemon : pokemonList){
            if(isWinner(pokemon, opponent))
                winners.add(pokemon.getName());
        }

        return winners.toArray(new String[0]);
    }

    //to find what pokemon it is
    private Pokemon findPokemon(String opponame){
        for(Pokemon pokemon : pokemonList){
            if(pokemon.getName().equals(opponame))
                return pokemon;
        }
        return null;
    }

    //check validity of winner
    private boolean isWinner(Pokemon pokemon, Pokemon opponent){
        double[] effective = effectiveness(pokemon.getType(), opponent.getType());

        if(pokemon.getStrength() * effective[0] > opponent.getStrength() * effective[1])
            return true;
        else
            return false;
    }

    //determine effectiveness value
    private double[] effectiveness(String pokemonType, String opponentType){
        double[] result = new double[2];

        if((pokemonType.equals("Flame")) && (opponentType.equals("Grass"))){
            result[0] = 1.5;
            result[1] = 1.0;
        }else if((pokemonType.equals("Grass")) && (opponentType.equals("Water"))){
            result[0] = 1.25;
            result[1] = 1.0;
        }else if((pokemonType.equals("Water")) && (opponentType.equals("Flame"))){
            result[0] = 1.4;
            result[1] = 1.0;
        }else if((pokemonType.equals("Grass")) && (opponentType.equals("Flame"))){
            result[0] = 1.0;
            result[1] = 1.5;
        }else if((pokemonType.equals("Water")) && (opponentType.equals("Grass"))){
            result[0] = 1.0;
            result[1] = 1.25;
        }else if((pokemonType.equals("Flame")) && (opponentType.equals("Water"))){
            result[0] = 1.0;
            result[1] = 1.4;
        }else{
            result[0] = 1.0;
            result[1] = 1.0;
        }
        return result;
    }
}