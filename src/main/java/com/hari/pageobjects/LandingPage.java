package com.hari.pageobjects;

import com.hari.abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponent {

    WebDriver driver;

    public LandingPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "userEmail")
    WebElement userEmail;

    @FindBy(id = "userPassword")
    WebElement userPassword;

    @FindBy(id = "login")
    WebElement submit;

    @FindBy(css = "[class*='flyInOut']")
    WebElement errorMessage;

    public ProductCatalogue loginApplication(String email, String password) throws InterruptedException {
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        submit.click();
        return new ProductCatalogue(driver);
    }

    public void goTo(String url){
        driver.get(url);
    }

    public String getErrorMessage(){
        waitForElementToAppear(errorMessage);
        return errorMessage.getText();
    }
}
