package com.bbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({HttpURLConnection.class})
public class OfflineTaskTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Mock
    private HttpURLConnection huc;
    private URL u;

    @InjectMocks
    private Task task;

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    private Map<String, List<String>> makeMap(String header, String value){
        List<String> myList = new ArrayList<>();
        myList.add(value);
        Map<String, List<String>> myMap = new HashMap<>();
        myMap.put(header,myList);

        return myMap;
    }

    @Test
    public void getResponse_NormalResponse() throws Exception {

        String expectedResponse = "This is the expected response text!";
        String contentLength = "216880";
        URL url = PowerMockito.mock(URL.class);
        HttpURLConnection connection = PowerMockito
                .mock(HttpURLConnection.class);
        InputStream inputStream = PowerMockito.mock(InputStream.class);
        Scanner scanner = PowerMockito.mock(Scanner.class);

        String REQUEST_URL = "http://www.bbc.co.uk/iplayer";
        PowerMockito.whenNew(URL.class).withArguments(REQUEST_URL)
                .thenReturn(url);
        PowerMockito.whenNew(Scanner.class).withArguments(inputStream)
                .thenReturn(scanner);
        PowerMockito.when(url.openConnection()).thenReturn(connection);

        // Response code mocked here
        PowerMockito.when(connection.getResponseCode()).thenReturn(200);

        Task task = new Task(REQUEST_URL);
        task.run();

        assertThat(outContent.toString(), containsString(contentLength));

    }

}
