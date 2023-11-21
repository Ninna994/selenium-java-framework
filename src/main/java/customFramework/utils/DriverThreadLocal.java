package customFramework.utils;

import org.openqa.selenium.WebDriver;

public class DriverThreadLocal extends InheritableThreadLocal<WebDriver> {

    private static DriverThreadLocal instance = new DriverThreadLocal();

    public static WebDriver getDriver() {
        return instance.get();
    }

    public static void setDriver(WebDriver driver) {
        instance.set(driver);
    }

}