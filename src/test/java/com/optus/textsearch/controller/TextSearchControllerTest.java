package com.optus.textsearch.controller;

import com.optus.textsearch.helper.FileHelper;
import com.optus.textsearch.models.SearchInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TextSearchControllerTest {
    @Mock
    private FileHelper helper;
    @Value("${sample.input.file}")
    private String inputFileName;
    @Value("${sample.output.file}")
    private String outputFileName;
    @InjectMocks
    private TextSearchController controller;
    private String[] inputArray = {"test1", "test2"};

    @Before
    public void setup() throws IOException {
        when(helper.mapFileToStringTokens(Mockito.anyString())).thenReturn(Arrays.asList(inputArray));
        Mockito.doNothing().when(helper).writeDataToCSV(Mockito.anyList(), Mockito.anyString());
        ReflectionTestUtils.setField(controller, "outputFileName", outputFileName);
    }

    @Test
    public void testSearch() {
        SearchInput input = new SearchInput();
        input.setSearchText(Arrays.asList(inputArray));
        assertEquals(201, controller.search(input).getStatus());
    }

    @Test
    public void testSearchTop() {
        assertEquals(201, controller.searchTop(2).getStatus());
    }

    @Test
    public void testSearchTop1() {
        ReflectionTestUtils.setField(controller, "outputFileName", "");
        assertEquals(201, controller.searchTop(2).getStatus());
    }
}
