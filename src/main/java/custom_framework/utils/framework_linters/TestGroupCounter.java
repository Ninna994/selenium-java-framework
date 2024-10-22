package custom_framework.utils.framework_linters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestGroupCounter {

    private static int smokeTestCount = 0;
    private static int regressionTestCount = 0;
    private static int deprecatedCount = 0;
    private static int cleanupCount = 0;
    private static int ignoreCount = 0;
    private static int noGroupCount = 0;

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        List<File> testFiles = findTestFiles(projectPath);

        for (File file : testFiles) {
            List<String> lines;
            try {
                lines = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                System.err.println("Failed to read file: " + file.getAbsolutePath());
                continue;
            }

            String content = String.join("\n", lines);
            countTestGroups(content);
        }

        // Output the counts for each test group
        System.out.println("SmokeTest count: " + smokeTestCount);
        System.out.println("RegressionTest count: " + regressionTestCount);
        System.out.println("Deprecated count: " + deprecatedCount);
        System.out.println("Cleanup count: " + cleanupCount);
        System.out.println("Ignore count: " + ignoreCount);
        System.out.println("No group count: " + noGroupCount);
    }

    private static List<File> findTestFiles(String projectPath) {
        List<File> testFiles = new ArrayList<>();
        File projectDir = new File(projectPath);

        if (projectDir.isDirectory()) {
            File[] files = projectDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        testFiles.addAll(findTestFiles(file.getAbsolutePath()));
                    } else if (file.getName().endsWith("Test.java")) {
                        testFiles.add(file);
                    }
                }
            }
        }

        return testFiles;
    }

    private static void countTestGroups(String content) {
        // Updated regex to match @Test annotation with groups and capture the group names
        Pattern testPattern = Pattern.compile("@Test\\s*\\(.*groups\\s*=\\s*\\{(\"[^\"]+\")\\}.*\\)");
        Matcher matcher = testPattern.matcher(content);

        while (matcher.find()) {
            String groupContent = matcher.group(1); // Get the captured group names

            // Check for specific group names within the captured content
            if (groupContent.contains("SmokeTest")) {
                smokeTestCount++;
            } else if (groupContent.contains("RegressionTest")) {
                regressionTestCount++;
            } else if (groupContent.contains("Deprecated")) {
                deprecatedCount++;
            } else if (groupContent.contains("Cleanup")) {
                cleanupCount++;
            } else if (groupContent.contains("Ignore")) {
                ignoreCount++;
            } else {
                noGroupCount++; // If no known groups are found
            }
        }

        // Count tests that have no group annotation at all
        if (!matcher.find()) {
            Pattern simpleTestPattern = Pattern.compile("@Test\\s*\\(\\)");
            Matcher simpleMatcher = simpleTestPattern.matcher(content);
            while (simpleMatcher.find()) {
                noGroupCount++;
            }
        }
    }
}
