package com.optus.textsearch.controller;

import com.optus.textsearch.endpoints.TextSearchResource;
import com.optus.textsearch.helper.FileHelper;
import com.optus.textsearch.models.SearchInput;
import com.optus.textsearch.models.TopOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
@Controller
public class TextSearchController implements TextSearchResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(TextSearchController.class);
    @Autowired
    private FileHelper fileHelper;
    @Value("${sample.input.file}")
    private String inputFileName;
    @Value("${sample.output.file}")
    private String outputFileName;

    /**
     * This method implements search endpoint
     *
     * @param input
     * @return
     */
    @Override
    public Response search(SearchInput input) {
        Map<String, Integer> countMap = new HashMap<>();
        LOGGER.info("Received search request for: " + input.getSearchText());
        try {
            getWordCount(countMap);
            LOGGER.info("Parsed file with word countMap:" + countMap.entrySet().size());
            return Response.status(201).entity(new HashMap<String, List<Map<String, Integer>>>() {
                {
                    put("counts", getFoundWordList(input));
                }

                private List<Map<String, Integer>> getFoundWordList(SearchInput input) {
                    List<Map<String, Integer>> list = new ArrayList<>();
                    input.getSearchText().forEach(word -> list.add(new HashMap<String, Integer>() {{
                        put(word, countMap.get(word.toLowerCase()) != null ? countMap.get(word.toLowerCase()) : new Integer(0));
                    }}));
                    return list;
                }
            }).build();

        } catch (IOException e) {
            LOGGER.error("Encountered exception while doing search" + e);
            return Response.status(500).entity(e.fillInStackTrace()).build();
        }
    }

    private void getWordCount(Map<String, Integer> countMap) throws IOException {
        fileHelper.mapFileToStringTokens(inputFileName).stream().
                forEach(word -> {
                    if (countMap.containsKey(word))
                        countMap.put(word, countMap.get(word) + 1);
                    else
                        countMap.put(word, new Integer(1));
                });
    }

    /**
     * This is the implementation of search top end point
     *
     * @param id
     * @return
     */
    @Override
    public Response searchTop(int id) {
        LOGGER.info("Received request for search Top");
        List<TopOutput> topOutputList;
        Map<String, Integer> wordCountMap = new HashMap<>();
        try {
            getWordCount(wordCountMap);
            topOutputList = wordCountMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(id).
                    map(entry -> new TopOutput(entry.getKey(), entry.getValue())).collect(Collectors.toList());
            fileHelper.writeDataToCSV(topOutputList, outputFileName);
            return Response.status(201).entity(new File(outputFileName)).build();
        } catch (IOException e) {
            LOGGER.error("Encountered exception while doing search top" + e);
            return Response.status(500).entity(e).build();
        }
    }
}


