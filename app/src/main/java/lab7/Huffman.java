package lab7;
import heap.Heap;

public class Huffman {

    Heap heap = new Heap();

    public void countFrequencies(String string){
        for (int i = 0; i < string.length(); i++){

            //if string[i] is not in heap, add new node with string[i] v with priority 1
            if (heap.contains(string.charAt(i))==false){
                heap.add(string.charAt(i), 1);
            //if string[i] is contained in heap, find string[i] node and ++ priority
            } else {
                heap.changePriority(string.charAt(i), heap.getPriority(string.charAt(i))+1);
            }

        }
        
    }

    public static hNode buildTree(Heap heap){
        return;
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