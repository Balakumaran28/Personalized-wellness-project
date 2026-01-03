import java.sql.*;
import java.util.*;

public class MoodAnalytics {

    public static void showAnalytics(int userId) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT mindset, score, created_at FROM assessments " +
                            "WHERE user_id = ? ORDER BY created_at DESC LIMIT 10"
            );
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            List<Integer> scores = new ArrayList<>();
            Map<String, Integer> mindsetCount = new HashMap<>();

            System.out.println("\n================ MOOD ANALYTICS REPORT ================");
            System.out.printf("%-20s %-30s %-5s%n", "Date & Time", "Mindset", "Score");
            System.out.println("-------------------------------------------------------");

            while (rs.next()) {
                String mindset = rs.getString("mindset");
                int score = rs.getInt("score");
                Timestamp time = rs.getTimestamp("created_at");

                scores.add(score);
                mindsetCount.put(mindset, mindsetCount.getOrDefault(mindset, 0) + 1);

                System.out.printf("%-20s %-30s %-5d%n",
                        time.toString().substring(0, 19),
                        mindset,
                        score);
            }

            if (scores.isEmpty()) {
                System.out.println("\nNo assessment data available yet.");
                return;
            }


            analyzeTrend(scores);

            // Dominant mindset
            String dominant = Collections.max(
                    mindsetCount.entrySet(),
                    Map.Entry.comparingByValue()
            ).getKey();

            System.out.println("\nDominant mindset: " + dominant);


            provideGuidance(scores);

            System.out.println("=======================================================\n");

        } catch (Exception e) {
            System.out.println("Analytics error: " + e.getMessage());
        }
    }

    private static void analyzeTrend(List<Integer> scores) {
        int latest = scores.get(0);
        int oldest = scores.get(scores.size() - 1);

        System.out.println("\nTrend Analysis:");

        if (latest < oldest)
            System.out.println("Recovery trend detected — You're improving.");
        else if (latest > oldest)
            System.out.println("Stress increase detected — Consider slowing down.");
        else
            System.out.println("Stable emotional state detected.");
    }

    private static void provideGuidance(List<Integer> scores) {
        int avg = scores.stream().mapToInt(i -> i).sum() / scores.size();

        System.out.println("\nGuidance:");

        if (avg <= 3)
            System.out.println("You're in a strong emotional space. Maintain your positive habits.");
        else if (avg <= 6)
            System.out.println("You're doing okay but need consistency and rest.");
        else if (avg <= 9)
            System.out.println("Your system shows rising stress. Prioritize rest and self-care.");
        else
            System.out.println("You're approaching burnout. Strongly consider slowing down and seeking support.");
    }
}

