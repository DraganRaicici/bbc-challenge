BBC Coding Challenge

# BBC Programming exercise

This project is a handin project for the BBC. It was given to me in order to qualify for the next step in the hiring process. It was initially completed in a weekend, but I later added some changes and fixes.

The requirements of the exercises can be found at the bottom of this README.

## Getting Started

Simply clone or download the project onto your machine.

The project is optimized for use on a Linux machine. I never tried it on a Windows or Mac machine, however, with the right settings, it should work.

### Prerequisites

You will need to have Java 1.8 on your system as well as junit if you want to run the tests. 

The program and tests can be run independently from an IDE in your terminal provided you build the artifact from your IDE.

### Running

Running the program can be simply done from your IDE.

Alternatively, after creating a .jar artifact(remember to include tests!) using the methods for your IDE (mine was IntelliJ IDEA and I used instructions found here: https://stackoverflow.com/a/45303637), by navigating to the directory of the artifact, you can run:

```
java -jar bbc-java.jar
```

You will be prompted with some information as soon as the program starts. Here you can enter as many URLs as you like, separated by a new line.
When the program encouters the keyword “Done” (it is key sensitive), it will start running through them one by one.

At the end, a JSON-style output will be presented with the results.

Warning: An empty new line will be considered a URL and will produce the appropriate message in the JSON response at the end.

## Running the tests

There are 3 test classes (test-to-run) upon which tests can be run:
- com.bbc.CommandLineReaderTest
- com.bbc.TaskTest
- com.bbc.OfflineTaskTest

Note: The command written will be for JUnit 4.X

These can be run sequentially by inputting the following command from the command line, while in the directory of the unzipped file:

```
java -cp /usr/share/java/junit4:bbc-java.jar org.junit.runner.JUnitCore com.bbc.TaskTest
```

## Built With

* [PowerMock](https://github.com/powermock/powermock) - The unit testing framework used


## Authors

* **Dragan Raicici** - [DraganRaicici](https://github.com/DraganRaicici)

## Exercise requirements

The task is to write a program that makes http (and https) requests to specified URLs and to report on certain properties of the responses it receives; see requirements below.

Please write your code in Java, Ruby or Python for a Linux/Unix platform. We will accept other languages but please check with us first.

Your code should be sent to us as a zipped tar archive (.tgz file). We would like to see your version control commit history so please include .git or .svn directory structure in the tar archive. We prefer to see small incremental commits so the order in which your solution is developed is apparent.

Please provide instructions so that we can install, test and run your program.

If we invite you for an interview we will ask you to modify your program to meet an additional requirement or make
other improvements.

Main Requirements
• The program is invoked from the command line and it consumes its input from stdin.
• The program output is written to stdout and errors are written to stderr.
• Input format is a newline separated list of public web addresses, such as

http://www.bbc.co.uk/iplayer
https://google.com
bad://address
http://www.bbc.co.uk/missing/thing
http://not.exists.bbc.co.uk/
http://www.oracle.com/technetwork/java/javase/downloads/index.html
https://www.pets4homes.co.uk/images/articles/1646/large/kitten-emergencies-signs-to-look-out-for-537479947ec1c.jpg
http://site.mockito.org/

• The program should make an http GET request to each valid address in its input and record particular properties of the http response in the program output.
• The properties of interest are status code, content length and date-time of the response. These are normally available in the http response headers.
• Output is a stream of JSON format documents that provide information about the http response for each address in the input, such as
{
"Url": "http://www.bbc.co.uk/iplayer",
"Status_code": 200,
"Content_length": 209127,
"Date": "Tue, 25 Jul 2017 17:00:55 GMT"
}
{
"Url": "https://google.com",
"Status_code": 302,
"Content_length": 262,
"Date": "Tue, 25 Jul 2017 17:00:55 GMT"
}
{
"Url": "bad://address",
"Error": "invalid url"
}

• The program should identify and report invalid URLs, e.g. those that don't start with http:// or https://, or contain characters not allowed in a URL.
• The program should cope gracefully when it makes a request to a slow or non-responsive web server, e.g. it could time out the request after ten seconds.
• The program should have a good set of unit tests.
• It must be possible to perform a test run, consisting of all unit tests, without accessing the Internet.




