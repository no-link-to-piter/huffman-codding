public class Node {
    private final int frequency;
    private final char ch;
    private final Node left;
    private final Node right;

    public Node(char ch, int frequency, Node left, Node right){
        this.frequency = frequency;
        this.ch = ch;
        this.left = left;
        this.right = right;
    }
    
    public Node getLeft() {
        return this.left;
    }

    public Node getRight() {
        return this.right;
    }

    public char getCh() {
        return ch;
    }

    public int getFrequency() {
        return frequency;
    }

}