package algorithmsandme;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a file with millions of URLs. Some URLs repeating , some unique. Find the first unique URL.
 */
public class FindUniqueUrl {

    public String findFirstUniqueUrl(String[] urls) {
        List<String> uniqueUrlsList = new List<>();
        TrieNode<ListNode<String>> root = new TrieNode<>();
        for (String url : urls) {
            insert(root, url, uniqueUrlsList);
        }

        if (uniqueUrlsList.head != null) {
            return uniqueUrlsList.head.value;
        }

        return null;
    }

    private boolean insert(TrieNode<ListNode<String>> root, String url, List<String> uniqueUrlList) {
        TrieNode<ListNode<String>> current = root;
        for (int i = 0; i < url.length(); i++) {
            char c = url.charAt(i);


            if (current.children.containsKey(c)) {
                current = current.children.get(c);
            } else {
                TrieNode<ListNode<String>> tmp = new TrieNode<>(c);
                current.children.put(c, tmp);
                current = tmp;
            }
        }

        if (current.value == null) {
            current.value = uniqueUrlList.insert(url);
            return true;
        } else {
            uniqueUrlList.remove(current.value);
            return false;
        }
    }


    @Test
    public void testAccuracy() {
        TrieNode root = new TrieNode();

        String[] urls = {"http://www.google.com", "http://www.yahoo.com",
                "http://news.yahoo.com", "http://www.yahoo.com", "http://www.google.com", "http://www.republica.ro"};

        String url = findFirstUniqueUrl(urls);
        System.out.println(url);
    }


    class TrieNode<V> {
        Map<Character, TrieNode<V>> children;
        V value;

        public TrieNode() {
            children = new HashMap<>();
        }

        public TrieNode(char value) {

            children = new HashMap<>();
        }

    }

    class List<T> {
        ListNode<T> head;
        ListNode<T> tail;

        public void remove(ListNode<T> listNode) {
            if (listNode.previous == null) {
                head = listNode.next;
                if (listNode.next == null) {
                    tail = null;
                }
            } else if (listNode.next == null) {
                tail = listNode.previous;
            } else {
                listNode.previous.next = listNode.next;
                listNode.next.previous = listNode.previous;
            }
        }

        public ListNode<T> insert(T value) {
            ListNode<T> ln = new ListNode<>(value);
            if (head == null) {
                head = ln;
                tail = ln;
            } else {
                tail.next = ln;
                ln.previous = tail;
                tail = ln;
            }

            return ln;
        }
    }

    class ListNode<T> {
        T value;
        ListNode<T> next;
        ListNode<T> previous;

        public ListNode(T value) {
            this.value = value;
        }
    }
}
