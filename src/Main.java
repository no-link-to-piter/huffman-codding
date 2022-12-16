import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static StringBuilder getDictionary(HashMap<Character, String> _hashmap) {
        StringBuilder dictionary = new StringBuilder();
        for (Map.Entry<Character, String> entry: _hashmap.entrySet()){
            String key = entry.getKey().toString();
            String val = entry.getValue();
            dictionary.append(key).append(": ").append(val).append("\n");
        }
        return dictionary;
    }

    public static String readFile(String _filename){
        StringBuilder result = new StringBuilder();
        File filename = new File(_filename);
        try (BufferedReader in = new BufferedReader(new FileReader(filename))){
            String line = in.readLine();
            while (line != null){
                result.append(line);
                line = in.readLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return result.toString();
    }

    public static void writeFile(String _filename, String _encoded){
        try (FileWriter myWriter = new FileWriter(_filename)) {
            myWriter.write(_encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getResultText(Huffman huffman, Boolean isEncode) {
        StringBuilder dictionary = getDictionary(huffman.hashMapCharCode);
        String response = isEncode ? huffman.encode() : Huffman.decode(null, "");
        return "Size: " + huffman.hashMapCharCode.size() + "\n\n" + "Dictionary:" + "\n" + dictionary.toString() + "\n" + "Result:" + "\n" + response;
    }


    public static void main(String[] args) {
        try {
            String type = args[0];
            String inputFile = args[1];
            String outputFile = args[2];

            String inputText = readFile(inputFile);
            Huffman huffman = new Huffman(inputText);

            writeFile(outputFile, getResultText(huffman, type.equals("--encode")));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}