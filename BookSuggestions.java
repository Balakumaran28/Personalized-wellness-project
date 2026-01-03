import java.util.*;
import java.util.stream.Collectors;

public class BookSuggestions {

    private final Scanner sc;

    public BookSuggestions(Scanner sc) {
        this.sc = sc;
    }


    public void showBookMenu() {
        while (true) {
            System.out.println("\n--- BOOK SUGGESTIONS ---");
            System.out.println("1. Find Similar Books");
            System.out.println("2. Browse by Genre");
            System.out.println("3. Personalized Suggestions (need assessment result)");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");
            String choice = sc.next().trim();

            switch (choice) {
                case "1":
                    findSimilarBooks();
                    break;
                case "2":
                    browseByGenre();
                    break;
                case "3":
                    System.out.println("To get personalized suggestions run the assessment from Main Menu first. You can also paste a mindset here.");
                    sc.nextLine(); // consume newline
                    System.out.print("Enter detected mindset (or press Enter to go back): ");
                    String mood = sc.nextLine().trim();
                    if (!mood.isEmpty()) showPersonalizedByMindset(mood);
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid option. Please enter 1-4.");
            }
        }
    }


    private static String normalize(String s) {
        if (s == null) return "";
        // lower-case, remove punctuation except keep letters/numbers and spaces
        return s.trim().toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", " ");
    }


    private Runnable findBookRunnableByTitle(String userInput, Map<String, Runnable> map) {
        String norm = normalize(userInput);
        if (norm.isEmpty()) return null;


        Map<String, Runnable> normMap = new LinkedHashMap<>();
        for (String key : map.keySet()) {
            normMap.put(normalize(key), map.get(key));
        }
        if (normMap.containsKey(norm)) return normMap.get(norm);


        for (String k : normMap.keySet()) {
            if (k.startsWith(norm) || norm.startsWith(k)) return normMap.get(k);
        }


        for (String k : normMap.keySet()) {
            if (k.contains(norm) || norm.contains(k)) return normMap.get(k);
        }

        return null;
    }

    private String bestMatchingKey(String userInput, Set<String> keys) {
        String norm = normalize(userInput);
        if (norm.isEmpty()) return null;

        String best = null;
        int bestScore = -1;
        for (String k : keys) {
            String nk = normalize(k);
            if (nk.equals(norm)) return k;
            if (nk.startsWith(norm) || norm.startsWith(nk)) return k;
            if (nk.contains(norm) || norm.contains(nk)) return k;

            int score = commonTokenScore(nk, norm);
            if (score > bestScore) {
                bestScore = score;
                best = k;
            }
        }
        return best;
    }

    private int commonTokenScore(String a, String b) {
        Set<String> sa = new HashSet<>(Arrays.asList(a.split(" ")));
        Set<String> sb = new HashSet<>(Arrays.asList(b.split(" ")));
        sa.retainAll(sb);
        return sa.size();
    }

    private void promptReturn() {
        System.out.println("\nPress Enter to return to Book Suggestions menu...");
        sc.nextLine();
    }


    private void findSimilarBooks() {
        Map<String, Runnable> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        map.put("Atomic Habits", this::suggestAtomicHabits);
        map.put("Rich Dad Poor Dad", this::suggestRichDadPoorDad);
        map.put("The Alchemist", this::suggestTheAlchemist);
        map.put("48 Laws of Power", this::suggest48LawsOfPower);
        map.put("Ikigai", this::suggestIkigai);
        map.put("The Subtle Art of Not Giving a F*ck", this::suggestSubtleArt);
        map.put("Think Like a Monk", this::suggestThinkLikeAMonk);
        map.put("Deep Work", this::suggestDeepWork);
        map.put("The Psychology of Money", this::suggestPsychologyOfMoney);
        map.put("Harry Potter", this::suggestHarryPotter);

        sc.nextLine();
        System.out.print("Enter book title (e.g. Atomic Habits): ");
        String title = sc.nextLine().trim();

        Runnable r = findBookRunnableByTitle(title, map);
        if (r != null) {
            r.run();
        } else {

            String suggestion = bestMatchingKey(title, map.keySet());
            System.out.println("Sorry, that book is not in the quick list.");
            System.out.println("Try one of: " + String.join(", ", map.keySet()));
            if (suggestion != null) System.out.println("Closest match: " + suggestion);
        }
        promptReturn();
    }


    private void browseByGenre() {
        Map<String, Runnable> genres = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        genres.put("Fantasy", this::genreFantasy);
        genres.put("Self-Help", this::genreSelfHelp);
        genres.put("Self Help", this::genreSelfHelp);      // synonyms
        genres.put("SelfHelp", this::genreSelfHelp);
        genres.put("Fiction", this::genreFeelGoodFiction);
        genres.put("Motivation", this::genreMotivation);
        genres.put("Business", this::genreBusinessMoney);

        sc.nextLine();
        System.out.print("Enter genre (e.g. Fantasy / Self-Help / Fiction / Motivation / Business): ");
        String g = sc.nextLine().trim();


        Runnable r = null;

        r = genres.get(g);
        if (r == null) {

            String best = bestMatchingKey(g, genres.keySet());
            if (best != null) r = genres.get(best);
        }

        if (r != null) r.run();
        else System.out.println("Genre not found. Try: " + String.join(", ", genres.keySet()));
        promptReturn();
    }


