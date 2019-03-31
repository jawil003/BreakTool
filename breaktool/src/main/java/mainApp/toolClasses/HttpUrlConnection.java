package mainApp.toolClasses;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class HttpUrlConnection {

    private static HttpUrlConnection connection;
    private WebDriver driver;
    private HttpUrlConnection() {

    }

    public static HttpUrlConnection getInstance() {
        if (connection == null)
            connection = new HttpUrlConnection();
        return connection;
    }

    public String exportDriver() throws IOException {
        final InputStream IEDriverStream = getClass().getResourceAsStream("/Driver/IEDriverServer.exe");
        final File ieDriverServer = FileWebOpener.stream2file(IEDriverStream, "IEDriverServer", ".exe");
        System.setProperty("webdriver.ie.driver", ieDriverServer.getAbsolutePath());
        return ieDriverServer.getAbsolutePath();
    }

    public void goToWebsite(String url) {
        driver = new InternetExplorerDriver();
        driver.get(url);
    }

    public void setUsernameAndPassword(String username, String password) {
        driver.findElement(By.id("user")).sendKeys(username);
        driver.findElement(By.id("pw_pwd")).sendKeys(password);
        driver.quit();
    }




}

