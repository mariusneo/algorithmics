package algorithmsandme;

public class MyHeap<T extends Comparable<T>> {

    private static final int CAPACITY = 2;
    private T[] heap;
    private int size;


    public MyHeap() {
        size = 0;
        heap = (T[]) new Comparable[CAPACITY];
    }

    public MyHeap(T[] array) {
        size = array.length;
        heap = (T[]) new Comparable[array.length + 1];

        System.arraycopy(array, 0, heap, 1, size);
    }

    public static void main(String[] args) {
        MyHeap<String> h = new MyHeap<>();

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
        String min = h.deleteMin();
        System.out.println(min);
        System.out.println(h);
    }

    private void buildHeap() {
        int i = size / 2 + 1;
        for (; i >= 1; i--) {
            percolateDown(i);
        }
    }

    private void doubleSize() {
        T[] old = heap;
        heap = (T[]) new Comparable[heap.length * 2];
        System.arraycopy(old, 1, heap, 1, size);
    }

    public void insert(T x) {
        if (size == heap.length - 1) {
            doubleSize();
        }

        int pos = ++size;
        for (; pos > 1 && x.compareTo(heap[pos / 2]) < 0; pos = pos / 2) {
            heap[pos] = heap[pos / 2];
        }

        heap[pos] = x;
    }

    public T deleteMin() {
        if (size == 0) throw new IllegalStateException("heap is empty");
        T min = heap[1];
        heap[1] = heap[size--];
        percolateDown(1);
        return min;
    }

    private void percolateDown(int k) {
        T x = heap[k];
        int child;
        for (; k * 2 <= size; k = child) {
            child = k * 2;
            // choose the min value from the children of this node
            if (child != size && heap[child].compareTo(heap[child + 1]) > 0) child++;

            if (x.compareTo(heap[child]) < 0) break;
            else heap[k] = heap[child];
        }
        heap[k] = x;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= size; i++) {
            sb.append(heap[i]).append(" ");
        }

        return sb.toString();
    }
}
