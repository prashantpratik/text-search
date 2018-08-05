package com.optus.textsearch.helper;

import com.optus.textsearch.models.TopOutput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileHelperTest {
    @InjectMocks
    private FileHelper fileHelper;
    @Value("${sample.input.file}")
    private String inputFileName;
    @Value("${sample.output.file}")
    private String outputFileName;

    @Before
    public void setup() {
    }

    @Test
    public void testMapFilesToString() throws IOException {
        List<String> employeeList = fileHelper.mapFileToStringTokens(inputFileName);
        assertEquals(911, employeeList.size());
    }

    @Test
    public void testWriteToFile() throws IOException {
        List<TopOutput> topOutputList = new ArrayList<>();
        topOutputList.add(new TopOutput("test1", 10));
        fileHelper.writeDataToCSV(topOutputList, outputFileName);
        assertNotNull(Files.lines(Paths.get(outputFileName)));
    }

    @Test(expected = IOException.class)
    public void testMapFilesToStringFailure() throws IOException {
        List<String> employeeList = fileHelper.mapFileToStringTokens("");
        assertEquals(911, employeeList.size());
    }

    @Test(expected = IOException.class)
    public void testWriteToFileFailure() throws IOException {
        List<TopOutput> topOutputList = new ArrayList<>();
        topOutputList.add(new TopOutput("test1", 10));
        fileHelper.writeDataToCSV(topOutputList, "");
    }
}
