package testcomponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hari.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    String globalDataProperties = "//src//main//resources//GlobalData.properties";
    protected LandingPage landingPage;

    public String propertyGetting(String propertyName, String filePath) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+filePath);
        prop.load(fis);
        return prop.getProperty(propertyName);
    }


    public WebDriver initializeDriver() throws IOException {

//        String browserName = propertyGetting("browser", globalDataProperties);

        String browserName = System.getProperty("browser")!=null?System.getProperty("browser"):propertyGetting("browser", globalDataProperties);

        String mode = getMode(browserName);

        switch (mode){
            case "chrome":
            case "chromeheadless":
                ChromeOptions options = new ChromeOptions();
                WebDriverManager.chromedriver().setup();

                if (browserName.contains("headless"))
                    options.addArguments("--headless");

                driver = new ChromeDriver(options);
                driver.manage().window().setSize(new Dimension(1440,900));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new NotFoundException("Driver Not Found");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        return driver;
    }

    private static String getMode(String browserName) {
        String mode = "";

        if (browserName.contains("chrome"))
            mode = browserName.contains("headless") ? "chromeheadless" : "chrome";
        else if (browserName.contains("firefox"))
            mode = browserName.contains("headless") ? "firefoxheadless" : "firefox";
        else if (browserName.contains("edge"))
            mode = browserName.contains("headless") ? "edgeheadless" : "edge";
        else
                mode = "";
        return mode;
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        driver = initializeDriver();
        landingPage = new LandingPage(driver);
        String url = propertyGetting("url", globalDataProperties);
        landingPage.goTo(url);
        return landingPage;
    }

    public List<Map<String, String>> getJsonDataToMap(String filePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<Map<String, String>>>() {
        });

        return data;
    }

    public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
        String filePathName = System.getProperty("user.dir")+"//reports//"+testCaseName+".png";
        TakesScreenshot ts = ((TakesScreenshot) driver);
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(filePathName);
        FileUtils.copyFile(source, file);
        return file.getName();
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.close();
    }
}
