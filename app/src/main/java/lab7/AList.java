/*
 * Author: Landon Weber
 * Date: 5/25/2025
 * Purpose: Arraylist that allocates new memory
 */
package lab7;
import java.lang.reflect.Array;
import java.util.NoSuchElementException;

/** An ArrayList-like dynamic array class that allocates
* new memory when needed */
public class AList<T> {

  protected int size; // number of elements in the AList
  protected T[] a; // the backing array storage

  public int size() {
    return size;
  }

  protected int getCap() {
    return a.length;
  }

  /** Creates an AList with a default capacity of 8 */
  public AList() {
    a = createArray(8);
    size = 0;
  }

  /** Creates an AList with the given capacity */
  public AList(int capacity) {
    // TODO 1
    a = createArray(capacity);
    size = 0;
  }

  /* Grows a to double its current capacity if newSize exceeds a's capacity. Does
  * nothing if newSize <= a.length.  Grow the array by allocating a new array
  * and copying the old array's contents into the new one. This does *not*
  * change the AList's size. */
  protected void growIfNeeded(int newSize) {

    if (newSize > getCap()){
      //make temp array a clone
      T[] b = createArray(getCap());
      b = a.clone();

      //double capacity
      int newCapacity = getCap()*2;
      while (newCapacity < newSize){
        newCapacity *=2;
      }

      a = createArray(newCapacity);

      //copies b to a
      for (int i = 0; i<size();i++){
        a[i] = b[i];
      }
    }

  }


  /** Resizes the AList.
  *  this *does* modify the size, and may modify the capacity if newsize
  *  exceeds capacity. */
  public void resize(int newsize) {
    // TODO 2b
    growIfNeeded(newsize);
    size = newsize;
  }

  /** Gets element i from AList.
  * @throws ArrayIndexOutOfBoundsException if 0 <= i < size does not hold */
  public T get(int i) {
    // TODO 3a
    if (i < 0 || i > size()-1){
      throw new ArrayIndexOutOfBoundsException();
    }
    return a[i];
  }




  /** Sets the ith element of the list to value.
  * @throws ArrayIndexOutOfBoundsException if 0 <= i < size does not hold */
  public void put(int i, T value) {
    // TODO 3b
    if (i<0 || i>=size()){
      throw new ArrayIndexOutOfBoundsException();
    }
    if (a[i] == null){
      size++;
    }
    a[i] = value;
  }




  /** Appends value at the end of the AList, increasing size by 1.
  * Grows the array if needed to fit the appended value */
  public void append(T value) {
    // TODO 4a
    growIfNeeded(size+1);
    a[size] = value;
    size++;
  }



  /** Removes and returns the value at the end of the AList.
  *  this *does* modify size and cannot modify capacity.
  *  @throws NoSuchElementException if size == 0*/
  public T pop() {
    // TODO 4b
    if (size==0){
      throw new NoSuchElementException();
    }

    T returnVal = a[size-1];
    a[size-1] = null;
    size--;
    return returnVal;
  }

  /*  Create and return a T[] of size n.
  *  This is necessary because generics and arrays don't play well together.*/
  @SuppressWarnings("unchecked")
  protected T[] createArray(int size) {
    return (T[]) new Object[size];
  }

}
