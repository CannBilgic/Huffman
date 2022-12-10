/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package huffman;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
/**
 *
 * @author bilgi
 */
public class Others {
    public static int[] frequencyTable(String filename) throws FileNotFoundException, IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), Charset.forName("UTF-8")));
        int[] ascii_alphabet = new int[256];

        int c;
        while ((c = bufferedReader.read()) != -1) {
            ascii_alphabet[c]++;

        }

        bufferedReader.close();
        return ascii_alphabet;
    }

    public static void printHuffmanEncodingTable(String[] mappingBits, char[] characters) {
        for (int i = 0; i < characters.length; i++) {
            System.out.println(characters[i] + " " + mappingBits[i]);
        }

    }

    public static void printNonZeroFrequency(int[] input) {
        System.out.println("Karşılaşılan Karakterler");
        System.out.println("---------");
        for (int i = 0; i <= 255; i++) {
            if (input[i] != 0) {
                System.out.println((char) (i) + " " + input[i]);
            }
        }
        System.out.println("---------");
    }

    public static char[] characterTable(int[] characterFrequency) {
        int processCount = nonZeroFrequency(characterFrequency);
        char[] characters = new char[processCount];
        int tempProcessCount = 0;

        for (int i = 0; i <= 255; i++) {

            if (characterFrequency[i] != 0) {

                characters[tempProcessCount] = (char) i;

                tempProcessCount++;
            }

        }
        return characters;
    }

    public static int nonZeroFrequency(int[] input) {
        int nonZeroFrequency = 0;
        for (int i = 0; i <= 255; i++) {
            if (input[i] != 0) {
                nonZeroFrequency++;
            }
        }
        return nonZeroFrequency;
    }
    
}
