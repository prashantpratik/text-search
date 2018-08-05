package com.optus.textsearch.helper;


import com.optus.textsearch.models.TopOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * File helper class
 * Reads and write operation
 */
@Service
public class FileHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

    /**
     * This method reads the input file and return words as list of string
     *
     * @param fileName
     * @return
     * @throws IOException
     */

    public List<String> mapFileToStringTokens(String fileName) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream.map(line -> line.split("\\s+"))
                    .flatMap(Arrays::stream).map(e -> e.replaceAll("[^A-Za-z0-9 ]", "")).
                            filter(w -> w.matches("\\w+")).map(String::toLowerCase)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOGGER.error("Unable to read file: " + fileName);
            throw e;
        }
    }

    /**
     * This method writes data to csv file
     *
     * @param outputList
     * @param fileName
     * @throws IOException
     */
    public void writeDataToCSV(List<TopOutput> outputList, String fileName) throws IOException {
        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(fileName)))) {
            outputList.stream().map(TopOutput::toString).forEach(pw::println);
            LOGGER.info("Successfully wrote output to file: " + fileName);
        } catch (IOException e) {
            LOGGER.error("Exception while generating csv file");
            throw e;
        }
    }

}
