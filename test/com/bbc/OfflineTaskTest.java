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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({URL.class, Task.class})
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

    @Test
    public void requestingContentLengthFromValidAddressShouldReturnWithExpectedResult() throws Exception {

        String REQUEST_URL = "http://www.bbc.co.uk/iplayer";
        String contentLength = "216880";
        String date = System.currentTimeMillis()+"";

        Map headers = new HashMap<String, List<String>>();
        headers = makeHeaderFields("Content-Length", contentLength, headers);
        headers = makeHeaderFields("Date", date, headers);


        URL url = PowerMockito.mock(URL.class);
        HttpURLConnection connection = PowerMockito
                .mock(HttpURLConnection.class);
        PowerMockito.whenNew(URL.class).withArguments(REQUEST_URL)
                .thenReturn(url);
        PowerMockito.when(url.openConnection()).thenReturn(connection);

        // Response mocked here

        PowerMockito.when(connection.getHeaderFields()).thenReturn(headers);

        task = new Task(REQUEST_URL);
        task.run();

        assertThat(outContent.toString(), containsString(contentLength));

    }

    @Test
    public void requestingStatusCodeFromValidAddressShouldReturnWithExpectedResult() throws Exception {

        String REQUEST_URL = "http://site.mockito.org/";
        String expectedStatusCode = "200";
        String contentLength = "216880";
        String date = System.currentTimeMillis()+"";

        Map headers = new HashMap<String, List<String>>();

        headers = makeHeaderFields("Content-Length", contentLength, headers);
        headers = makeHeaderFields("Date", date, headers);
        headers = makeHeaderFields("Url", REQUEST_URL, headers);

        URL url = PowerMockito.mock(URL.class);
        HttpURLConnection connection = PowerMockito
                .mock(HttpURLConnection.class);
        PowerMockito.whenNew(URL.class).withArguments(REQUEST_URL)
                .thenReturn(url);
        PowerMockito.when(url.openConnection()).thenReturn(connection);

        //Response mocked here
        PowerMockito.when(connection.getResponseCode()).thenReturn(200);
        PowerMockito.when(connection.getHeaderFields()).thenReturn(headers);

        task = new Task(REQUEST_URL);
        task.run();

        assertThat(outContent.toString(), containsString(expectedStatusCode));
    }

    @Test
    public void handleNoContentLengthFromValidAddressShouldReturnWithExpectedResult() throws Exception {

        String REQUEST_URL = "https://google.com";
        String contentLength = "Content-Length missing";
        String date = System.currentTimeMillis()+"";

        Map headers = new HashMap<String, List<String>>();

        headers = makeHeaderFields("Date", date, headers);
        headers = makeHeaderFields("Url", REQUEST_URL, headers);

        URL url = PowerMockito.mock(URL.class);
        HttpURLConnection connection = PowerMockito
                .mock(HttpURLConnection.class);
        PowerMockito.whenNew(URL.class).withArguments(REQUEST_URL)
                .thenReturn(url);
        PowerMockito.when(url.openConnection()).thenReturn(connection);

        //Response mocked here
        PowerMockito.when(connection.getResponseCode()).thenReturn(200);
        PowerMockito.when(connection.getHeaderFields()).thenReturn(headers);

        task = new Task(REQUEST_URL);
        task.run();

        assertThat(outContent.toString(), containsString(contentLength));
    }

    @Test
    public void requestingInfoFromValidAddressShouldReturnWithExpectedValues() throws Exception {

        String REQUEST_URL = "http://www.bbc.co.uk/iplayer";
        String expectedUrl = "http://www.bbc.co.uk/iplayer";
        String expectedStatusCode = "200";
        String expectedContentLength = "216880";
        String date = System.currentTimeMillis()+"";

        Map headers = new HashMap<String, List<String>>();

        headers = makeHeaderFields("Date", date, headers);
        headers = makeHeaderFields("Url", REQUEST_URL, headers);
        headers = makeHeaderFields("Content-Length", expectedContentLength, headers);

        URL url = PowerMockito.mock(URL.class);
        HttpURLConnection connection = PowerMockito
                .mock(HttpURLConnection.class);
        PowerMockito.whenNew(URL.class).withArguments(REQUEST_URL)
                .thenReturn(url);
        PowerMockito.when(url.openConnection()).thenReturn(connection);

        //Response mocked here
        PowerMockito.when(connection.getResponseCode()).thenReturn(200);
        PowerMockito.when(connection.getHeaderFields()).thenReturn(headers);

        task = new Task(REQUEST_URL);
        task.run();

        assertThat(outContent.toString(), containsString(expectedContentLength));
        assertThat(outContent.toString(), containsString(expectedStatusCode));
        assertThat(outContent.toString(), containsString(expectedUrl));
    }

    @Test
    public void requestingInfoFromInValidAddressShouldReturnWithExpectedValues() throws Exception {

        String REQUEST_URL = "http://www.bbc.co.uk/missing/thing";
        String expectedStatusCode = "404";
        String date = System.currentTimeMillis()+"";

        Map headers = new HashMap<String, List<String>>();

        headers = makeHeaderFields("Date", date, headers);
        headers = makeHeaderFields("Url", REQUEST_URL, headers);

        URL url = PowerMockito.mock(URL.class);
        HttpURLConnection connection = PowerMockito
                .mock(HttpURLConnection.class);
        PowerMockito.whenNew(URL.class).withArguments(REQUEST_URL)
                .thenReturn(url);
        PowerMockito.when(url.openConnection()).thenReturn(connection);

        //Response mocked here
        PowerMockito.when(connection.getResponseCode()).thenReturn(404);
        PowerMockito.when(connection.getHeaderFields()).thenReturn(headers);

        task = new Task(REQUEST_URL);
        task.run();

        assertThat(outContent.toString(), containsString(expectedStatusCode));
        assertThat(outContent.toString(), containsString(REQUEST_URL));
    }


    /**
     * Helper method to create the header map.
     * @param header
     * @param value
     * @param headerFields
     * @return A map containing the header as the key and the value as content in a List.
     */
    private Map<String, List<String>> makeHeaderFields(String header, String value, Map<String, List<String>> headerFields ) {
        List<String> contentList = new ArrayList<>();
        contentList.add(value);
        headerFields.put(header, contentList);

        return headerFields;
    }

}
