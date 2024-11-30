package edu.uoc.pac4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String analyzeTexts(List<String> texts) {
        Map<String, Integer> sentimentCounters = countSentimentWords(texts);
        return determineSentiment(sentimentCounters);
    }
}