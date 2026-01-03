import java.util.TreeMap;

public class MindsetScoreMap {

    public static final TreeMap<String, Integer> mindsetScore = new TreeMap<>();

    static {
        mindsetScore.put("Energized and Positive", 1);
        mindsetScore.put("Growth-Oriented Mindset", 2);
        mindsetScore.put("Calm and Reflective", 3);
        mindsetScore.put("Emotionally Composed", 4);
        mindsetScore.put("Drained but Still Hopeful", 5);
        mindsetScore.put("Overwhelmed and Fatigued", 6);
        mindsetScore.put("Unstable Focus", 7);
        mindsetScore.put("Low Self-Worth", 8);
        mindsetScore.put("Anxious or Restless", 9);
        mindsetScore.put("Burnout Warning", 10);
        mindsetScore.put("Perfectionism & Self-Criticism", 11);
        mindsetScore.put("Social Withdrawal", 12);
    }
}

