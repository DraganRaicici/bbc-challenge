package com.bbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.*;


public class TaskTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }


    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void requestingInfoFromValidAddressShouldReturnWithExpectedValues() {

        String validSite = "http://www.bbc.co.uk/iplayer";
        String expectedUrl = "http://www.bbc.co.uk/iplayer";
        String expectedStatusCode = "301";
        String expectedContentLength = "114";

        List<String> addresses = new ArrayList<>();
        addresses.add(validSite);

        ThreadStarter ts = ThreadStarter.getInstance();
        ts.start(addresses);

        assertThat(outContent.toString(), containsString(expectedContentLength));
        assertThat(outContent.toString(), containsString(expectedStatusCode));
        assertThat(outContent.toString(), containsString(expectedUrl));
    }

    @Test
    public void requestingInfoFromInValidAddressShouldReturnWithExpectedValues() {

        String invalidSite = "http://www.bbc.co.uk/missing/thing";
        String expectedUrl = "http://www.bbc.co.uk/missing/thing";
        String expectedStatusCode = "404";

        List<String> addresses = new ArrayList<>();
        addresses.add(invalidSite);

        ThreadStarter ts = ThreadStarter.getInstance();
        ts.start(addresses);

        assertThat(outContent.toString(), containsString(expectedStatusCode));
        assertThat(outContent.toString(), containsString(expectedUrl));
    }

    @Test
    public void requestingInfoFromValidAddressShouldWorkForBothValidProtocols() {

        String httpString = "http://www.bbc.co.uk/missing/thing";
        String httpsString = "https://google.com";
        List<String> addresses = new ArrayList<>();
        addresses.add(httpsString);
        addresses.add(httpString);
        ThreadStarter ts = ThreadStarter.getInstance();
        ts.start(addresses);

        assertThat(outContent.toString(), containsString(httpsString));
        assertThat(outContent.toString(), containsString(httpString));
    }

    @Test
    public void requestingStatusFromValidAddressShouldWorkForBothValidProtocols() {

        String httpString = "http://www.bbc.co.uk/missing/thing";
        String httpsString = "https://google.com";
        List<String> addresses = new ArrayList<>();
        addresses.add(httpsString);
        addresses.add(httpString);
        ThreadStarter ts = ThreadStarter.getInstance();
        ts.start(addresses);

        assertThat(outContent.toString(), containsString(httpsString));
        assertThat(outContent.toString(), containsString(httpString));
    }

    @Test
    public void requestingInfoFromBadUrlShouldBeHandledAccordingly() {

        String badUrl = "bad://address";
        String expectedError = "Malformed url";

        List<String> addresses = new ArrayList<>();
        addresses.add(badUrl);
        ThreadStarter ts = ThreadStarter.getInstance();
        ts.start(addresses);

        assertThat(outContent.toString(), containsString(badUrl));
        assertThat(outContent.toString(), containsString(expectedError));
        assertThat(errContent.toString(), containsString("Malformed URL"));
    }

    @Test
    public void requestingInfoFromNonExistentUrlShouldBeHandledAccordingly() {

        String nonExistentUrl = "http://not.exists.bbc.co.uk/";
        String expectedError = "Non-existent URL";

        List<String> addresses = new ArrayList<>();
        addresses.add(nonExistentUrl);
        ThreadStarter ts = ThreadStarter.getInstance();
        ts.start(addresses);

        assertThat(outContent.toString(), containsString(nonExistentUrl));
        assertThat(outContent.toString(), containsString(expectedError));
        assertThat(errContent.toString(), containsString("Url does not exist"));
    }


    @Test
    public void requestingInfoFromSlowSourcesShouldTimeout() {

        String slowUrl = "http://deelay.me/20000/http://mysite.com/image.gif";
        String expectedError = "Request timed out automatically after 10 seconds";

        List<String> addresses = new ArrayList<>();
        addresses.add(slowUrl);
        ThreadStarter ts = ThreadStarter.getInstance();
        ts.start(addresses);

        assertThat(outContent.toString(), containsString(slowUrl));
        assertThat(outContent.toString(), containsString(expectedError));
        assertThat(errContent.toString(), containsString("Request automatically timed out"));
    }
}
