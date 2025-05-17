package com.Assignment.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PageObjectFlipkart {
    static WebDriver driver = null;
    static WebElement searchBox;
    static WebElement searchButton;
    public PageObjectFlipkart(WebDriver driver){
        PageObjectFlipkart.driver = driver;
        SetLocators();
    }
    public void SetLocators(){
        searchBox = driver.findElement(By.name("q"));
        searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
    }
    public void addMultipleItemtoCart(String args) throws InterruptedException {
        searchProduct(args);
        By products = By.xpath("(//div[@class='_13oc-S']/child::div)");
        List<WebElement> elements = driver.findElements(products);
        int count = 0;
        for(WebElement element : elements){
            AddToCart(element);
            count++;
            if(count == 3){
                break;
            }
        }
        Thread.sleep(2000);

    }
    public void navigateToCart() throws InterruptedException {
        By goToCart = By.xpath("//span[text()='Cart']");
        driver.findElement(goToCart).click();
        Thread.sleep(2000);
    }
    public int getCartItemListSize(){
        By product = By.xpath("(//div[@class='_1AtVbE col-12-12']//div[text()='Remove'])");
        return driver.findElements(product).size();
    }
    public void AddToCart(WebElement element) throws InterruptedException {
        element.click();
        Thread.sleep(2000);
        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> iterator =  windowHandles.iterator();
        String parentWindow = iterator.next();
        String childWindow = iterator.next();
        driver.switchTo().window(childWindow);
        By AddtoCartButton = By.xpath("//button[text()='Add to cart']");
        driver.findElement(AddtoCartButton).click();
        Thread.sleep(2000);
        Reporter.log("Element added to cart successfully",true);
        driver.navigate().back();
        driver.close();
        driver.switchTo().window(parentWindow);
    }
    public void DeleteFromCart(){
        try{
            By product = By.xpath("(//div[@class='_1AtVbE col-12-12']//div[text()='Remove'])");
            while(driver.findElements(product).size()>0){
                WebElement elements = driver.findElements(product).get(0);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0,100)", "");
                elements.click();
                Thread.sleep(2000);
                By cancel = By.xpath("//div[@class='_2_e-g9 _2WFwmV']//child::div[text()='Remove']");
                driver.findElement(cancel).click();
                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getLocalizedMessage());
        }

    }
    public static void searchProduct(String args) throws InterruptedException {
        searchBox.sendKeys(args);
        Thread.sleep(2000);
        searchButton.click();
        String actualTitle = driver.getTitle();
        String temp = args.toUpperCase().charAt(0)+args.substring(1);
        String expectedTitle = temp+"- Buy Products Online at Best Price in India - All Categories | Flipkart.com";
        Assert.assertEquals(actualTitle, expectedTitle,"Product search failed");
        Reporter.log("search for "+args+" is Successful",true);
    }
}
