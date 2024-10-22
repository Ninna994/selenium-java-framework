package custom_framework.utils.framework_linters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestGroupCounterByClass {

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
            countTestGroups(file.getName(), content);
        }
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

    private static void countTestGroups(String fileName, String content) {
        int totalCount = 0;
        int smokeTestCount = 0;
        int regressionTestCount = 0;
        int deprecatedCount = 0;
        int cleanupCount = 0;
        int ignoreCount = 0;
        int noGroupCount = 0;

        // Updated regex to match @Test annotation with groups
        Pattern testPattern = Pattern.compile("@Test\\s*\\(.*groups\\s*=\\s*\\{([^}]+)\\}.*\\)");
        Matcher matcher = testPattern.matcher(content);

        while (matcher.find()) {
            totalCount++; // Count this @Test annotation
            String groupContent = matcher.group(1); // Get the captured group names

            // Split group names by comma and trim whitespace
            String[] groups = groupContent.split(",");
            for (String group : groups) {
                String trimmedGroup = group.trim().replace("\"", ""); // Remove quotes
                switch (trimmedGroup) {
                    case "SmokeTest" -> smokeTestCount++;
                    case "RegressionTest" -> regressionTestCount++;
                    case "Deprecated" -> deprecatedCount++;
                    case "Cleanup" -> cleanupCount++;
                    case "Ignore" -> ignoreCount++;
                    default -> noGroupCount++; // If no known groups are found
                }
            }
        }

        // Count tests that have no group annotation at all
        Pattern simpleTestPattern = Pattern.compile("@Test\\s*\\(\\)");
        Matcher simpleMatcher = simpleTestPattern.matcher(content);
        while (simpleMatcher.find()) {
            totalCount++; // Count this @Test annotation
            noGroupCount++;
        }

        // Print results for the current file
        System.out.println("Results for " + fileName + ":");
        System.out.println(" - Total @Test count: " + totalCount);
        System.out.println(" - SmokeTest count: " + smokeTestCount);
        System.out.println(" - RegressionTest count: " + regressionTestCount);
        System.out.println(" - Deprecated count: " + deprecatedCount);
        System.out.println(" - Cleanup count: " + cleanupCount);
        System.out.println(" - Ignore count: " + ignoreCount);
        System.out.println(" - No group count: " + noGroupCount);
        System.out.println();
    }
}
