package com.hari.pageobjects;

import com.hari.abstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductCatalogue extends AbstractComponent {

    WebDriver driver;

    public ProductCatalogue(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".mb-3")
    List<WebElement> products;

    @FindBy(css = ".ng-animating")
    WebElement spinner;

    @FindBy(xpath = "//h3[text()='Automation']")
    WebElement automationText;

    @FindBy(css = ".mt-1>h3")
    WebElement logoText;


    By productsBy = By.cssSelector(".mb-3");
    By addToCart = By.cssSelector(".card-body button:last-of-type");
    By toastMessage = By.cssSelector("#toast-container");


    private List<WebElement> getProductList(){
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement getProductByName(String productName){
        WebElement prod = getProductList().stream()
                .filter(product -> product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(productName))
                .findFirst()
                .orElse(null);

        return prod;

    }


    public void addProductToCart(String productName) throws InterruptedException {
        alertHandle();
        WebElement prod = getProductByName(productName);
        prod.findElement(addToCart).click();
        waitForElementToAppear(toastMessage);
//        waitForElementToDisAppear(spinner);
        sleepThread(1000);
    }

    public String verifyText(){
        return logoText.getText();
    }

}
