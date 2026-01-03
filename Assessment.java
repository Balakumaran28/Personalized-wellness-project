import java.util.*;

public class Assessment {

    private final Scanner sc;

    public Assessment(Scanner sc) {
        this.sc = sc;
    }

    public String showAssessmentMenu() {
        System.out.println("\n===== SELF-ASSESSMENT TEST =====");
        char[] answers = new char[15];

        answers[0]  = ask(1, "How do you feel when starting a new task?", "A) Excited", "B) Overwhelmed", "C) Curious", "D) Hesitant");
        answers[1]  = ask(2, "What best describes your energy right now?", "A) High and motivated", "B) Tired and dull", "C) Calm and steady", "D) Uneasy or restless");
        answers[2]  = ask(3, "How do you respond to challenges today?", "A) Confidently", "B) With doubt", "C) Wanting to learn", "D) Avoiding them");
        answers[3]  = ask(4, "How's your focus today?", "A) Sharp and clear", "B) Scattered", "C) Wandering but comes back", "D) Hard to maintain");
        answers[4]  = ask(5, "How do you see yourself today?", "A) Positive and capable", "B) Unsure", "C) Wanting to grow", "D) Stuck or low");
        answers[5]  = ask(6, "How do you react to mistakes today?", "A) Learn and move on", "B) Feel guilty", "C) Curious to fix it", "D) Blame yourself");
        answers[6]  = ask(7, "How do you feel about your goals right now?", "A) On track", "B) Off path", "C) Planning ahead", "D) Not clear");
        answers[7]  = ask(8, "How are you handling emotions now?", "A) Balanced", "B) Ups and downs", "C) Noticing and managing", "D) Struggling");
        answers[8]  = ask(9, "What do you think about your future today?", "A) Hopeful", "B) Worried", "C) Open-minded", "D) Doubtful");
        answers[9]  = ask(10, "How do you feel about taking breaks or rest?", "A) It's refreshing", "B) It makes me anxious", "C) I want to but don't", "D) I feel lazy doing it");
        answers[10] = ask(11, "How are you handling social interactions today?", "A) Engaged and happy", "B) Avoiding people", "C) Talking when needed", "D) Feeling disconnected");
        answers[11] = ask(12, "How would you describe your sleep lately?", "A) Restful", "B) Poor", "C) Enough but not great", "D) Hard to sleep");
        answers[12] = ask(13, "How often do you compare yourself to others today?", "A) Rarely", "B) Often", "C) I'm trying not to", "D) Too much");
        answers[13] = ask(14, "How do you feel about your daily routine today?", "A) In control", "B) Out of sync", "C) Wanting to improve", "D) Stuck in a loop");
        answers[14] = ask(15, "What's your current self-talk like?", "A) Encouraging", "B) Negative", "C) Reflective", "D) Self-doubting");

        String result = evaluate(answers);
        System.out.println("\nAssessment finished. Detected mindset: " + result);
        System.out.println("You can use this phrase "+result+"in Book Suggestions -> Personalized Suggestions to get matched books.\n");
        return result;
    }

    private char ask(int qNo, String q, String... options) {
        System.out.println("\nQ" + qNo + ". " + q);
        for (String op : options) System.out.println(op);

        while (true) {
            System.out.print("Enter A/B/C/D: ");
            String token = sc.next().trim();
            if (token.isEmpty()) continue;
            char ch = Character.toUpperCase(token.charAt(0));
            if (ch == 'A' || ch == 'B' || ch == 'C' || ch == 'D') return ch;
            System.out.println("Invalid choice. Try again.");
        }
    }


