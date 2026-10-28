/**
 * MenuOption — the fixed set of actions MiniBank offers.
 * Each constant maps to a numbered menu entry.
 */
public enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT;

    /**
     * Returns a user-friendly label for this option.
     * Example: OPEN_ACCOUNT → "Open Account"
     */
    public String label() {
        String raw = name().replace('_', ' ');
        // Capitalize first letter of each word
        StringBuilder sb = new StringBuilder();
        for (String word : raw.split(" ")) {
            if (!sb.isEmpty()) sb.append(' ');
            sb.append(Character.toUpperCase(word.charAt(0)));
            sb.append(word.substring(1).toLowerCase());
        }
        return sb.toString();
    }
}

