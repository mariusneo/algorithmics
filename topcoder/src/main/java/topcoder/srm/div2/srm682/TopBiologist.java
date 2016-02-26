package topcoder.srm.div2.srm682;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TopBiologist {
    public String findShortestNewSequence(String sequence) {

        char[] dnaSymbols = new char[]{'A', 'C', 'G', 'T'};


        String newSequence = null;
        Queue<String> queue = new LinkedList<>();
        for (char c : dnaSymbols) {
            queue.add(Character.toString(c));
        }

        while (!queue.isEmpty()) {
            newSequence = queue.poll();
            System.out.println(newSequence);

            if (!sequence.contains(newSequence)) {
                break;
            } else {
                for (char c : dnaSymbols) {
                    queue.add(newSequence + c);
                }
            }
        }

        return newSequence;
    }

    @Test
    public void test1() {
        TopBiologist tb = new TopBiologist();
        assertThat("AA", is(equalTo(tb.findShortestNewSequence("AGGTCTA"))));
    }
}
