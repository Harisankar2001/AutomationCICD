package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {
    public static void main(String[] args) {

        String productName = "ZARA COAT 3";

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");

        driver.findElement(By.id("userEmail")).sendKeys("hari21@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Hari@2001");
        driver.findElement(By.id("login")).click();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        WebElement prod =   products.stream().filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);

        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));



        boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);

        driver.findElement(By.cssSelector(".totalRow button")).click();

        Actions actions = new Actions(driver);

        actions.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("[class*='ta-results']"))));

        driver.findElement(By.xpath("(//button[contains(@class, 'ta-item')])[2]")).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 800);");

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(".action__submit"))));
        driver.findElement(By.cssSelector(".action__submit")).click();

        Assert.assertTrue(driver.findElement(By.cssSelector(".hero-primary")).isDisplayed());

    }

}
