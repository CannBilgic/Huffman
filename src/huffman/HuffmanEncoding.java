/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package huffman;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


/**
 *
 * @author bilgi
 */
public class HuffmanEncoding {
    public static int firstMinIndex(boolean[] status, int[] frequency) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int i = 0; i < status.length; i++) {

            if (status[i] && min > frequency[i]) {
                min = frequency[i];
                minIndex = i;
            }
        }
        return minIndex;

    }

    public static int secondMinIndex(boolean[] status, int[] frequency, int firstMax) {
        int min = Integer.MAX_VALUE, minIndex = -1;
        for (int i = 0; i < status.length; i++) {

            if (status[i] && min > frequency[i] && i != firstMax) {
                min = frequency[i];
                minIndex = i;
            }
        }
        return minIndex;

    }

    public static String[] characterMapping(int[] characterFrequency) {
        int processCount = Others.nonZeroFrequency(characterFrequency);
        boolean[] status = new boolean[processCount];
        String[] mappingBits = new String[processCount];
        int[] frequency = new int[processCount];
        String[] childrenTrace = new String[processCount];
        int tempProcessCount = 0;

        for (int i = 0; i < status.length; i++) {
            status[i] = true;
        }
        for (int i = 0; i <= 255; i++) {

            if (characterFrequency[i] != 0) {
                frequency[tempProcessCount] = characterFrequency[i];
                mappingBits[tempProcessCount] = "";
                childrenTrace[tempProcessCount] = "";
                tempProcessCount++;
            }

        }

        while (processCount != 1) {

            int firstMinIndex = firstMinIndex(status, frequency);
            int secondMinIndex = secondMinIndex(status, frequency, firstMinIndex);
            if (firstMinIndex == -1 || secondMinIndex == -1) {
                System.out.println("The Input File is Empty.");
                break;
            }

            int sum = frequency[firstMinIndex] + frequency[secondMinIndex];

            if (secondMinIndex > firstMinIndex) {
                frequency[firstMinIndex] = sum;
                if (!childrenTrace[firstMinIndex].contains("" + firstMinIndex)) {
                    childrenTrace[firstMinIndex] = childrenTrace[firstMinIndex] + "," + firstMinIndex;

                }
                if (!childrenTrace[secondMinIndex].contains("" + secondMinIndex)) {

                    childrenTrace[secondMinIndex] = childrenTrace[secondMinIndex] + "," + secondMinIndex;
                }
                String[] splitOne = childrenTrace[firstMinIndex].split(",");

                for (int i = 0; i < splitOne.length; i++) {
                    if (!splitOne[i].equals("")) {
                        mappingBits[Integer.parseInt(splitOne[i])] = "0" + mappingBits[Integer.parseInt(splitOne[i])];
                    }
                }
                String[] splitTwo = childrenTrace[secondMinIndex].split(",");
                for (int i = 0; i < splitTwo.length; i++) {
                    if (!splitTwo[i].equals("")) {
                        mappingBits[Integer.parseInt(splitTwo[i])] = "1" + mappingBits[Integer.parseInt(splitTwo[i])];
                    }
                }

                if (!childrenTrace[secondMinIndex].trim().startsWith(",")) {
                    childrenTrace[firstMinIndex] = childrenTrace[firstMinIndex] + "," + childrenTrace[secondMinIndex];
                } else {
                    childrenTrace[firstMinIndex] = childrenTrace[firstMinIndex] + childrenTrace[secondMinIndex];
                }

                status[secondMinIndex] = false;

            } else {
                frequency[secondMinIndex] = sum;
                if (!childrenTrace[firstMinIndex].contains("" + firstMinIndex)) {
                    childrenTrace[firstMinIndex] = childrenTrace[firstMinIndex] + "," + firstMinIndex;

                }
                if (!childrenTrace[secondMinIndex].contains("" + secondMinIndex)) {

                    childrenTrace[secondMinIndex] = childrenTrace[secondMinIndex] + "," + secondMinIndex;
                }
                String[] splitOne = childrenTrace[firstMinIndex].split(",");

                for (int i = 0; i < splitOne.length; i++) {
                    if (!splitOne[i].equals("")) {
                        mappingBits[Integer.parseInt(splitOne[i])] = "1" + mappingBits[Integer.parseInt(splitOne[i])];
                    }
                }
                String[] splitTwo = childrenTrace[secondMinIndex].split(",");
                for (int i = 0; i < splitTwo.length; i++) {
                    if (!splitTwo[i].equals("")) {
                        mappingBits[Integer.parseInt(splitTwo[i])] = "0" + mappingBits[Integer.parseInt(splitTwo[i])];
                    }
                }

                if (!childrenTrace[firstMinIndex].trim().startsWith(",")) {
                    childrenTrace[secondMinIndex] = childrenTrace[secondMinIndex] + "," + childrenTrace[firstMinIndex];
                } else {
                    childrenTrace[secondMinIndex] = childrenTrace[secondMinIndex] + childrenTrace[firstMinIndex];
                }

                status[firstMinIndex] = false;

            }

            processCount--;

        }

        return mappingBits;
    }

    public static String characterBits(char character, int[] characterFrequency, String[] characterMappingTable) {
        int processCount = Others.nonZeroFrequency(characterFrequency);
        char[] characters = new char[processCount];
        int tempProcessCount = 0;
        for (int i = 0; i <= 255; i++) {

            if (characterFrequency[i] != 0) {

                characters[tempProcessCount] = (char) (i);

                tempProcessCount++;
            }

        }

        int mappedBitsCombinationIndex = -1;
        for (int i = 0; i < characters.length; i++) {
            if (characters[i] == character) {

                mappedBitsCombinationIndex = i;
                break;
            }

        }
        return characterMappingTable[mappedBitsCombinationIndex];
    }

    public static String huffmanEncoding(String[] characterMapping, String input, String output, String[] characterMappingTable, int[] alphabetFrequency) throws FileNotFoundException, IOException {

        String cipherText = "";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(input), Charset.forName("UTF-8")));

        int c;
        while ((c = bufferedReader.read()) != -1) {
            cipherText += characterBits((char) c, alphabetFrequency, characterMappingTable);

        }

        bufferedReader.close();
        File file = new File(output);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(cipherText);

        bufferedWriter.close();

        return cipherText;
    }

    public static void writeCharacterMappingTable(String[] characterMappingTable, int[] characterFrequency, String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(filename);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        int c = 0;
        for (int i = 0; i < characterFrequency.length; i++) {
            if (characterFrequency[i] != 0) {
                bufferedWriter.write(i + "," + characterMappingTable[c++] + "," + characterFrequency[i]);
            }
            if (i < (characterFrequency.length - 1) && characterFrequency[i] != 0) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.close();
    }
    
}
