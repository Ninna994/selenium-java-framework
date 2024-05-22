package custom_framework.utils.frameworkLinters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AACheckSorting {
    public static void main(String[] args) {
        String filePath =System.getProperty("user.dir") +  "\\src\\main\\java\\custom_framework\\utils\\SharedMethods.java";
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            String content = String.join("\n", lines);

            // Split content into segments
            List<String> segments = splitIntoSegments(content);

            // Check each segment for alphabetical order
            for (int i = 0; i < segments.size(); i++) {
                String segment = segments.get(i);
                List<String> methodNames = extractMethodNames(segment);
                List<String> unsortedMethods = findUnsortedMethods(methodNames);

                if (unsortedMethods.isEmpty()) {
                    System.out.println("Methods in segment " + (i + 1) + " are sorted alphabetically.");
                } else {
                    System.out.println("Methods in segment " + (i + 1) + " are NOT sorted alphabetically.");
                    System.out.println("Unsorted methods: " + unsortedMethods);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Step 1: Split content into segments
    private static List<String> splitIntoSegments(String content) {
        // Regex to match the specific section divider comment
        String regex = "(?=/\\*\\s*\\n?\\s*\\*\\s*-{20,}.*?\\n?\\s*\\*/)";
        String[] segments = content.split(regex);
        List<String> segmentList = new ArrayList<>();
        for (String segment : segments) {
            segmentList.add(segment.trim());
        }
        return segmentList;
    }

    // Step 2: Extract Method Names
    private static List<String> extractMethodNames(String content) {
        // Regex to match method signatures (public, private, protected, default)
        String methodRegex = "\\b(public|protected|private|static|final|native|synchronized|abstract|threadsafe|transient)\\s+\\w+\\s+\\w+\\s*\\([^)]*\\)\\s*\\{";
        return Pattern.compile(methodRegex)
                .matcher(content)
                .results()
                .map(matchResult -> {
                    String methodSignature = matchResult.group();
                    return extractMethodNameFromSignature(methodSignature);
                })
                .collect(Collectors.toList());
    }

    private static String extractMethodNameFromSignature(String methodSignature) {
        // Regex to extract the method name from the signature
        String nameRegex = "\\w+\\s+(\\w+)\\s*\\(";
        Matcher matcher = Pattern.compile(nameRegex).matcher(methodSignature);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    // Step 3: Find All Unsorted Methods
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
