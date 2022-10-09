package utils;

import java.util.Random;

public class RandomlyGeneratedUtils {

    public static final String LATIN_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static Random random = new Random();

    public static String getRandomlyGeneratedSymbols(String symbols, int amountOfSymbols) {
        StringBuilder randomlyGeneratedSymbols = new StringBuilder();
        for (int i = 0; i < amountOfSymbols; i++) {
            int randomCharIndex = random.nextInt(symbols.length());
            char randomCharacter = symbols.charAt(randomCharIndex);
            randomlyGeneratedSymbols.append(randomCharacter);
        }
        return randomlyGeneratedSymbols.toString();
    }
}