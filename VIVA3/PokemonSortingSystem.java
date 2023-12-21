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