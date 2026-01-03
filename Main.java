import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);


        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        int userId;
        try {
            userId = UserService.getOrCreateUser(name);
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            return;
        }

        NeedHelp needHelp = new NeedHelp(sc);
        BookSuggestions books = new BookSuggestions(sc);
        Assessment assessment = new Assessment(sc);

        while (true) {
            System.out.println("\n===== WELLNESS & SUPPORT SYSTEM =====");
            System.out.println("1. Need Help (Immediate Support)");
            System.out.println("2. Book Suggestions");
            System.out.println("3. Take Assessment");
            System.out.println("4. View Mood Analytics");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            String ch = sc.next().trim();

            switch (ch) {
                case "1":
                    needHelp.showHelpMenu();
                    break;

                case "2":
                    books.showBookMenu();
                    break;

                case "3":
                    String mindset = assessment.showAssessmentMenu();

                    int score = MindsetScoreMap.mindsetScore.getOrDefault(mindset, 7);

                    try {
                        Integer lastScore = AssessmentService.getLastScore(userId);
                        AssessmentService.saveAssessment(userId, mindset, score);
                        ProgressAnalyzer.analyze(lastScore, score);
                    } catch (Exception e) {
                        System.out.println("Error saving assessment: " + e.getMessage());
                    }

                    System.out.print("Would you like book suggestions for \"" + mindset + "\" now? (y/n): ");
                    String resp = sc.next().trim();
                    if (resp.equalsIgnoreCase("y")) {
                        books.showPersonalizedByMindset(mindset);
                    }
                    break;

                case "4":
                    MoodAnalytics.showAnalytics(userId);
                    break;

                case "5":
                    System.out.println("Goodbye — take care!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Enter 1-4.");
            }
        }
    }
}
