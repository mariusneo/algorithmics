package topcoder.srm.div2.srm145;

/**
 * Created by marius on 19.01.15.
 */
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.regex.*;
import java.text.*;
import java.math.*;
import java.util.stream.IntStream;


public class ImageDithering
{
    private ImageDithering id;

    public int count(String dithered, String[] screen)
    {
        Set<Character> uniqueDitheredChars = new HashSet<>();
        for (char c : dithered.toCharArray()){
            if (!uniqueDitheredChars.contains(c)){
                uniqueDitheredChars.add(c);
            }
        }

        int count = 0;
        for (String line : screen){
            for (Character c : line.toCharArray()){
                if (uniqueDitheredChars.contains(c)){
                    count++;
                }
            }
        }

        return count;
    }

    @Before
    public void setup(){
        id = new ImageDithering();
    }

    @Test
    public void test0(){
        String dithered = "BW";
        String[] screen = {"AAAAAAAA",
                "ABWBWBWA",
                "AWBWBWBA",
                "ABWBWBWA",
                "AWBWBWBA",
                "AAAAAAAA"};
        int count = id.count(dithered, screen);

        Assert.assertEquals(24, count);
    }
}