package tests;

import Framework.*;
import org.openqa.selenium.WebDriver;
import Framework.WBAC.*;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.NoSuchElementException;


import static com.codeborne.selenide.Selenide.open;

public class MainTests extends Setup {

    WebDriver driver;
    String url;
    List<String> plates = RegExtraction.extractCarPlates("Resources/car_input.txt");


    @BeforeClass
    public void setup() throws Exception {
        Setup setup = new Setup();
        setup.getProperties();
        setup.launchBrowser(browser);
        driver = setup.getDriver();
        WebDriverRunner.setWebDriver(driver);
        url = "https://www.webuyanycar.com";


        open(url);
        WBACNavigation.clickAcceptCookies();

    }

    @Test(priority = 1)
    public void extractRegistractionPlates() throws Exception {
        //RegExtraction.extractPlate("Resources/car_input.txt");

        for (String plate : plates) {
            plate = plate.replaceAll("\\s", "");
            System.out.println(plate);
        }
    }

    @Test(priority = 2)
    public void compareToOutputFIle() throws Exception {

        for (String plate : plates) {

            plate = plate.replaceAll("\\s", "");
            String fullDetails = RegExtraction.getFullDetails(plate);
            System.out.println(fullDetails);
            RegExtraction.compareWithOutput(plate, fullDetails);

            Thread.sleep(3000);
            open(url);
        }
    }

    @AfterClass
    public void afterClass() throws Exception {
        System.out.println("Reached After Class Method");
        driver.quit();
    }

}
