package tests;

import com.hari.pageobjects.CartPage;
import com.hari.pageobjects.ProductCatalogue;
import org.testng.Assert;
import org.testng.annotations.Test;
import testcomponents.BaseTest;

public class ErrorValidationsTest extends BaseTest {

    @Test(groups = {"ErrorHandling"})
    public void loginErrorValidation() throws InterruptedException {
        landingPage.loginApplication("ashika@gmail.com", "Ashik@20012");
        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
    }

    @Test
    public void productErrorValidation() throws InterruptedException {
        String productName = "ZARA COAT 3";
        ProductCatalogue productCatalogue = landingPage.loginApplication("hari21@gmail.com", "Hari@2001");

        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();

        Boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
        Assert.assertFalse(match);
    }
}
