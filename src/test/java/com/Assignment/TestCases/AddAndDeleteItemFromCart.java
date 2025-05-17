package com.Assignment.TestCases;

import com.Assignment.PageObject.PageObjectFlipkart;
import com.Assignment.Utils.BrowserManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddAndDeleteItemFromCart {
    String url = "https://www.flipkart.com/";
    @Test
    public void TC_AddAndDeleteProductFromCart(){
        try{
            WebDriver driver = BrowserManager.getDriver("Chrome",url);
            PageObjectFlipkart pageObjectFlipkart = new PageObjectFlipkart(driver);
            pageObjectFlipkart.addMultipleItemtoCart("iphone");
            pageObjectFlipkart.navigateToCart();
            int size = pageObjectFlipkart.getCartItemListSize();
            Assert.assertEquals(size,3,"Items size not matched with actual items added in cart");
            pageObjectFlipkart.DeleteFromCart();
            int size1 = pageObjectFlipkart.getCartItemListSize();
            Assert.assertEquals(size1, 0,"Cart is not empty");
            Thread.sleep(1000);
            driver.quit();
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail(e.getLocalizedMessage());
        }
    }
}
