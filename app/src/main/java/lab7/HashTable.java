/*
 * Author: Landon Weber
 * Date: 5/25/2025
 * Purpose: Hash Table
 */

package lab7;

/** A hash table modeled after java.util.Map. It uses chaining for collision
 * resolution and grows its underlying storage by a factor of 2 when the load
 * factor exceeds 0.8. */
public class HashTable<K,V> {

    protected Pair[] buckets; // array of list nodes that store K,V pairs
    protected int size; // how many items currently in the made

    /** class Pair stores a key-value pair and a next pointer for chaining
     * multiple values together in the same bucket, linked-list style*/
    public class Pair {
        protected K key;
        protected V value;
        protected Pair next;

        /** constructor: sets key and value */
        public Pair(K k, V v) {
            key = k;
            value = v;
            next = null;
        }

        /** constructor: sets key, value, and next */
        public Pair(K k, V v, Pair nxt) {
            key = k;
            value = v;
            next = nxt;
        }

        /** returns (k, v) String representation of the pair */
        public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }

    /** constructor: initialize with default capacity 17 */
    public HashTable() {
        this(17);
    }

    /** constructor: initialize the given capacity */
    public HashTable(int capacity) {
        buckets = createBucketArray(capacity);
    }

    /** Return the size of the map (the number of key-value mappings in the
     * table) */
    public int getSize() {
        return size;
    }

    /** Return the current capacity of the table (the size of the buckets
     * array) */
    public int getCapacity() {
        return buckets.length;
    }

    /** Return the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     * Runtime: average case O(1); worst case O(size) */
    public V get(K key) {
        // TODO 2.1 - do this together with put.
        int keyVal = Math.abs(key.hashCode());
        int numBuckets = getCapacity();
        int bucketSpot = keyVal % numBuckets;
        V returnVal = null;
        
        if (buckets[bucketSpot]!=null){
            Pair traverse = buckets[bucketSpot];
            //search for matching key or end of sequence
            while (traverse.key != key && traverse.next!=null){
                traverse = traverse.next;
            }
            //return val if equal to key
            if (traverse.key == key){
                returnVal = traverse.value;
            }
        }   
        return returnVal;
    }



    /** Associate the specified value with the specified key in this map. If
     * the map previously contained a mapping for the key, the old value is
     * replaced. Return the previous value associated with key, or null if
     * there was no mapping for key. If the load factor exceeds 0.8 after this
     * insertion, grow the array by a factor of two and rehash.
     * Precondition: val is not null.
     * Runtime: average case O(1); worst case O(size + a.length)*/
    public V put(K key, V val) {
        // TODO 2.2
        //   do this together with get. For now, don't worry about growing the
        //   array and rehashing.
        //   Tips:
        //     - Use the key's hashCode method to find which bucket it belongs in.
        //     - It's possible for hashCode to return a negative integer.


        int keyVal = Math.abs(key.hashCode());
        int numBuckets = getCapacity();
        int bucketSpot = keyVal % numBuckets;
        Pair thisPair = new Pair(key, val);
        V returnVal = null;

        //place at value if no value
        if (buckets[bucketSpot] == null){
            buckets[bucketSpot] = thisPair;
            size++;
        //replace if key matches
        } else if (buckets[bucketSpot].key == key){
            //if is followed by a pair, attach to thisPair
            if (buckets[bucketSpot].next!=null){
                    thisPair.next = buckets[bucketSpot].next;
                }
            //replaces val
            returnVal = buckets[bucketSpot].value;
            buckets[bucketSpot] = thisPair;

        //otherwise traverse to open spot or identicle key and place
        } else {
            Pair traverse = buckets[bucketSpot];
            //move to insertion spot
            while (traverse.next != null && traverse.next.key!=key){
                traverse = traverse.next;
            }
            //Checks for identicle key
            if (traverse.next != null && traverse.next.key == key){
                //if is followed by a pair, attach to thisPair
                if (traverse.next.next!=null){
                    thisPair.next = traverse.next.next;
                }
                returnVal = traverse.next.value;
                traverse.next = thisPair;
            //otherwise assign to traverse.next
            } else {
                traverse.next = thisPair;
                size++;
            }
        }

        // TODO 2.5 - modify this method to grow and rehash if the load factor
        //            exceeds 0.8.
        growIfNeeded();
        return returnVal;
    }

    /** Return true if this map contains a mapping for the specified key.
     *  Runtime: average case O(1); worst case O(size) */
    public boolean containsKey(K key) {
        // TODO 2.3
        if (get(key) == null){
            return false;
        }
        return true;
    }

    /** Remove the mapping for the specified key from this map if present.
     *  Return the previous value associated with key, or null if there was no
     *  mapping for key.
     *  Runtime: average case O(1); worst case O(size)*/
    public V remove(K key) {
        // TODO 2.4

        int keyVal = Math.abs(key.hashCode());
        int numBuckets = getCapacity();
        int bucketSpot = keyVal % numBuckets;
        V returnVal = null;

        //if pair with key exists
        if (containsKey(key)){
        
            //moves through until finds matching key
            int counter = 0;
            Pair copyFirst = new Pair(buckets[bucketSpot].key, buckets[bucketSpot].value, buckets[bucketSpot].next);
            while(buckets[bucketSpot].key != key){
                counter++;
                buckets[bucketSpot] = buckets[bucketSpot].next;
            }
            //if more pairs exist afterwards, connect to them
            if (buckets[bucketSpot].next!=null){
                returnVal = buckets[bucketSpot].value;
                buckets[bucketSpot] = buckets[bucketSpot].next;
                size--;
            //otherwise, just set as null
            } else {
                returnVal = buckets[bucketSpot].value;
                buckets[bucketSpot] = null;
                size--;
            }
            //if first value wasn't removed, attach first val
            if (counter != 0){
                buckets[bucketSpot] = copyFirst;
            }
        }
        return returnVal;
    }


    // suggested helper method:
    /* check the load factor; if it exceeds 0.8, double the capacity 
     * and rehash values from the old array to the new array */
    private void growIfNeeded() {
        double loadFactor = ( (double)getSize() / (double) getCapacity() );
        if (loadFactor > 0.8){
            //creates a new larger array
            Pair[] newHash = createBucketArray(getCapacity()*2);
            Pair[] bucketsCopy = buckets;
            int capacity = getCapacity();
            buckets = newHash;
            size = 0;

            for (int i=0;i<capacity;i++){
                //for each bucket, inserts into newHash
                Pair traverse = new Pair(null,null,bucketsCopy[i]);
                while (traverse.next!=null){
                    put(traverse.next.key, traverse.next.value);
                    //move to next pair
                    traverse = traverse.next; 
            }
        }
        }
    }

    /* useful method for debugging - prints a representation of the current
     * state of the hash table by traversing each bucket and printing the
     * key-value pairs in linked-list representation */
    protected void dump() {
        System.out.println("Table size: " + getSize() + " capacity: " +
                getCapacity());
        for (int i = 0; i < buckets.length; i++) {
            System.out.print(i + ": --");
            Pair node = buckets[i];
            while (node != null) {
                System.out.print(">" + node + "--");
                node = node.next;

            }
            System.out.println("|");
        }
    }

    /*  Create and return a bucket array with the specified size, initializing
     *  each element of the bucket array to be an empty LinkedList of Pairs.
     *  The casting and warning suppression is necessary because generics and
     *  arrays don't play well together.*/
    @SuppressWarnings("unchecked")
    protected Pair[] createBucketArray(int size) {
        return (Pair[]) new HashTable<?,?>.Pair[size];
    }
}
