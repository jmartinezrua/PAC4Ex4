package edu.uoc.pac4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Analyzes the sentiment of a given list of texts.
 */
public class SentimentAnalyzer {

    private static final Map<String, String> SENTIMENT_MAP = Map.of(
            "joy", "positive",
            "sadness", "negative",
            "love", "positive",
            "hate", "negative",
            "fear", "negative",
            "hope", "positive",
            "anger", "negative",
            "peace", "positive");

    /**
     * Determines the overall sentiment based on the counts of positive and negative sentiments.
     *
     * @param sentimentCounters a map containing the counts of positive and negative sentiments
     * @return the overall sentiment ("positive", "negative", or "neutral")
     */
    public String determineSentiment(Map<String, Integer> sentimentCounters) {
        int positiveCount = sentimentCounters.getOrDefault("positive", 0);
        int negativeCount = sentimentCounters.getOrDefault("negative", 0);
        if (positiveCount > negativeCount) {
            return "positive";
        } else if (negativeCount > positiveCount) {
            return "negative";
        } else {
            return "neutral";
        }
    }

    /**
     * Counts the sentiment words in a list of texts.
     *
     * @param texts the list of texts to analyze
     * @return a map containing the counts of positive and negative sentiment words
     */
    public Map<String, Integer> countSentimentWords(List<String> texts) {
        Map<String, Integer> sentimentCounters = new HashMap<>();
        for (String text : texts) {
            String[] words = text.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
            for (String word : words) {
                String sentiment = SENTIMENT_MAP.get(word);
                if (sentiment != null) {
                    sentimentCounters.put(sentiment, sentimentCounters.getOrDefault(sentiment, 0) + 1);
                }
            }
        }
        return sentimentCounters;
    }

    /**
     * Analyzes the sentiment of a list of texts and determines the overall sentiment.
     *
     * @param texts the list of texts to analyze
     * @return the overall sentiment ("positive", "negative", or "neutral")
     */
    public String analyzeTexts(List<String> texts) {
        Map<String, Integer> sentimentCounters = countSentimentWords(texts);
        return determineSentiment(sentimentCounters);
    }
}
