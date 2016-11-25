package algorithmsandme;

import java.util.Arrays;

/**
 * Code taken from
 * https://www.cs.cmu.edu/~adamchik/15-121/lectures/Binary%20Heaps/code/Heap.java
 *
 * @param <T>
 */
public class Heap<T extends Comparable<T>> {
    private static final int CAPACITY = 2;

    private int size;            // Number of elements in heap
    private T[] heap;     // The heap array

    public Heap() {
        size = 0;
        heap = (T[]) new Comparable[CAPACITY];
    }

    /**
     * Construct the binary heap given an array of items.
     */
    public Heap(T[] array) {
        size = array.length;
        heap = (T[]) new Comparable[array.length + 1];

        System.arraycopy(array, 0, heap, 1, array.length);//we do not use 0 index

        buildHeap();
    }

    public static void main(String[] args) {
        Heap<String> h = new Heap<>();

        h.insert("p");
        h.insert("r");
        h.insert("i");
        h.insert("o");
        h.insert("a");
        h.insert("z");
        h.insert("c");
        h.insert("b");
        h.insert("x");
        System.out.println(h);
        h.deleteMin();
        System.out.println(h);


        Heap<Integer> tmp = new Heap<Integer>();
        Integer[] a = {4, 7, 7, 7, 5, 0, 2, 3, 5, 1};
        tmp.heapSort(a);
        System.out.println(Arrays.toString(a));
    }

    /**
     * runs at O(size)
     */
    private void buildHeap() {
        for (int k = size / 2; k > 0; k--) {
            percolatingDown(k);
        }
    }

    private void percolatingDown(int k) {
        T tmp = heap[k];
        int child;

        for (; 2 * k <= size; k = child) {
            child = 2 * k;

            if (child != size &&
                    heap[child].compareTo(heap[child + 1]) > 0) child++;

            if (tmp.compareTo(heap[child]) > 0) heap[k] = heap[child];
            else
                break;
        }
        heap[k] = tmp;
    }

    /**
     * Sorts a given array of items.
     */
    public void heapSort(T[] array) {
        size = array.length;
        heap = (T[]) new Comparable[size + 1];
        System.arraycopy(array, 0, heap, 1, size);
        buildHeap();

        for (int i = size; i > 0; i--) {
            T tmp = heap[i]; //move top item to the end of the heap array
            heap[i] = heap[1];
            heap[1] = tmp;
            size--;
            percolatingDown(1);
        }
        for (int k = 0; k < heap.length - 1; k++)
            array[k] = heap[heap.length - 1 - k];
    }

    /**
     * Deletes the top item
     */
    public T deleteMin() throws RuntimeException {
        if (size == 0) throw new RuntimeException();
        T min = heap[1];
        heap[1] = heap[size--];
        percolatingDown(1);
        return min;
    }

    /**
     * Inserts a new item
     */
    public void insert(T x) {
        if (size == heap.length - 1) doubleSize();

        //Insert a new item to the end of the array
        int pos = ++size;

        //Percolate up
        for (; pos > 1 && x.compareTo(heap[pos / 2]) < 0; pos = pos / 2)
            heap[pos] = heap[pos / 2];

        heap[pos] = x;
    }

    private void doubleSize() {
        T[] old = heap;
        heap = (T[]) new Comparable[heap.length * 2];
        System.arraycopy(old, 1, heap, 1, size);
    }

    public String toString() {
        String out = "";
        for (int k = 1; k <= size; k++) out += heap[k] + " ";
        return out;
    }
}
