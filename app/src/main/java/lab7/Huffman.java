package lab7;
import heap.Heap;

public class Huffman {

    private static Heap heap = new Heap();
    private static HashTable hash = new HashTable();
    //elementList exists to provide a way to get values during buildTree
    private static AList<Character> elementList = new AList<Character>(10);
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
    

    public static hNode buildTree(HashTable Hash){
        //forms a heap with the priorities and hNodes
        for (int i = 0; i < (elementCount);i++){
                int priority = (Integer) hash.get(elementList.get(i));
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
                newNode = new hNode(addedPri, firstNode,secondNode);
            } else {
                newNode = new hNode(addedPri, secondNode, firstNode);
            }
            firstNode.parent = newNode;
            secondNode.parent = newNode;
            heap.add(newNode, addedPri);
        }
        return (hNode) heap.poll();
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

    public static class hNode{

        public char value;
        public int priority;
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