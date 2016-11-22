package leetcode.twitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

public class Twitter {

    private static final Comparator<Tweet> TWEET_COMPARATOR = new Comparator<Tweet>() {
        @Override
        public int compare(Tweet o1, Tweet o2) {
            return Long.compare(o1.sequenceNumber, o2.sequenceNumber);
        }
    };
    private final Map<Integer, ConcurrentLinkedQueue<Tweet>> user2Tweets;
    private final Map<Integer, ConcurrentSkipListSet<Tweet>> user2NewsFeeds;
    private final Map<Integer, Set<Integer>> user2Followers;
    private AtomicLong sequenceNumber;

    /**
     * Initialize your data structure here.
     */
    public Twitter() {
        sequenceNumber = new AtomicLong();
        user2Tweets = new HashMap<>();
        user2NewsFeeds = new HashMap<>();
        user2Followers = new HashMap<>();
    }

    public static void main(String[] args) {
        Twitter obj = new Twitter();
        obj.postTweet(1, 1);
        List<Integer> tweets01 = obj.getNewsFeed(1);
        System.out.println(tweets01);
        obj.follow(2, 1);
        List<Integer> tweets02 = obj.getNewsFeed(2);
        System.out.println(tweets02);
        obj.unfollow(2, 1);
        List<Integer> tweets03 = obj.getNewsFeed(2);
        System.out.println(tweets03);

        obj = new Twitter();
        obj.postTweet(1, 5);
        obj.postTweet(1, 3);
        List<Integer> tweets1 = obj.getNewsFeed(1);
        System.out.println(tweets1);
        obj.follow(2, 1);
        List<Integer> tweets2 = obj.getNewsFeed(2);
        System.out.println(tweets2);

    }

    /**
     * Compose a new tweet.
     */
    public void postTweet(int userId, int tweetId) {
        ConcurrentLinkedQueue<Tweet> tweets;
        synchronized (user2Tweets) {
            tweets = user2Tweets.get(userId);
            if (tweets == null) {
                tweets = new ConcurrentLinkedQueue<>();
                user2Tweets.put(userId, tweets);
            }
        }

        Tweet tweet = new Tweet(tweetId, sequenceNumber.incrementAndGet());
        synchronized (tweets) {
            tweets.add(tweet);
        }

        postToNewsFeed(userId, tweet);

        Set<Integer> followers = user2Followers.get(userId);
        if (followers != null && !followers.isEmpty()) {
            for (int followerId : followers) {
                postToNewsFeed(followerId, tweet);
            }

        }
    }

    private void postToNewsFeed(int userId, Tweet tweet) {
        ConcurrentSkipListSet<Tweet> newsFeed;
        synchronized (user2NewsFeeds) {
            newsFeed = user2NewsFeeds.get(userId);
            if (newsFeed == null) {
                newsFeed = new ConcurrentSkipListSet<>(TWEET_COMPARATOR);
            }
            user2NewsFeeds.put(userId, newsFeed);
        }

        newsFeed.add(tweet);
    }

    /**
     * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by
     * users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
     */
    public List<Integer> getNewsFeed(int userId) {
        ConcurrentSkipListSet<Tweet> newsFeed = user2NewsFeeds.get(userId);
        if (newsFeed == null) {
            return Collections.emptyList();
        }

        System.out.println("news feed size " + newsFeed.size());
        Iterator<Tweet> it = newsFeed.descendingIterator();
        int count = 0;
        List<Integer> tweets = new ArrayList<>();
        do {
            if (!it.hasNext()) break;
            tweets.add(it.next().id);
            count++;
        } while (count < 10);

        return tweets;
    }

    /**
     * Follower follows a followee. If the operation is invalid, it should be a no-op.
     */
    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId) return;

        Set<Integer> followers;
        synchronized (user2Followers) {
            followers = user2Followers.get(followeeId);
            if (followers == null) {
                followers = new HashSet<>();
                user2Followers.put(followeeId, followers);
            }

        }

        synchronized (followers) {
            followers.add(followerId);
        }

        ConcurrentLinkedQueue<Tweet> tweets = user2Tweets.get(followeeId);
        if (tweets == null) return;
        tweets.forEach(tweet -> postToNewsFeed(followerId, tweet));
    }

    /**
     * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
     */
    public void unfollow(int followerId, int followeeId) {
        if (followerId == followeeId) return;

        Set<Integer> followers = user2Followers.get(followeeId);
        if (followers == null || followers.isEmpty()) return;

        boolean removed;
        synchronized (followers) {
            removed = followers.remove(followerId);
        }

        if (!removed) return;

        ConcurrentSkipListSet<Tweet> newsFeed = user2NewsFeeds.get(followerId);
        if (newsFeed == null) return;
        ConcurrentLinkedQueue<Tweet> tweets = user2Tweets.get(followeeId);
        if (tweets == null) return;
        tweets.forEach(tweet -> newsFeed.remove(tweet));
    }

    private static class Tweet {
        int id;
        long sequenceNumber;

        public Tweet(int id, long sequenceNumber) {
            this.id = id;
            this.sequenceNumber = sequenceNumber;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tweet tweet = (Tweet) o;
            return id == tweet.id &&
                    sequenceNumber == tweet.sequenceNumber;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, sequenceNumber);
        }
    }

}