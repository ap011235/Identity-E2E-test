package Framework.WBAC;

import Framework.Setup;
import org.openqa.selenium.*;

import static com.codeborne.selenide.Selenide.$;

public class WBACNavigation extends Setup{

    public static void clickAcceptCookies() throws InterruptedException {
        Setup.waitFor(By.id("onetrust-accept-btn-handler"));
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
    }

    public static void inputRegNumber(String regnumber) throws InterruptedException {
        Setup.waitFor(By.id("vehicleReg"));
        driver.findElement(By.id("vehicleReg")).sendKeys(regnumber);
    }

    public static void inputMileage(String mileage) throws InterruptedException {
        Setup.waitFor(By.id("Mileage"));
        driver.findElement(By.id("Mileage")).sendKeys(mileage);
    }

    public static void getValuation() throws InterruptedException {
        Setup.waitFor(By.id("btn-go"));
        driver.findElement(By.id("btn-go")).click();
    }

    public static void typeEmaill() throws InterruptedException {
        Setup.waitFor(By.id("EmailAddress"));
        driver.findElement(By.id("EmailAddress")).sendKeys("cvtest@testmail.com");
    }

    public static void typePostCode() throws InterruptedException {
        Setup.waitFor(By.id("Postcode"));
        driver.findElement(By.id("Postcode")).sendKeys("EC4R9AT");
    }

    public static void getValuation2() throws InterruptedException {
        Setup.waitFor(By.id("advance-btn"));
        driver.findElement(By.id("advance-btn")).click();
    }

    public static String getValuationPrice() throws InterruptedException {
        Setup.waitFor(By.xpath("//div[@id='wbac-app-container']/div/div/valuation/section[2]/div/div/div/div/div/div/div/div"));
        String valuationPrice = driver.findElement(By.xpath("//div[@id='wbac-app-container']/div/div/valuation/section[2]/div/div/div/div/div/div/div/div")).getText();
        return valuationPrice;
    }

    public static String getPlateNS() throws InterruptedException {
        try {
            Setup.waitFor(By.xpath("/html/body/div[1]/wbac-app/div[1]/div/div/vehicle-questions/div/section[1]/div/div[1]/div/div[3]/div/vehicle-details/div[3]/div[1]/div[2]"));
            String carPlate = driver.findElement(By.xpath("/html/body/div[1]/wbac-app/div[1]/div/div/vehicle-questions/div/section[1]/div/div[1]/div/div[3]/div/vehicle-details/div[3]/div[1]/div[2]")).getText();
            return carPlate;
        }
        catch (TimeoutException exception) {
            System.out.println("Plate New not found");
            return "";
        }

    }

    public static String getMake() throws InterruptedException {
            Setup.waitFor(By.xpath("//div[@id='wbac-app-container']/div/div/vehicle-questions/div/section/div/div/div/div[3]/div/vehicle-details/div[3]/div[2]/div/div[2]"));
            String carMake = driver.findElement(By.xpath("//div[@id='wbac-app-container']/div/div/vehicle-questions/div/section/div/div/div/div[3]/div/vehicle-details/div[3]/div[2]/div/div[2]")).getText();
            return carMake;
    }

    public static String getModel() throws InterruptedException {
            Setup.waitFor(By.xpath("//div[@id='wbac-app-container']/div/div/vehicle-questions/div/section/div/div/div/div[3]/div/vehicle-details/div[3]/div[2]/div[2]/div[2]"));
            String carModel = driver.findElement(By.xpath("//div[@id='wbac-app-container']/div/div/vehicle-questions/div/section/div/div/div/div[3]/div/vehicle-details/div[3]/div[2]/div[2]/div[2]")).getText();
            return carModel;
    }

    public static String getYear() throws InterruptedException {
            Setup.waitFor(By.xpath("//div[@id='wbac-app-container']/div/div/vehicle-questions/div/section/div/div/div/div[3]/div/vehicle-details/div[3]/div[2]/div[3]/div[2]"));
            String carYear = driver.findElement(By.xpath("//div[@id='wbac-app-container']/div/div/vehicle-questions/div/section/div/div/div/div[3]/div/vehicle-details/div[3]/div[2]/div[3]/div[2]")).getText();
            return carYear;
    }

}
