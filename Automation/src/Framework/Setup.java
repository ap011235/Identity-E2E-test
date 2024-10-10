package Framework;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.AssertJUnit;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static java.time.Duration.ofMillis;

public class Setup {

    String url;
    public static String car_input;
    public static String car_output;
    public static String privateURL;
    public static String username;
    String password;
    public static String browser;
    public static String chromeVersion;
    public static String resolution;
    public static String remoteRun;
    public static String locale;
    public static String docker;
    protected static WebDriver driver;
    public static String documentTitle;
    public static String portalSummary;
    public static String portalDetail;
    public void getProperties() throws Exception {
        Properties props = new Properties();

        props.load(new FileReader("src/config/test.properties"));

//		props.load(new FileReader("/Users/Saif/Documents/IdeaProjects/QA-Automation/Automation/src/config/test.properties"));
        url = props.getProperty("sites.url");
        car_input = props.getProperty("test.inputFile");
        car_output = props.getProperty("test.outputFile");
        username = props.getProperty("test.username");
        password = props.getProperty("test.password");
        browser = props.getProperty("test.browser");
        chromeVersion = props.getProperty("test.ChromeVersion");
        resolution = props.getProperty("test.resolution");
        remoteRun = props.getProperty("test.RunRemotely");
        locale = props.getProperty("test.locale");
        docker = props.getProperty("test.Docker");

        System.setProperty("webdriver.ie.driver", props.getProperty("iedriver.path"));
        System.setProperty("webdriver.chrome.driver", props.getProperty("chromedriver.path"));
        System.setProperty("webdriver.edge.driver", props.getProperty("edgedriver.path"));
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getURL() {
        return url;
    }

    public static void waitFor(By selector) throws InterruptedException {
        //       wait for element to be clickable
        $(selector).shouldBe(appear, Duration.ofSeconds(30));
    }

    public WebDriver launchBrowser(String browser) {    //create launch remote browser for Grid
        switch (browser) {
            case "Chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                options.addArguments("--lang=en");
//                options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
//			options.addArguments("--headless");
//			options.addArguments("--disable-gpu");
//			options.addArguments("--disable-extensions"); 
//			options.addArguments("window-size="+resolution);
//			options.addArguments("--start-maximized");
//			options.addArguments("--disable-infobars");
                options.addArguments("--remote-allow-origins=*");
                Proxy proxyChrome = new Proxy();
                proxyChrome.setAutodetect(true);
                options.setCapability("proxy", proxyChrome);
                Map<String, Object> cloudOptions = new HashMap<>();
                cloudOptions.put("Name", "Chrome");
//			cb.setcapability("unexpectedalertbehaviour", "leave");
//			cb.setcapability("requirewindowfocus", false);
                driver = new ChromeDriver(options);
                break;
            case "Edge":
                Proxy proxyEdge = new Proxy();
                EdgeOptions edgeOptions = new EdgeOptions();
                proxyEdge.setAutodetect(true);
                edgeOptions.setCapability("proxy", proxyEdge);
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
//                Proxy proxyDefault = new Proxy();
//                proxyDefault.setAutodetect(true);
//                cb.setCapability("proxy", proxyDefault);
//                cb.setBrowserName("Chrome");
//                driver = new ChromeDriver(cb);
        }
        //setsize does not seem to be working on Macs. Commenting this section for now, window size is
        //to be managed via addArguments() method (
        if (resolution.equals("1280x1024")) {
            driver.manage().window().setSize(new Dimension(1280, 1024));
        } else if (resolution.equals("1601x1080")) {
            driver.manage().window().setSize(new Dimension(1601, 1080));
        } else if (resolution.equals("1720,1050")) {
            driver.manage().window().setSize(new Dimension(1720, 1050));
        } else if (resolution.equals("1920,1080")) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            driver.manage().window().maximize(); //use chromeoptions
        }
//		driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
        return driver;
    }

    public WebDriver getDriver() {
        return driver;
    }
}