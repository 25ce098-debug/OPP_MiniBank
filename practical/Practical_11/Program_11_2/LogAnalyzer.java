import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class LogAnalyzer {
    public static void main(String[] args) throws IOException {
        Path logsDir = Path.of("sample_logs");
        Files.createDirectories(logsDir);

        Path log1 = logsDir.resolve("app1.log");
        Path log2 = logsDir.resolve("app2.log");

        Files.writeString(log1,
                "INFO Server started on port 8080\n" +
                "ERROR Failed to connect to database\n" +
                "INFO Request processed for 25CE098\n");

        Files.writeString(log2,
                "INFO User login: Bhavy Pan\n" +
                "ERROR Timeout while reading socket\n" +
                "WARN High memory usage\n" +
                "ERROR Disk write failure\n");

        Path[] files = {log1, log2};
        String keyword = "ERROR";
        int totalLines = 0;
        int keywordMatches = 0;

        System.out.println("Analyzing log files for keyword: '" + keyword + "'");
        for (Path file : files) {
            BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
            List<String> lines = Files.readAllLines(file);
            int fileMatches = 0;

            for (String line : lines) {
                totalLines++;
                if (line.contains(keyword)) {
                    keywordMatches++;
                    fileMatches++;
                }
            }
            System.out.println(" - File: " + file.getFileName() + " | Size: " + attrs.size()
                    + " bytes | Lines: " + lines.size() + " | '" + keyword + "' matches: " + fileMatches);
        }

        System.out.println("Total Lines Across All Files : " + totalLines);
        System.out.println("Total Keyword Matches ('" + keyword + "'): " + keywordMatches);
    }
}
