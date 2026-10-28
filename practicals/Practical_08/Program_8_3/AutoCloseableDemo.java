class DatabaseSession implements AutoCloseable {
    private final String sessionName;
    private boolean open;

    public DatabaseSession(String sessionName) {
        this.sessionName = sessionName;
        this.open = true;
        System.out.println("[Resource] Opened session: " + sessionName);
    }

    public void executeQuery(String sql, boolean simulateError) throws Exception {
        if (!open) {
            throw new IllegalStateException("Session already closed!");
        }
        System.out.println("[Resource] Executing SQL: " + sql);
        if (simulateError) {
            throw new RuntimeException("QueryExecutionException: Syntax error near 'FROM'");
        }
        System.out.println("[Resource] Query completed successfully.");
    }

    @Override
    public void close() {
        open = false;
        System.out.println("[Resource] close() automatically invoked for session: " + sessionName);
    }
}

public class AutoCloseableDemo {
    public static void main(String[] args) {
        System.out.println("=== Case 1: Normal Execution with try-with-resources ===");
        try (DatabaseSession session = new DatabaseSession("MainDB-Conn-1")) {
            session.executeQuery("SELECT * FROM students WHERE id = '25CE098'", false);
        } catch (Exception e) {
            System.out.println("[Caught] " + e.getMessage());
        }

        System.out.println("\n=== Case 2: Exception Thrown Inside try-with-resources ===");
        try (DatabaseSession session = new DatabaseSession("MainDB-Conn-2")) {
            session.executeQuery("SELECT * FORM broken_query", true);
        } catch (Exception e) {
            System.out.println("[Caught Original Error] " + e.getMessage());
        }
    }
}
