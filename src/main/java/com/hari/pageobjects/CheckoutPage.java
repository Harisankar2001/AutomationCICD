package com.hari.pageobjects;

import com.hari.abstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends AbstractComponent {

    WebDriver driver;

    public CheckoutPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[placeholder = 'Select Country']")
    WebElement country;

    @FindBy(css = ".action__submit")
    WebElement submit;

    String formatText = "//span[text()=' %s']/parent::button";

    By results = By.cssSelector(".ta-results");

    private WebElement getCountryOption(String countryName) {
        String xpath = String.format(formatText, countryName);
        return driver.findElement(By.xpath(xpath));
    }

    public void selectCountry(String countryName){
        Actions actions = new Actions(driver);
        actions.sendKeys(country, countryName).build().perform();
        waitForElementToAppear(results);
        getCountryOption(countryName).click();
    }

    public ConfirmationPage submitOrder() throws InterruptedException {
        initJavaScriptExecutor(800);
        waitForElementToClickable(submit);
        sleepThread(500);
        submit.click();
        return new ConfirmationPage(driver);
    }
}
