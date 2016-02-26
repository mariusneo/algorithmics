package topcoder.srm.div2.srm682;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DNASequence {


    private static boolean isDNASymbol(char c) {
        return (c == 'A' || c == 'C' || c == 'G' || c == 'T');
    }

    public int longestDNASequence(String sequence) {
        int maxDnaSeqLength = 0;

        int currentDnaSeqLength = 0;
        for (int i = 0; i < sequence.length(); i++) {
            char c = sequence.charAt(i);
            if (isDNASymbol(c)) {
                currentDnaSeqLength++;
            } else {
                if (currentDnaSeqLength > maxDnaSeqLength) {
                    maxDnaSeqLength = currentDnaSeqLength;
                }
                currentDnaSeqLength = 0;
            }
        }

        if (currentDnaSeqLength > maxDnaSeqLength) {
            maxDnaSeqLength = currentDnaSeqLength;
        }

        return maxDnaSeqLength;
    }

    @Test
    public void test1() {
        DNASequence dnaSequence = new DNASequence();
        assertThat(2, is(equalTo(dnaSequence.longestDNASequence("TOPBOATER"))));
    }

    @Test
    public void test4() {
        DNASequence dnaSequence = new DNASequence();
        assertThat(6, is(equalTo(dnaSequence.longestDNASequence("VVZWKCSIQEGANULDLZESHUYHUQGRKUMFCGTATGOHMLKBIRCA"))));
    }
}
