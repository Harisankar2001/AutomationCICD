package stepdefinitions;

import com.hari.pageobjects.*;
import io.cucumber.java.en.*;
import org.testng.Assert;
import testcomponents.BaseTest;

import java.io.IOException;

public class StepDefinitionImpl extends BaseTest {

    public LandingPage landingPage;
    public ProductCatalogue productCatalogue;
    public ConfirmationPage confirmationPage;

    @Given("I landed on Ecommerce Page")
    public void iLandedOnEcommercePage() throws IOException {
        landingPage = launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void loggedInWithUsernameAndPassword(String userName, String password) throws InterruptedException {
        productCatalogue = landingPage.loginApplication(userName, password);

    }

    @When("^I add product (.+) to cart$")
    public void iAddProductToCart(String productName) throws InterruptedException {
        productCatalogue.addProductToCart(productName);
    }


    @And("^Checkout (.+) and submit the order$")
    public void checkoutAndSubmitTheOrder(String productName) throws InterruptedException {
        CartPage cartPage = productCatalogue.goToCartPage();

        Boolean match = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(match);

        CheckoutPage checkoutPage = cartPage.goToCheckOut();
        checkoutPage.selectCountry("India");
        confirmationPage = checkoutPage.submitOrder();
    }

    @Then("{string} message is displayed on ConfirmationPage")
    public void messageIsDisplayedOnConfirmationPage(String messageConfirmation) {
        String confirmationMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMessage.equalsIgnoreCase(messageConfirmation));
        tearDown();
    }


    @But("{string} message is displayed")
    public void messageIsDisplayed(String errorMessage) {
        String errorMess = landingPage.getErrorMessage();
        Assert.assertEquals(errorMessage, errorMess);
        tearDown();
    }
}
