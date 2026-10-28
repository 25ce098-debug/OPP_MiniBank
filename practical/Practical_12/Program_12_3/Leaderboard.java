import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leaderboard {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Bhavy", 950);
        scores.put("Aarav", 880);
        scores.put("Diya", 950);
        scores.put("Riya", 990);
        scores.put("Kabir", 880);
        scores.put("Neha", 760);

        List<Map.Entry<String, Integer>> ranking = new ArrayList<>(scores.entrySet());
        ranking.sort(
                Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                        .reversed()
                        .thenComparing(Map.Entry::getKey)
        );

        int topN = 4;
        System.out.println("All Player Scores: " + scores);
        System.out.println("\n=== Top-" + topN + " Leaderboard (Score Desc, Tie-Break by Name Asc) ===");
        for (int i = 0; i < Math.min(topN, ranking.size()); i++) {
            Map.Entry<String, Integer> entry = ranking.get(i);
            System.out.printf("Rank #%d : %-8s - %d pts%n", (i + 1), entry.getKey(), entry.getValue());
        }
    }
}
