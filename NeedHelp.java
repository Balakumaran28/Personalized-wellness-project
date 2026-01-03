import java.util.Scanner;

public class NeedHelp {

    private final Scanner sc;

    public NeedHelp(Scanner sc) {
        this.sc = sc;
    }
    public void showHelpMenu() {
        while (true) {
            System.out.println("\n--- NEED HELP → Immediate Emotional Support ---");
            System.out.println("1. Depressed");
            System.out.println("2. Stressed");
            System.out.println("3. Feeling Tired");
            System.out.println("4. Confused");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");

            String choice = sc.next().trim();
            switch (choice) {
                case "1":
                    showDepressedHelp();
                    break;
                case "2":
                    showStressedHelp();
                    break;
                case "3":
                    showTiredHelp();
                    break;
                case "4":
                    showConfusedHelp();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Invalid option. Please enter 1-5.");
            }
        }
    }

    // 1.1 Depressed Suggestions
    public void showDepressedHelp() {
        System.out.println("\n--- Feeling Depressed: Immediate Help Suggestions ---");
        System.out.println("1. Take a warm bath or shower – Relaxes muscles and reduces heaviness.");
        System.out.println("2. Sit in sunlight for at least 10 minutes – Helps improve mood.");
        System.out.println("3. Step outside and walk for 5–10 minutes – No pace, just move.");
        System.out.println("4. Write down 3 things making you feel heavy – Helps release pressure.");
        System.out.println("5. Message one trusted person: \"I'm not okay today. Just wanted you to know.\"");
        promptReturn();
    }

    // 1.2 Stressed Suggestions
    public void showStressedHelp() {
        System.out.println("\n--- Feeling Stressed: Immediate Help Suggestions ---");
        System.out.println("1. Take a 2-minute breathing break – Inhale 4s, Hold 2s, Exhale 6s.");
        System.out.println("2. Make a 'Today's Top 3' list – Focus only on 3 important tasks.");
        System.out.println("3. Drink a full glass of water slowly – Helps reset your mind.");
        System.out.println("4. Do a 1-minute stretch – Neck roll, shoulder roll, chest opening.");
        System.out.println("5. Step away from screen for 3 minutes – Look at something far away.");
        promptReturn();
    }

    // 1.3 Feeling Tired Suggestions
    public void showTiredHelp() {
        System.out.println("\n--- Feeling Tired: Quick Reset Suggestions ---");
        System.out.println("1. Lie down for 10 minutes with eyes closed – Just a reset, not sleep.");
        System.out.println("2. Wash your face with cool water – Instant refresh.");
        System.out.println("3. Eat something light – Banana, nuts, or a small snack.");
        System.out.println("4. Do a slow 30-second stretch – Hands above head, deep exhale.");
        System.out.println("5. Take a 5-minute walk – Light movement boosts energy.");
        promptReturn();
    }

    // 1.4 Confused Suggestions
    public void showConfusedHelp() {
        System.out.println("\n--- Feeling Confused: Clarity Suggestions ---");
        System.out.println("1. Write your problem in one sentence – Simplify it.");
        System.out.println("2. Make one tiny decision now – Helps break confusion.");
        System.out.println("3. Make a 1-minute pros & cons list – Quick clarity.");
        System.out.println("4. Talk aloud to yourself for 30 seconds – Helps untangle thoughts.");
        System.out.println("5. Break the situation into 3 questions:");
        System.out.println("   - What do I know?");
        System.out.println("   - What don't I know?");
        System.out.println("   - What do I need right now?");
        promptReturn();
    }

    private void promptReturn() {
        System.out.println("\nPress Enter to return to the Need Help menu...");
        sc.nextLine();
        sc.nextLine();
    }
}
