package lab7;
import heap.Heap;

public class Huffman {

    private static Heap heap = new Heap();
    private static HashTable hash = new HashTable();
    //keyList currently initialized at an arbitrary value
    private static char[] keyList = new char[20];
    private static int keyCount = 0;


    public static void countFrequencies(String string){
        for (int i = 0; i < string.length(); i++){
            //TODO: I think we need to tell it to skip spaces (' ') as well
            if (hash.containsKey(string.charAt(i))==false){
                hash.put(string.charAt(i),1);
                //list of keys used
                keyList[keyCount] = string.charAt(i);
                keyCount++;
            } else {
                //replace key and ++ its value
                int newCount = (int)hash.get(string.charAt(i))+1;
                hash.put(string.charAt(i), newCount);
            }

        }
    }

    //TODO: replace void with root Node
    public static void buildTree(HashTable Hash){
        //forms a heap with the priorities
        for (int i = 0; i < (keyCount);i++){
                char key = keyList[i];
                int value = (Integer) hash.get(key);
                heap.add(key, value);
        }
        
        //TODO: take heap, form into huffman tree


    }
    

    public static int Decode(hNode rootNode, String codedString){
        return 1;
    }

    public static int Encode(hNode rootNode, String inputString){
        return 1;
    }

    public static void main(String[] args) {

        String string = "aaabbcccddddex";
        countFrequencies(string);
        hash.dump();
        buildTree(hash);
    }

    public class hNode{

        public String value;
        public int priority;
        public hNode parent;
        public hNode leftChild;
        public hNode rightChild;


        public hNode(String v, int pri, hNode p, hNode l, hNode r){

            value = v;
            priority = pri;
            parent = p;
            leftChild = l;
            rightChild = r;
            
        }
    
    }

    

}