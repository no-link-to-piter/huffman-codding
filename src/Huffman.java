import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Huffman {
    private final String inputString;
    private String encodedString;
    public HashMap<Character, Integer> hashMapCharFrequency;
    public HashMap<Character, String> hashMapCharCode;
    public HashMap<String, Character> hashMapCodeChar;
    private final PriorityQueue<Node> priorityQueue;
    private Node root;

    public Huffman(String inputString){
        this.inputString = inputString;
        this.hashMapCharFrequency = new HashMap<Character, Integer>();
        this.hashMapCharCode = new HashMap<Character, String>();
        this.hashMapCodeChar = new HashMap<String, Character>();
        this.priorityQueue = new PriorityQueue<Node>(1, new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                if (n1.getFrequency() < n2.getFrequency()) {
                    return -1;
                } else if (n1.getFrequency() > n2.getFrequency()) {
                    return 1;
                }
                return 0;
            }
        });

        this.handleFrequencies();
        this.createDefaultHuffmanTree();
        this.createHuffmanTree();
        this.buildCodeRecursion(this.root, "");
    }

    private void buildCodeRecursion(Node node, String code){
        if (node != null){
            Node leftNode = node.getLeft();
            Node rightNode = node.getRight();
            if (leftNode != null || rightNode != null) {
                this.buildCodeRecursion(leftNode, code + '0');
                this.buildCodeRecursion(rightNode, code + '1');
            } else {
                this.hashMapCharCode.put(node.getCh(), code);
                this.hashMapCodeChar.put(code, node.getCh());
            }
        }
    }

    private void createHuffmanTree(){
        while (!this.priorityQueue.isEmpty()) {
            Node leftNode = this.priorityQueue.poll();
            Node rightNode = this.priorityQueue.peek() != null ? this.priorityQueue.poll() : null;
            int rightNodeFrequency = rightNode != null ? rightNode.getFrequency() : 0;
            this.root = new Node('\0', leftNode.getFrequency() + rightNodeFrequency, leftNode, rightNode);

            if (this.priorityQueue.peek() != null){
                this.priorityQueue.offer(this.root);
            } else {
                break;
            }
        }
    }

    private void createDefaultHuffmanTree(){
        for (Map.Entry<Character, Integer> entry : this.hashMapCharFrequency.entrySet()){
            Character ch = entry.getKey();
            Integer frequency = entry.getValue();
            Node node = new Node(ch, frequency, null, null);
            this.priorityQueue.offer(node);
        }
    }

    private void handleFrequencies() {
        int frequency;
        char[] chars = this.inputString.toCharArray();
        for(char ch : chars){
            if (!this.hashMapCharFrequency.containsKey(ch)) {
                frequency = 1;
            } else {
                frequency = this.hashMapCharFrequency.get(ch) + 1;
            }
            this.hashMapCharFrequency.put(ch, frequency);
        }
    }

    public String encode(){
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = this.inputString.toCharArray();
        for(char ch : chars){
            stringBuilder.append(this.hashMapCharCode.get(ch));
        }
        this.encodedString = stringBuilder.toString();
        return stringBuilder.toString();
    }

    public static String decode(HashMap<String, Character> _hashMapCodeChar, String _encodedString){
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder tmp = new StringBuilder();
        char[] chars = _encodedString.toCharArray();
        HashMap<String, Character> _hash = _hashMapCodeChar;
        for(char ch : chars){
            tmp.append(ch);
            if (_hash.containsKey(tmp.toString())) {
                stringBuilder.append(_hash.get(tmp.toString()));
                tmp.setLength(0);
            }
        }
        return stringBuilder.toString();
    }

}