package VIVA3;
import java.util.Arrays;
import java.util.Comparator;

public class PokemonSortingSystem {

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

    // Method to determine winner
    public String[] determineWinner(String opponentName) {
      double opponentStrength = Arrays.stream(pokemonList)
                .findFirst()
                .map(Pokemon::getStrength)
                .orElse(0.0);

        return Arrays.stream(pokemonList)
                .filter(pokemon -> pokemon.getStrength() > opponentStrength * getStrengthMultiplier(pokemon.getType()))
                .map(Pokemon::toString) 
                .toArray(String[]::new);
    }

    public double getStrengthMultiplier(String type) {
        switch (type) {
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