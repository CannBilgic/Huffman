/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package huffman;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 *
 * @author bilgi
 */
public class HuffmanDecoding {
    public static String deciferedCharacter(String bitCombination, int[] alphabetFrequency, String[] characterMappingTable) {
        int processCount = Others.nonZeroFrequency(alphabetFrequency);
        char[] characters = new char[processCount];
        int tempProcessCount = 0;
        String character = "";
        for (int i = 0; i <= 255; i++) {

            if (alphabetFrequency[i] != 0) {

                characters[tempProcessCount] = (char) (i);

                tempProcessCount++;
            }

        }
        for (int i = 0; i < characterMappingTable.length; i++) {
            if (characterMappingTable[i].equals(bitCombination)) {
                character = "" + characters[i];
                break;
            }
        }

        return character;
    }

    public static String huffmanDecoding(String cipher, String[] characterMappingTable, int[] alphabetFrequency) throws FileNotFoundException, IOException {

        String sourceText = "";

        String storage = "";
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(cipher), Charset.forName("UTF-8")));

        int c;

        while ((c = bufferedReader.read()) != -1) {

            storage = "" + (char) c;

            String decipheredCharacter = "";

            while ((decipheredCharacter = deciferedCharacter(storage, alphabetFrequency, characterMappingTable)).equals("")) {

                if ((c = bufferedReader.read()) != -1) {
                    storage += (char) c;
                }

            }

            sourceText += decipheredCharacter;

        }

        return sourceText;
    }

    public static int[] readCharacterFrequency(String characterMappingInput) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(characterMappingInput);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int[] characterFrequency = new int[256];
        String line;

        String[] store;

        while ((line = bufferedReader.readLine()) != null) {

            store = line.split(",");
            characterFrequency[Integer.parseInt(store[0])] = Integer.parseInt(store[2]);

        }

        bufferedReader.close();

        return characterFrequency;
    }

    public static String[] readCharacterMappingTable(String characterMappingInput, int[] characterFrequency) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(characterMappingInput);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;

        String[] store;
        int c = 0;
        int nonZeroCharacterSize = Others.nonZeroFrequency(characterFrequency);
        String[] characterMappingTable = new String[nonZeroCharacterSize];
        while ((line = bufferedReader.readLine()) != null) {

            store = line.split(",");
            characterMappingTable[c++] = store[1];

        }

        bufferedReader.close();

        return characterMappingTable;
    }
    
}
