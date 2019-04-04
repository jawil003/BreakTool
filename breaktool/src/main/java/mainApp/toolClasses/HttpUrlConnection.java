package mainApp.toolClasses;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class HttpUrlConnection {

    private static HttpUrlConnection connection;
    private static WebDriver driver;
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
        driver.quit();
        driver = new SafariDriver();
        driver.manage().window().fullscreen();
        driver.get(url);
    }

    public void setUsernameAndPasswordCSS(String username, String password, int Website) throws InterruptedException {
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.cssSelector("input#user[name='pw_usr']"))).sendKeys(username);
            driver.findElement(By.cssSelector("input#pw_pwd[name='pw_pwd']")).sendKeys(password);
            driver.findElement(By.cssSelector("input.button.standard_button_size.large#pw_submit")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUsernameAndPasswordXPath(String username, String password, int Website) throws InterruptedException {
        new WebDriverWait(driver, 20).until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='user' and @name='pw_usr']"))).sendKeys("team52@t-online.de");
        driver.findElement(By.xpath("//input[@id='pw_pwd' and @name='pw_pwd']")).sendKeys("YC2018$tudent!");
        driver.findElement(By.xpath("//input[@class='button standard_button_size large' and @id='pw_submit']")).click();
    }

    public void disable() {
        if (driver != null)
        driver.quit();
    }



}