    public void showPersonalizedByMindset(String mindset) {
        String key = normalize(mindset);
        System.out.println("\nPersonalized suggestions for mindset: " + mindset);


        Map<String, Runnable> m = new HashMap<>();
        m.put(normalize("Energized and Positive"), this::emotionEnergized);
        m.put(normalize("Calm and Reflective"), this::emotionCalmReflective);
        m.put(normalize("Overwhelmed and Fatigued"), this::emotionOverwhelmed);
        m.put(normalize("Anxious or Restless"), this::emotionRestless);
        m.put(normalize("Low Self-Worth"), this::emotionLowConfidence);
        m.put(normalize("Growth-Oriented Mindset"), this::emotionGrowthOriented);
        m.put(normalize("Burnout Warning"), this::emotionBurnout);
        m.put(normalize("Social Withdrawal"), this::emotionSocialWithdrawal);
        m.put(normalize("Emotionally Composed"), this::emotionBalanced);
        m.put(normalize("Perfectionism & Self-Criticism"), this::emotionPerfectionism);
        m.put(normalize("Unstable Focus"), this::emotionInconsistentFocus);
        m.put(normalize("Drained but Still Hopeful"), this::emotionDrainedHopeful);
        // also accept short forms
        m.put("energized", this::emotionEnergized);
        m.put("calm", this::emotionCalmReflective);
        m.put("overwhelmed", this::emotionOverwhelmed);
        m.put("restless", this::emotionRestless);
        m.put("burnout", this::emotionBurnout);

        Runnable r = m.get(key);
        if (r == null) {

            for (String k : m.keySet()) {
                if (k.contains(key) || key.contains(k) || k.startsWith(key) || key.startsWith(k)) {
                    r = m.get(k);
                    break;
                }
            }
        }

        if (r != null) r.run();
        else System.out.println("Could not map mindset to recommendations. Try a simpler phrase like 'Energized and Positive'.");

        promptReturn();
    }


    public void suggestAtomicHabits() {
        System.out.println("\nAtomic Habits — James Clear");
        System.out.println("Similar Books:\n- The Power of Habit — Charles Duhigg\n- Tiny Habits — BJ Fogg\n- Make Your Bed — Admiral McRaven");
        System.out.println("Level-Up Book: Deep Work — Cal Newport");
        System.out.println("Response: Since you liked Atomic Habits, you’ll love these books that help you build consistency and reshape identity. Deep Work will push your focus to a higher level.");
    }

    public void suggestRichDadPoorDad() {
        System.out.println("\nRich Dad Poor Dad — Robert Kiyosaki");
        System.out.println("Similar Books:\n- The Millionaire Next Door — Thomas Stanley\n- Think and Grow Rich — Napoleon Hill\n- The Psychology of Money — Morgan Housel");
        System.out.println("Level-Up Book: The Intelligent Investor — Benjamin Graham");
        System.out.println("Response: You’re exploring money mindset — these books continue the same journey. The Intelligent Investor is your advanced upgrade.");
    }

    public void suggestTheAlchemist() {
        System.out.println("\nThe Alchemist — Paulo Coelho");
        System.out.println("Similar Books:\n- The Little Prince — Antoine de Saint-Exupéry\n- Siddhartha — Hermann Hesse\n- The Prophet — Kahlil Gibran");
        System.out.println("Level-Up Book: Man’s Search for Meaning — Viktor Frankl");
        System.out.println("Response: You’re drawn to soulful journeys. These books share the spirit of purpose and courage. Man’s Search for Meaning deepens your reflection.");
    }

    public void suggest48LawsOfPower() {
        System.out.println("\n48 Laws of Power — Robert Greene");
        System.out.println("Similar Books:\n- The Art of War — Sun Tzu\n- Mastery — Robert Greene\n- The Prince — Niccolò Machiavelli");
        System.out.println("Level-Up Book: The 33 Strategies of War — Robert Greene");
        System.out.println("Response: You like strategy and human behavior. These sharpen your influence. 33 Strategies of War deepens tactical thinking.");
    }

    public void suggestIkigai() {
        System.out.println("\nIkigai — Héctor García & Francesc Miralles");
        System.out.println("Similar Books:\n- The Happiness Advantage — Shawn Achor\n- The Art of Happiness — Dalai Lama\n- Stillness Is the Key — Ryan Holiday");
        System.out.println("Level-Up Book: Flow — Mihaly Csikszentmihalyi");
        System.out.println("Response: You’re exploring purpose and joy. These continue that peaceful journey. Flow takes you deeper into living fully engaged.");
    }

