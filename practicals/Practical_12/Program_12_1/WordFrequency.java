import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordFrequency {
    public static void main(String[] args) {
        String paragraph = "java collections make java data structures easy and java maps store key value pairs in java";
        String[] words = paragraph.toLowerCase().split("\\\s+");

        Map<String, Integer> freqMap = new HashMap<>();
        for (String w : words) {
            freqMap.put(w, freqMap.getOrDefault(w, 0) + 1);
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(freqMap.entrySet());
        Comparator<Map.Entry<String, Integer>> byFreqDesc = (a, b) -> {
            int cmp = Integer.compare(b.getValue(), a.getValue());
            return (cmp != 0) ? cmp : a.getKey().compareTo(b.getKey());
        };
        entries.sort(byFreqDesc);

        System.out.println("Input Paragraph: \"" + paragraph + "\"");
        System.out.println("\nWord Frequencies (Highest First):");
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println("  " + String.format("%-12s", entry.getKey()) + " : " + entry.getValue());
        }
    }
}
