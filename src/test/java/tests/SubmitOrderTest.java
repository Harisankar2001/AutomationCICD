package tests;

import com.hari.pageobjects.*;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testcomponents.BaseTest;
import testcomponents.ListenersTest;
import testcomponents.Retry;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SubmitOrderTest extends BaseTest {

    String productName = "ZARA COAT 3";


    @Test(dataProvider = "getData", groups = {"purchase"})
    public void submitOrderTest(Map<String, String> input) throws InterruptedException, IOException {
//        ProductCatalogue productCatalogue = landingPage.loginApplication("hari21@gmail.com", "Hari@2001");

        //new Comments added

        String productName = input.get("product");
        ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));

        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();

        Boolean match = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(match);

        CheckoutPage checkoutPage = cartPage.goToCheckOut();
        checkoutPage.selectCountry("India");
        ConfirmationPage confirmationPage = checkoutPage.submitOrder();
        Assert.assertTrue(confirmationPage.getConfirmationMessage().equalsIgnoreCase("Thankyou for the order."));
    }

    @Test(groups = {"purchase"}, retryAnalyzer = Retry.class)
    public void orderHistoryTest() throws InterruptedException {
        ProductCatalogue productCatalogue = landingPage.loginApplication("hari21@gmail.com", "Hari@2001");
        OrderPage orderPage = productCatalogue.goToOrderPage();
        Boolean isVerified = orderPage.verifyOrderDisplay(productName);
        Assert.assertTrue(isVerified);
    }



    @DataProvider
    public Object[][] getData() throws IOException {
        List<Map<String, String>> jsonDataMap = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//data//PurchaseOrder.json");
        return new Object[][]{{jsonDataMap.getFirst()}};
    }


    /*@DataProvider
    public Object[][] getData(){
        Map<String, String> map = new HashMap<>();
        map.put("email", "hari21@gmail.com");
        map.put("password", "Hari@2001");
        map.put("product", "ZARA COAT 3");

        Map<String, String> map1 = new HashMap<>();
        map1.put("email", "shetty@gmail.com");
        map1.put("password", "Iamking@000");
        map1.put("product", "ADIDAS ORIGINAL");

        return new Object[][]{{map}, {map1}};
    }*/


   /* @DataProvider
    public Object[][] getData(){

        return new Object[][]{{"hari21@gmail.com", "Hari@2001", "ZARA COAT 3"}, {"shetty@gmail.com", "Iamking@000", "ADIDAS ORIGINAL"}};
    }*/

}
