package custom_framework.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RetryLogger {

    private static final String LOG_FILE_PATH = "target/retry-log.csv";

    // Read the existing retry data from the CSV file
    public static Map<String, Integer> readExistingRetryData() {
        Map<String, Integer> existingData = new HashMap<>();
        if (Files.exists(Paths.get(LOG_FILE_PATH))) {
            try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split(",");
                    if (split.length == 2) {
                        String testName = split[0].trim().replace("Test: ", "");
                        int retryCount = Integer.parseInt(split[1].trim().replace("Retries: ", ""));
                        existingData.put(testName, retryCount);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return existingData;
    }

    // Log retry data, updating existing entries if needed
    public static void logRetryData(Map<String, Integer> retryCountMap) {
        Map<String, Integer> existingRetryData = readExistingRetryData();

        // Update retry data
        for (Map.Entry<String, Integer> entry : retryCountMap.entrySet()) {
            String testName = entry.getKey();
            int newRetryCount = entry.getValue();

            // If the test already exists in the CSV, update the retry count
            existingRetryData.put(testName, existingRetryData.getOrDefault(testName, 0) + newRetryCount);
        }

        // Write updated data back to the CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH))) {
            for (Map.Entry<String, Integer> entry : existingRetryData.entrySet()) {
                writer.write("Test: " + entry.getKey() + ", Retries: " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
