package org.mg.catcoder.auctionbidding;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Bidding {
    public Bidd highestBid(int initialValue, Bidd[] bidds) {
        Bidd highestBid = null;
        int currentValue = initialValue;

        for (int i = 0; i < bidds.length; i++) {
            if (highestBid == null) {
                if (bidds[i].value >= initialValue) {
                    highestBid = bidds[i];
                }
            } else {

                if (bidds[i].value > highestBid.value && !bidds[i].bidder.equals(highestBid.bidder)) {
                    currentValue = highestBid.value + 1;
                    highestBid = bidds[i];
                } else if (bidds[i].value == highestBid.value) {
                    currentValue = bidds[i].value;
                } else if (bidds[i].value < highestBid.value && bidds[i].value > currentValue) {
                    currentValue = bidds[i].value + 1;
                }
            }
        }

        return new Bidd(highestBid.bidder, currentValue);
    }

    public List<Bidd> biddHistory(int initialValue, Bidd[] bidds) {
        Bidd highestBid = null;
        int currentValue = initialValue;
        List<Bidd> biddHistory = new LinkedList<>();
        biddHistory.add(new Bidd("-", initialValue));


        for (int i = 0; i < bidds.length; i++) {
            if (highestBid == null) {
                if (bidds[i].value >= initialValue) {
                    highestBid = bidds[i];
                    biddHistory.add(new Bidd(highestBid.bidder, initialValue));
                }
            } else {

                if (bidds[i].value > highestBid.value) {
                    if (!bidds[i].bidder.equals(highestBid.bidder)) {
                        currentValue = highestBid.value + 1;
                        biddHistory.add(new Bidd(bidds[i].bidder, currentValue));
                    }
                    highestBid = bidds[i];
                } else if (bidds[i].value == highestBid.value && !bidds[i].bidder.equals(highestBid.bidder)) {
                    currentValue = bidds[i].value;
                    biddHistory.add(new Bidd(highestBid.bidder, currentValue));
                } else if (bidds[i].value < highestBid.value && bidds[i].value >= currentValue) {
                    currentValue = bidds[i].value + 1;
                    biddHistory.add(new Bidd(highestBid.bidder, currentValue));
                }
            }
        }

        return biddHistory;
    }


    public List<Bidd> biddHistory(int initialValue, int buyNowPrice, Bidd[] bidds) {
        if (buyNowPrice == 0) return biddHistory(initialValue, bidds);

        Bidd highestBid = null;
        int currentValue = initialValue;
        List<Bidd> biddHistory = new LinkedList<>();
        biddHistory.add(new Bidd("-", initialValue));


        for (int i = 0; i < bidds.length; i++) {
            if (highestBid == null) {
                if (bidds[i].value >= initialValue) {
                    highestBid = bidds[i];
                    biddHistory.add(new Bidd(highestBid.bidder, initialValue));
                }
            } else {

                if (bidds[i].value > highestBid.value) {
                    if (!bidds[i].bidder.equals(highestBid.bidder)) {
                        currentValue = Math.min(highestBid.value + 1, buyNowPrice);
                        biddHistory.add(new Bidd(bidds[i].bidder, currentValue));
                    }
                    highestBid = bidds[i];
                } else if (bidds[i].value == highestBid.value && !bidds[i].bidder.equals(highestBid.bidder)) {
                    currentValue = Math.min(bidds[i].value, buyNowPrice);
                    biddHistory.add(new Bidd(highestBid.bidder, currentValue));
                } else if (bidds[i].value < highestBid.value && bidds[i].value >= currentValue) {
                    currentValue = Math.min(bidds[i].value + 1, buyNowPrice);
                    biddHistory.add(new Bidd(highestBid.bidder, currentValue));
                }
                if (currentValue == buyNowPrice) {
                    break;
                }
            }
        }

        return biddHistory;
    }


    @Test
    public void quizBiddHistoryWithBuyNowPrice() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(Bidding.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/auctionbidding/input-level4.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                if (st.hasMoreTokens()) {
                    int initialValue = Integer.parseInt(st.nextToken());
                    int buyNowPrice = Integer.parseInt(st.nextToken());
                    Bidd[] bidds = new Bidd[st.countTokens() / 2];
                    int i = 0;
                    while (st.hasMoreTokens()) {
                        String name = st.nextToken();
                        int value = Integer.parseInt(st.nextToken());
                        bidds[i++] = new Bidd(name, value);
                    }
                    Bidding bidding = new Bidding();
                    List<Bidd> biddHistory = bidding.biddHistory(initialValue, buyNowPrice, bidds);
                    StringBuilder sb = new StringBuilder();
                    for (Bidd bidd : biddHistory) {
                        if (sb.length() > 0) {
                            sb.append(",");
                        }
                        sb.append(bidd.bidder).append(",").append(bidd.value);
                    }
                    System.out.println(sb.toString());
                }
            }


        } finally {
            if (br != null) {
                br.close();
            }
        }
    }

    @Test
    public void quizBiddHistory() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(Bidding.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/auctionbidding/input-level3.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                if (st.hasMoreTokens()) {
                    int initialValue = Integer.parseInt(st.nextToken());
                    Bidd[] bidds = new Bidd[st.countTokens() / 2];
                    int i = 0;
                    while (st.hasMoreTokens()) {
                        String name = st.nextToken();
                        int value = Integer.parseInt(st.nextToken());
                        bidds[i++] = new Bidd(name, value);
                    }
                    Bidding bidding = new Bidding();
                    List<Bidd> biddHistory = bidding.biddHistory(initialValue, bidds);
                    StringBuilder sb = new StringBuilder();
                    for (Bidd bidd : biddHistory) {
                        if (sb.length() > 0) {
                            sb.append(",");
                        }
                        sb.append(bidd.bidder).append(",").append(bidd.value);
                    }
                    System.out.println(sb.toString());
                }
            }


        } finally {
            if (br != null) {
                br.close();
            }
        }
    }


    @Test
    public void quizScore() throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(Bidding.class.getClassLoader()
                    .getResourceAsStream("org/mg/catcoder/auctionbidding/input-level2.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ",");
                if (st.hasMoreTokens()) {
                    int initialValue = Integer.parseInt(st.nextToken());
                    Bidd[] bidds = new Bidd[st.countTokens() / 2];
                    int i = 0;
                    while (st.hasMoreTokens()) {
                        String name = st.nextToken();
                        int value = Integer.parseInt(st.nextToken());
                        bidds[i++] = new Bidd(name, value);
                    }

                    Bidding bidding = new Bidding();
                    Bidd highestBidd = bidding.highestBid(initialValue, bidds);
                    if (highestBidd == null) {
                        System.out.println("NO VALID BIDD");
                    } else {
                        System.out.println(highestBidd.bidder + "," + highestBidd.value);
                    }
                }
            }


        } finally {
            if (br != null) {
                br.close();
            }
        }
    }


    private class Bidd {
        String bidder;
        int value;

        public Bidd(String bidder, int value) {
            this.bidder = bidder;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Bid{" +
                    "bidder='" + bidder + '\'' +
                    ", value=" + value +
                    '}';
        }
    }
}
