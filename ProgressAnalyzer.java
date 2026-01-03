public class ProgressAnalyzer {

    public static void analyze(Integer lastScore, int currentScore) {

        if (lastScore == null) {
            System.out.println("\nThis is your first recorded assessment ");
            return;
        }

        if (currentScore < lastScore) {
            System.out.println("\nYou're improving — great progress ");
        }
        else if (currentScore > lastScore) {
            System.out.println("\nYou're struggling more than before — let's take things gently");
        }
        else {
            System.out.println("\nYou're maintaining your state steadily");
        }
    }
}