    private String evaluate(char[] ans) {
        int countA = 0, countB = 0, countC = 0, countD = 0;
        for (char c : ans) {
            if (c == 'A') countA++;
            else if (c == 'B') countB++;
            else if (c == 'C') countC++;
            else if (c == 'D') countD++;
        }


        if (countA > countB && countA > countC && countA > countD) {
            printResult("Energized and Positive", "You're feeling motivated, stable, and ready to move forward.");
            return "Energized and Positive";
        }


        if (countC > countA && countC > countB && countC > countD) {
            printResult("Calm and Reflective", "Your mind is steady and thoughtful.");
            return "Calm and Reflective";
        }

        if (countB > countA && countB > countC && countB > countD) {
            printResult("Overwhelmed and Fatigued", "You're mentally tired or under pressure.");
            return "Overwhelmed and Fatigued";
        }


        if (isDominantD(ans, new int[]{2, 4, 8, 11})) {
            printResult("Anxious or Restless", "Your inner state feels unsettled or distracted.");
            return "Anxious or Restless";
        }


        if (mixBD(ans, new int[]{3,5,6,9,15})) {
            printResult("Low Self-Worth", "You're doubting yourself and feeling stuck.");
            return "Low Self-Worth";
        }


        if (mixAC(ans, new int[]{1,3,7,9,14})) {
            printResult("Growth-Oriented Mindset", "You are learning, evolving, and embracing challenges.");
            return "Growth-Oriented Mindset";
        }


        if (mixBD(ans, new int[]{2,4,10,12,14})) {
            printResult("Burnout Warning", "Your energy is collapsing and you’re pushing too hard. Rest is important.");
            return "Burnout Warning";
        }

        if (mixBD(ans, new int[]{11,9,15})) {
            printResult("Social Withdrawal / Emotional Isolation", "You’re pulling away from people and feeling disconnected.");
            return "Social Withdrawal";
        }


        if (mixAC(ans, new int[]{8,10,6,4})) {
            printResult("Emotionally Composed", "You’re processing emotions with maturity and stability.");
            return "Emotionally Composed";
        }

        if (isPerfectionism(ans)) {
            printResult("Perfectionism & Self-Criticism", "You are striving but being overly self-critical. Try gentler self-talk.");
            return "Perfectionism & Self-Criticism";
        }


        if (mixABD(ans, new int[]{2,4,14})) {
            printResult("Unstable Focus", "Your productivity is inconsistent and your attention is pulled many ways.");
            return "Unstable Focus";
        }


        if (drainedButHopeful(ans)) {
            printResult("Drained but Still Hopeful", "You’re tired but still holding on to possibilities.");
            return "Drained but Still Hopeful";
        }

        printResult("Mixed / Complex", "Result unclear — mixed signals. Consider retaking when you're more settled.");
        return "Mixed / Complex";
    }


    private boolean isDominantD(char[] ans, int[] q) {
        for (int index : q) if (ans[index - 1] != 'D') return false;
        return true;
    }

    private boolean mixBD(char[] ans, int[] q) {
        for (int index : q) if (!(ans[index - 1] == 'B' || ans[index - 1] == 'D')) return false;
        return true;
    }

    private boolean mixAC(char[] ans, int[] q) {
        for (int index : q) if (!(ans[index - 1] == 'A' || ans[index - 1] == 'C')) return false;
        return true;
    }

    private boolean mixABD(char[] ans, int[] q) {
        for (int index : q) if (ans[index - 1] == 'C') return false;
        return true;
    }

    private boolean drainedButHopeful(char[] ans) {
        int countBD = 0;
        for (char c : ans) if (c == 'B' || c == 'D') countBD++;
        boolean bdMajority = countBD >= 8;
        return bdMajority && (ans[0] == 'A' || ans[8] == 'A' || ans[0] == 'C' || ans[8] == 'C');
    }

    private boolean isPerfectionism(char[] ans) {
        return (ans[6 - 1] == 'C' || ans[7 - 1] == 'C' || ans[10 - 1] == 'C')
                && (ans[13 - 1] == 'B' || ans[13 - 1] == 'D')
                && (ans[15 - 1] == 'B' || ans[15 - 1] == 'D');
    }

    private void printResult(String title, String message) {
        System.out.println("\n=== " + title + " ===");
        System.out.println(message);
        System.out.println("====================================");
    }
}
