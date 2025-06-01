package com.hari.hack;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class Samp {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");
        driver.findElement(By.id("userEmail")).sendKeys("hari21@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Hari@2001");
        driver.findElement(By.id("login")).click();

        // Locate element using CSS selector
        WebElement element = driver.findElement(By.cssSelector(".mb-3 b"));


        // Use JavaScript to get XPath
        String script =
                "function getElementXPath(elt) {" +
                        "  var path = '';" +
                        "  for (; elt && elt.nodeType == 1; elt = elt.parentNode) {" +
                        "    var idx = 1;" +
                        "    for (var sib = elt.previousSibling; sib; sib = sib.previousSibling) {" +
                        "      if (sib.nodeType == 1 && sib.tagName == elt.tagName) idx++;" +
                        "    }" +
                        "    var xname = elt.tagName.toLowerCase();" +
                        "    path = '/' + xname + '[' + idx + ']' + path;" +
                        "  }" +
                        "  return path;" +
                        "}" +
                        "return getElementXPath(arguments[0]);";

        String xpath = (String) ((JavascriptExecutor) driver).executeScript(script, element);
        System.out.println("XPath: " + xpath);

        driver.quit();
    }
}

