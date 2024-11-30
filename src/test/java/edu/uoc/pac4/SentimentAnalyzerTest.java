package edu.uoc.pac4;

import org.junit.jupiter.api.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SentimentAnalyzerTest {

    SentimentAnalyzer analyzer = new SentimentAnalyzer();

    @Test
    @Order(1)
    @DisplayName("determineSentiment")
    public void testDetermineSentiment() {
        Map<String, Integer> sentimentCounters = Map.of("positive", 3, "negative", 1);
        assertEquals("positive", analyzer.determineSentiment(sentimentCounters));

        sentimentCounters = Map.of("positive", 1, "negative", 4);
        assertEquals("negative", analyzer.determineSentiment(sentimentCounters));

        sentimentCounters = Map.of("positive", 2, "negative", 2);
        assertEquals("neutral", analyzer.determineSentiment(sentimentCounters));

        sentimentCounters = Map.of();
        assertEquals("neutral", analyzer.determineSentiment(sentimentCounters));
    }

    @Test
    @Order(2)
    @DisplayName("countSentimentWords")
    void testCountSentimentWords() {
        List<String> texts = List.of();
        Map<String, Integer> sentimentCounters = analyzer.countSentimentWords(texts);
        assertEquals(Map.of(), sentimentCounters);

        texts = List.of(
                "The quick brown fox jumps over the lazy dog.",
                "Programming is fun and exciting."
        );
        sentimentCounters = analyzer.countSentimentWords(texts);
        assertEquals(Map.of(), sentimentCounters);

        texts = List.of(
                "Joy and fear can coexist in life.",
                "Love and hate are opposite emotions.",
                "Hope can overcome sadness."
        );
        sentimentCounters = analyzer.countSentimentWords(texts);
        assertEquals(Map.of("positive", 3, "negative", 3), sentimentCounters);

        texts = List.of(
                "Joy, joy, and more joy!",
                "Love, love, and love again."
        );
        sentimentCounters = analyzer.countSentimentWords(texts);
        assertEquals(Map.of("positive", 6), sentimentCounters);

        texts = List.of(
                "JOY and Love are powerful.",
                "joy and LOVE bring peace."
        );
        sentimentCounters = analyzer.countSentimentWords(texts);
        assertEquals(Map.of("positive", 5), sentimentCounters);

        texts = List.of(
                "Joy! Love? Hope, anger; sadness."
        );
        sentimentCounters = analyzer.countSentimentWords(texts);
        assertEquals(Map.of("positive", 3, "negative", 2), sentimentCounters);

        texts = List.of(
                "JOY, joy, and LOVE! Fear? Sadness...",
                "HATE, hate, and ANGER."
        );
        sentimentCounters = analyzer.countSentimentWords(texts);
        assertEquals(Map.of("positive", 3, "negative", 5), sentimentCounters);
    }

    @Test
    @Order(3)
    @DisplayName("analyzeTexts")
    void testAnalyzeTexts() {
        List<String> texts;
        String result;

        texts = List.of();
        result = analyzer.analyzeTexts(texts);
        assertEquals("neutral", result);

        texts = List.of(
                "The quick brown fox jumps over the lazy dog.",
                "Programming is fun and exciting."
        );
        result = analyzer.analyzeTexts(texts);
        assertEquals("neutral", result);

        texts = List.of(
                "Joy and fear can coexist in life.",
                "Love and hate are opposite emotions.",
                "Hope can overcome sadness."
        );
        result = analyzer.analyzeTexts(texts);
        assertEquals("neutral", result);

        texts = List.of(
                "Joy, joy, and more joy!",
                "Love, love, and love again."
        );
        result = analyzer.analyzeTexts(texts);
        assertEquals("positive", result);

        texts = List.of(
                "JOY and Love are powerful.",
                "joy and LOVE bring peace."
        );
        result = analyzer.analyzeTexts(texts);
        assertEquals("positive", result);

        texts = List.of(
                "Joy! Love? Hope, anger; sadness."
        );
        result = analyzer.analyzeTexts(texts);
        assertEquals("positive", result);

        texts = List.of(
                "JOY, joy, and LOVE! Fear? Sadness...",
                "HATE, hate, and ANGER."
        );
        result = analyzer.analyzeTexts(texts);
        assertEquals("negative", result);

        texts = List.of(
                "Anger and sadness consume our thoughts.",
                "Fear and hate dominate the world."
        );
        result = analyzer.analyzeTexts(texts);
        assertEquals("negative", result);

        texts = List.of(
                "Peace, love, and hope are essential for joy.",
                "Hate, anger, and fear destroy happiness."
        );
        result = analyzer.analyzeTexts(texts);
        assertEquals("positive", result);

        texts = List.of("Joy.");
        result = analyzer.analyzeTexts(texts);
        assertEquals("positive", result);

        texts = List.of("Sadness.");
        result = analyzer.analyzeTexts(texts);
        assertEquals("negative", result);

        texts = Collections.nCopies(1000, "Love and hate.");
        result = analyzer.analyzeTexts(texts);
        assertEquals("neutral", result);
    }

}
