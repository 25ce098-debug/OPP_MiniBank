import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FolderTreeWalker {
    public static void main(String[] args) throws IOException {
        Path rootDir = Path.of("project_tree");
        Path docsSub = rootDir.resolve("docs");
        Path srcSub = rootDir.resolve("src");
        Files.createDirectories(docsSub);
        Files.createDirectories(srcSub);

        Files.writeString(docsSub.resolve("guide.txt"), "CHARUSAT OOP Practical Guide for 25CE098\n");
        Files.writeString(srcSub.resolve("Main.java"), "public class Main { public static void main(String[] a){} }\n");

        StringBuilder report = new StringBuilder();
        report.append("=== NIO Folder Tree Report ===\n");
        report.append(String.format("%-28s | %-10s | %-25s%n", "Relative Path", "Size (B)", "Last Modified Time"));
        report.append("----------------------------------------------------------------------\n");

        long totalFiles = 0;
        long totalBytes = 0;

        try (Stream<Path> walk = Files.walk(rootDir)) {
            List<Path> regularFiles = walk.filter(Files::isRegularFile).sorted().collect(Collectors.toList());
            for (Path p : regularFiles) {
                BasicFileAttributes attrs = Files.readAttributes(p, BasicFileAttributes.class);
                totalFiles++;
                totalBytes += attrs.size();
                report.append(String.format("%-28s | %-10d | %-25s%n",
                        rootDir.relativize(p), attrs.size(), attrs.lastModifiedTime().toString()));
            }
        }

        report.append("----------------------------------------------------------------------\n");
        report.append("Total Files: ").append(totalFiles).append(" | Total Size: ").append(totalBytes).append(" bytes\n");

        Path reportFile = Path.of("tree_report.txt");
        Files.writeString(reportFile, report.toString());

        System.out.println("Report written to: " + reportFile.toAbsolutePath().getFileName());
        System.out.print(Files.readString(reportFile));
    }
}
