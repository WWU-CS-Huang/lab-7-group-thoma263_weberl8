package lab7;
import heap.Heap;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class Huffman {

    private static Heap heap = new Heap();
    private static HashTable hash = new HashTable();
    //elementList exists to provide a way to get values during buildTree
    private static AList<Character> elementList = new AList<Character>(10);
    //hashtable that contains all letters and their codes
    private static HashTable codes = new HashTable();
    private static int elementCount = 0;

    public static void countFrequencies(String string){
        for (int i = 0; i < string.length(); i++){
            //TODO: I think we need to tell it to skip spaces (' ') as well
            if (hash.containsKey(string.charAt(i))==false){
                hash.put(string.charAt(i),1);
                //list of elements used
                elementList.append(string.charAt(i));
                elementCount++;
            } else {
                //replace key and ++ its value
                int newCount = (int)hash.get(string.charAt(i))+1;
                hash.put(string.charAt(i), newCount);
            }
        }
    }

    public static hNode buildTree(HashTable hash){
        //forms a heap with the priorities and hNodes
        for (int i = 0; i < (elementCount); i++){
                int priority = (int) hash.get(elementList.get(i));
                hNode newNode = new hNode(elementList.get(i), priority);
                heap.add(newNode, priority);
        }

        //loop that forms the huffman tree while >1 element in the heap
        while (heap.size() > 1){
            hNode firstNode = (hNode) heap.poll();
            hNode secondNode = (hNode) heap.poll();
            int addedPri = firstNode.priority + secondNode.priority;
        
            //assigns children to newNode based on priority
            hNode newNode;
            if (firstNode.priority < secondNode.priority){
                newNode = new hNode(addedPri, firstNode, secondNode);
            } else {
                newNode = new hNode(addedPri, secondNode, firstNode);
            }
            firstNode.parent = newNode;
            secondNode.parent = newNode;
            heap.add(newNode, addedPri);
        }
        return (hNode) heap.poll();
    } 

    //Goes through a coded string, if the value is 0 it continues down
    //the left subtree and if the value is 1 it goes down the right subtree.
    //Once it gets to a leaf node, it adds that letter to the decoded string.
    public static String decode(hNode rootNode, String codedString){
        StringBuilder decodedString = new StringBuilder();
        hNode curr = rootNode;

        for (int i = 0; i < codedString.length(); i++){
            if (codedString.charAt(i) == '0'){
                curr = curr.leftChild;
            } else if (codedString.charAt(i) == '1'){
                curr = curr.rightChild;
            }
            if (curr.leftChild == null && curr.rightChild ==null){
                decodedString.append(curr.value);
                curr = rootNode;
            }
        }
        return decodedString.toString();
    }

    //Traverses through the tree, building codes along the way. once it
    //finds a leaf node, it puts that nodes value along with the code
    //to get there into a hashmap
    public static void traversal(hNode n, String codeBuild){
        if(n.leftChild == null && n.rightChild ==null){
            n.code = codeBuild;
            codes.put(n.value, n.code);
        } else {
            traversal(n.leftChild, codeBuild + "0");
            traversal(n.rightChild, codeBuild + "1");
        }
    }

    //Precondition: traversal has been run
    //Goes through the input string, then gets each letter's code from
    //the hashtable built by traversal, putting the codes into a new string
    public static String encode(String inputString){
        StringBuilder encodedString = new StringBuilder();

        for (int i = 0; i < inputString.length(); i++){
            encodedString.append(codes.get(inputString.charAt(i)));
        }
        return encodedString.toString();
    }

    public static void main(String[] args) {

        //Takes the file name argument and makes a file object and makes a scanner
        String fileName = args[0];
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        //Error catching if the file is not found
        //try {
        //    scanner = new Scanner(file);
        //} catch (FileNotFoundException e){
        //    e.printStackTrace();
        //    return;
        //}

        while (scanner.hasNext()){
            String input = scanner.nextLine;
        }

        System.out.println(input);

        countFrequencies(input);
        hNode huffmanTree = buildTree(hash);
        traversal(huffmanTree, "");
        
        String encoded = encode(input);
        String decoded = decode(huffmanTree, encoded);
        double compRatio = (encoded.length())/(input.length())/0.8;
        String correctness = "";
        if (input.equals(decoded) == true){
            correctness = "true";
        } else {
            correctness = "false";
        }

        if (input.length() < 100){
            System.out.println("Input string: " + input);
            System.out.println("Encoded string: " + encoded);
            System.out.println("Decoded string: " + decoded);
            System.out.println("Decoded equals input: " + correctness);
            System.out.println("Compression ratio: " + compRatio);
        } else {
            System.out.println("Decoded equals input: " + correctness);
            System.out.println("Compression ratio: " + compRatio) ;
        }

        //String string = "aaabbcccddddex";
        //countFrequencies(string);
        //hash.dump();
        //hNode tree = buildTree(hash);
        //traversal(tree, "");
        //codes.dump();
        //String encoded = encode(tree, string);
        //String decoded = decode(tree, encoded);
        //System.out.println(encoded);
        //System.out.println(decoded);
    }

    public static class hNode{

        public char value;
        public int priority;
        public String code;
        public hNode parent;
        public hNode leftChild;
        public hNode rightChild;

        //initialize without parents/children
        public hNode(char v, int pri){
            value = v;
            priority = pri;
        }

        //initialize for parent nodes
        public hNode(int pri, hNode left, hNode right){
            priority = pri;
            leftChild = left;
            rightChild = right;
        }

        //initialize with all values
        public hNode(char v, int pri, hNode p, hNode left, hNode right){
            value = v;
            priority = pri;
            parent = p;
            leftChild = left;
            rightChild = right;
        }
    
    }
}