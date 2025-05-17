package com.Assignment.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.Objects;

public class BrowserManager {
    static WebDriver driver;

    public static WebDriver getDriver(String type, String url) throws InterruptedException {
        if(Objects.equals(type, "Chrome")){
            driver = new ChromeDriver();
        }else if(Objects.equals(type, "FireFox")){
            driver = new FirefoxDriver();
        }else if(Objects.equals(type, "Safari")){
            driver = new SafariDriver();
        }else {
            Assert.assertEquals(false,"Browser Type not given correctly");
        }
        driver.manage().window().maximize();
        driver.get(url);
        Reporter.log("Driver Launched : "+ type + "\nURL : "+driver.getTitle(),true);
        Thread.sleep(2000);
        return driver;
    }
}
