package mainApp.toolClasses;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpUrlConnection {

    private static HttpUrlConnection connection;

    private HttpUrlConnection() {

    }

    public static HttpUrlConnection getInstance() {
        if (connection == null)
            connection = new HttpUrlConnection();
        return connection;
    }

    public String exportDriver() throws IOException {
        final InputStream IEDriverStream = getClass().getResourceAsStream("/Driver/IEDriverServer.exe");
        final Path ieDriverServer = Files.createTempFile("IEDriverServer", ".exe", null);
        System.setProperty("webdriver.ie.driver", ieDriverServer.toString());
        return ieDriverServer.toAbsolutePath().toString();
    }

    public void goToWebsite(String url) {
        WebDriver driver = new InternetExplorerDriver();
        driver.get(url);
    }


}

