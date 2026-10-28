import java.util.Arrays;

public class BoundedStats {
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (N val : values) {
            sum += val.doubleValue();
        }
        return sum / values.length;
    }

    public static void main(String[] args) {
        Integer[] intArr = {10, 20, 30, 40, 50};
        Double[] dblArr = {2.5, 3.5, 4.5, 5.5};
        Long[] lngArr = {100L, 200L, 300L};

        System.out.println("Integer[] " + Arrays.toString(intArr) + " -> Average: " + average(intArr));
        System.out.println("Double[]  " + Arrays.toString(dblArr) + " -> Average: " + average(dblArr));
        System.out.println("Long[]    " + Arrays.toString(lngArr) + " -> Average: " + average(lngArr));

        // Note: Calling average(new String[]{"a", "b"}) fails at compile time because
        // String does not extend java.lang.Number, guaranteeing compile-time type safety
        // and ensuring doubleValue() is available on every element.
        System.out.println("Note: Non-Number arrays (e.g., String[]) are rejected at compile time by <N extends Number>.");
    }
}
