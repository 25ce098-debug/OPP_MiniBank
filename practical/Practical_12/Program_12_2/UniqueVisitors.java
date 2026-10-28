import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UniqueVisitors {
    public static void main(String[] args) {
        List<String> logins = Arrays.asList(
                "bhavy", "riya", "bhavy", "aarav", "riya", "diya", "kabir", "aarav"
        );

        Set<String> uniqueUsers = new HashSet<>(logins);
        Set<String> orderedUsers = new LinkedHashSet<>(logins);

        System.out.println("Login Stream         : " + logins);
        System.out.println("Unique Visitor Count : " + uniqueUsers.size());
        System.out.println("First-Seen Order     : " + orderedUsers);

        System.out.print("First 3 Distinct     : ");
        Iterator<String> it = orderedUsers.iterator();
        for (int i = 0; i < 3 && it.hasNext(); i++) {
            System.out.print(it.next() + (i < 2 ? ", " : "\n"));
        }
    }
}