    public void suggestSubtleArt() {
        System.out.println("\nThe Subtle Art of Not Giving a F*ck — Mark Manson");
        System.out.println("Similar Books:\n- Everything Is F*cked — Mark Manson\n- You Are a Badass — Jen Sincero\n- The Courage to Be Disliked — Ichiro Kishimi");
        System.out.println("Level-Up Book: The Mountain Is You — Brianna Wiest");
        System.out.println("Response: You like bold, honest books. These bring the same raw clarity. The Mountain Is You helps transform emotional blocks.");
    }

    public void suggestThinkLikeAMonk() {
        System.out.println("\nThink Like a Monk — Jay Shetty");
        System.out.println("Similar Books:\n- The Power of Now — Eckhart Tolle\n- The Miracle of Mindfulness — Thich Nhat Hanh\n- The Untethered Soul — Michael A. Singer");
        System.out.println("Level-Up Book: The Bhagavad Gita (simplified versions)");
        System.out.println("Response: You’re seeking clarity and stillness. These books quiet the mind. The Gita gives timeless wisdom.");
    }

    public void suggestDeepWork() {
        System.out.println("\nDeep Work — Cal Newport");
        System.out.println("Similar Books:\n- Digital Minimalism — Cal Newport\n- Essentialism — Greg McKeown\n- The ONE Thing — Gary Keller");
        System.out.println("Level-Up Book: Peak — Anders Ericsson");
        System.out.println("Response: You want sharper focus and mastery. These books build mental discipline. Peak explains how top skills are built.");
    }

    public void suggestPsychologyOfMoney() {
        System.out.println("\nThe Psychology of Money — Morgan Housel");
        System.out.println("Similar Books:\n- Nudge — Richard Thaler\n- Money: Master the Game — Tony Robbins\n- Your Money or Your Life — Vicki Robin");
        System.out.println("Level-Up Book: Thinking, Fast and Slow — Daniel Kahneman");
    }

    public void suggestHarryPotter() {
        System.out.println("\nHarry Potter Series");
        System.out.println("Similar Books:\n- Percy Jackson — Rick Riordan\n- The Chronicles of Narnia — C.S. Lewis\n- The Hobbit — J.R.R. Tolkien");
        System.out.println("Level-Up Book: The Lord of the Rings");
    }

    // Genre methods
    public void genreFantasy() {
        System.out.println("\nSomething magical to pull you away from reality for a moment.\n- The Hobbit\n- Percy Jackson\n- Mistborn");
    }

    public void genreSelfHelp() {
        System.out.println("\nBooks that help you breathe, grow, and realign yourself.\n- Atomic Habits\n- Stillness Is the Key\n- The Mountain Is You");
    }

    public void genreFeelGoodFiction() {
        System.out.println("\nWarm stories that comfort your heart.\n- The Alchemist\n- The Little Prince\n- Eleanor Oliphant Is Completely Fine");
    }

    public void genreMotivation() {
        System.out.println("\nFor days when you want to rebuild your strength.\n- Grit\n- Make Your Bed\n- The Power of Habit");
    }

    public void genreBusinessMoney() {
        System.out.println("\nClear, practical, future-focused reads.\n- Rich Dad Poor Dad\n- The Intelligent Investor\n- Zero to One");
    }

    // Emotion-based recommendations
    public void emotionEnergized() {
        System.out.println("\nRecommended: \n- Atomic Habits\n- Deep Work\n- The ONE Thing");
    }

    public void emotionCalmReflective() {
        System.out.println("\nRecommended: \n- Ikigai\n- Stillness Is the Key\n- The Little Prince");
    }

    public void emotionOverwhelmed() {
        System.out.println("\nRecommended: \n- The Subtle Art of Not Giving a F*ck\n- Make Your Bed\n- The Power of Now");
    }

    public void emotionRestless() {
        System.out.println("\nRecommended: \n- The Courage to Be Disliked\n- The Untethered Soul\n- Everything Is F*cked");
    }

    public void emotionLowConfidence() {
        System.out.println("\nRecommended: \n- You Are a Badass\n- The Mountain Is You\n- Grit");
    }

    public void emotionGrowthOriented() {
        System.out.println("\nRecommended: \n- Mastery\n- Peak\n- Think Again");
    }

    public void emotionBurnout() {
        System.out.println("\nRecommended: \n- Stillness Is the Key\n- Atomic Habits\n- Essentialism");
    }

    public void emotionSocialWithdrawal() {
        System.out.println("\nRecommended: \n- Eleanor Oliphant Is Completely Fine\n- The Alchemist\n- The Midnight Library");
    }

    public void emotionBalanced() {
        System.out.println("\nRecommended: \n- The Power of Now\n- The Art of Happiness\n- Ikigai");
    }

    public void emotionPerfectionism() {
        System.out.println("\nRecommended: \n- The Gifts of Imperfection\n- Essentialism\n- The Mountain Is You");
    }

    public void emotionInconsistentFocus() {
        System.out.println("\nRecommended: \n- Deep Work\n- The ONE Thing\n- Eat That Frog");
    }

    public void emotionDrainedHopeful() {
        System.out.println("\nRecommended: \n- The Midnight Library\n- The Mountain Is You\n- The Alchemist");
    }

}
