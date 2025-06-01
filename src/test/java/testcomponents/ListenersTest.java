package testcomponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.hari.resource.ExtentReporterNG;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.IOException;

public class ListenersTest extends BaseTest implements ITestListener {
    ExtentTest test;
    ExtentReports extentReports = ExtentReporterNG.getReporterObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test = extentReports.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {


        String filePath = "";

        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch(Exception e){
            e.printStackTrace();
        }


        try {
            filePath = getScreenShot(result.getMethod().getMethodName(), driver);
        } catch (IOException e) {
            e.printStackTrace();
        }

        extentTest.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(filePath).build());
        extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
