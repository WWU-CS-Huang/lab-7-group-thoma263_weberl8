package lab7;
import heap.Heap;

public class Huffman {

    private static Heap heap = new Heap();
    private static HashTable hash = new HashTable();


    public static void countFrequencies(String string){
        for (int i = 0; i < string.length(); i++){
            if (hash.containsKey(string.charAt(i))==false){
                hash.put(string.charAt(i),1);
            } else {
                int newCount = (int)hash.get(string.charAt(i))+1;
                hash.put(string.charAt(i), newCount);
            }

        }
        
    }

    
    public static hNode buildTree(HashTable Hash){
        for (int i = 0; i < hash.getSize();i++){
            if (hash[i] != null){
                heap.add(hash.get(i).key,hash.get(i).value);
            }
            
        }
    }
    

    public static int Decode(hNode rootNode, String codedString){
        return 1;
    }

    public static int Encode(hNode rootNode, String inputString){
        return 1;
    }


    public static void main(String[] args) {

        String string = "kasdjlf;j;asdf";
        countFrequencies(string);
        hash.dump();
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