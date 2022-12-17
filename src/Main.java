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

    public static String readTextFile(String _filename){
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

    public static void decodeCompressedFile(String _filename, String _outputFilename){
        String encoded = "";
        HashMap<String, Character> hm = new HashMap<String, Character>();
        File filename = new File(_filename);
        try (BufferedReader in = new BufferedReader(new FileReader(filename))){
            String line = in.readLine();
            int flag = 0;
            while (line != null){
                if (line.equals("")) {
                    flag += 1;
                }
                if (flag == 1) {
                    String[] tmp = line.split(": ");
                    if (tmp.length == 2) {
                        hm.put(tmp[1], tmp[0].charAt(0));
                    }
                } else if (flag == 2) {
                    encoded = line;
                }
                line = in.readLine();
            }
            String response = Huffman.decode(hm, encoded);
            System.out.println(response);
            writeFile(_outputFilename, response);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void writeFile(String _filename, String _encoded){
        try (FileWriter myWriter = new FileWriter(_filename)) {
            myWriter.write(_encoded);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String createWithZero(String _str) {
        String result = _str;
        for (int i = 0; i < 8 - _str.length(); i++) {
            result = "0" + result;
        }
        return result;
    }

    public static String getResultText(Huffman huffman) {
        StringBuilder dictionary = getDictionary(huffman.hashMapCharCode);
        String response = huffman.encode();
        System.out.println(response);
        String str = "";
        for (int i = 0; i <= response.length()/8; i++) {
            int n = i == response.length()/8 ? response.length() : (i+1)*8;
            int a = Integer.parseInt(response.substring(8*i,n),2);
            str += (char)(a);
        }
        String dec = "";
        for (char ch : str.toCharArray()) {
            int n = (int)ch;
            String tmp = Integer.toBinaryString(n);
            dec += createWithZero(tmp);
        }
        System.out.println(Huffman.decode(huffman.hashMap, dec));
        return str;
    }


    public static void main(String[] args) {
        try {
            String type = args[0];
            String inputFile = args[1];
            String outputFile = args[2];

            if (type.equals("--encode")) {
                String inputText = readTextFile(inputFile);
                Huffman huffman = new Huffman(inputText);
                writeFile(outputFile, getResultText(huffman));
            } else {
                decodeCompressedFile(inputFile, outputFile);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}