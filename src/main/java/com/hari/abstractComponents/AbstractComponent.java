package com.hari.abstractComponents;

import com.hari.pageobjects.CartPage;
import com.hari.pageobjects.OrderPage;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class AbstractComponent {

    WebDriver driver;

    public AbstractComponent(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[routerlink*='cart']")
    WebElement cartHeader;

    @FindBy(css = "[routerlink*='myorders']")
    WebElement orderHeader;


    public void waitForElementToAppear(By findBy){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitForElementToAppear(WebElement findBy){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(findBy));
    }


    public void waitForElementToDisAppear(WebElement findBy){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(findBy));
    }

    public void waitForElementToClickable(WebElement findBy){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(findBy));
    }

    public void sleepThread(int milliSeconds) throws InterruptedException {
        Thread.sleep(milliSeconds);
    }

    public CartPage goToCartPage(){
        cartHeader.click();
        return new CartPage(driver);
    }

    public OrderPage goToOrderPage() throws InterruptedException {
        orderHeader.click();
        return new OrderPage(driver);
    }

    public void initJavaScriptExecutor(int scroll) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, " + scroll + ");");
    }

    public boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public void alertHandle(){
        if (isAlertPresent(driver)){
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert Text: "+alert.getText());
            alert.accept();
        }
    }

}
