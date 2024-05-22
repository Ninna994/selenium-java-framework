package custom_framework.utils.frameworkLinters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestFileChecker {

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
            checkTestAnnotations(file, content);
            checkMethodSorting(file, content);
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

    private static void checkTestAnnotations(File file, String content) {
        Pattern testPattern = Pattern.compile("@Test\\s*\\(\\s*groups\\s*=\\s*\\{[^}]+}\\s*\\)");
        Matcher matcher = testPattern.matcher(content);

        List<String> errors = new ArrayList<>();
        while (matcher.find()) {
            String annotation = matcher.group();
            if (!annotation.contains("groups={")) {
                errors.add("Missing groups in @Test annotation: " + annotation);
            }
        }

        if (!errors.isEmpty()) {
            System.out.println("Issues found in file: " + file.getAbsolutePath());
            for (String error : errors) {
                System.out.println(" - " + error);
            }
        }
    }

    private static void checkMethodSorting(File file, String content) {
        List<String> methodNames = extractMethodNames(content);
        List<String> unsortedMethods = findUnsortedMethods(methodNames);

        if (!unsortedMethods.isEmpty()) {
            System.out.println("Methods not sorted alphabetically in file: " + file.getAbsolutePath());
            System.out.println("Unsorted methods: " + unsortedMethods);
        }
    }

    private static List<String> extractMethodNames(String content) {
        String methodRegex = "\\b(public|protected|private|static|final|native|synchronized|abstract|threadsafe|transient)\\s+\\w+\\s+(\\w+)\\s*\\([^)]*\\)\\s*\\{";
        return Pattern.compile(methodRegex)
                .matcher(content)
                .results()
                .map(matchResult -> matchResult.group(2))
                .collect(Collectors.toList());
    }

    private static List<String> findUnsortedMethods(List<String> methodNames) {
        List<String> unsortedMethods = new ArrayList<>();
        for (int i = 0; i < methodNames.size() - 1; i++) {
            if (methodNames.get(i).compareTo(methodNames.get(i + 1)) > 0) {
                unsortedMethods.add(methodNames.get(i + 1));
            }
        }
        return unsortedMethods;
    }
}
