package com.bbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TaskTest {

    protected HttpURLConnection mHttpURLConnectionMock;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Before
    public void setUp() throws Exception {
        mHttpURLConnectionMock = Mockito.mock(HttpURLConnection.class);
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void requestingStatusFromValidAvailableAddressShouldReturnOk() throws Exception {
        Mockito.when(mHttpURLConnectionMock.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        String validSite = "http://google.com";
        List<String> addresses = new ArrayList<>();
        addresses.add(validSite);
        ThreadStarter ts = ThreadStarter.getInstance();
        ts.start(addresses);

        assertEquals("200", outContent.toString().trim());
    }
    @Test
    public void requestingStatusFromInValidAddressShouldReturnNotFound() throws Exception {
        Mockito.when(mHttpURLConnectionMock.getResponseCode()).thenReturn(HttpURLConnection.HTTP_NOT_FOUND);
        String validSite = "http://www.bbc.co.uk/missing/thing";
        List<String> addresses = new ArrayList<>();
        addresses.add(validSite);
        ThreadStarter ts = ThreadStarter.getInstance();
        ts.start(addresses);

        assertEquals("404", outContent.toString().trim());
    }

}
