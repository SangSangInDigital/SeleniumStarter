package com.sangsanginib.seleniumstarter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* 삼성증권 장외채권 데이터 크롤러 */
public class CrawlerPage01 {

    public void getBondsData() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://truefriend.com/main/mall/opendecision/DecisionInfo.jsp?cmd=TF02da010100");

        // String title = driver.getTitle();
        // System.out.println(title);
        // assertEquals("Web form", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        // WebElement textBox = driver.findElement(By.name("my-text"));
        // WebElement submitButton = driver.findElement(By.cssSelector("button"));

        // textBox.sendKeys("Selenium");
        // submitButton.click();

        // WebElement message = driver.findElement(By.id("message"));
        // String value = message.getText();
        // assertEquals("Received!", value);

        // driver.quit();

        List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div/div[2]/table/tbody/tr"));
        System.out.println(webElements.size());
        ////*[@id="content"]/div[2]/div/div[2]/table/tbody/tr[1]/td[2]/strong
        //*[@id="content"]/div[2]/div/div[2]/table/tbody/tr[3]/td[2]/
        ////*[@id="content"]/div[2]/div/div[2]/table/tbody/tr[5]/td[2]/strong

        int size = webElements.size();
        // List<BondsList> bondsLists = new ArrayList<>();

        for (int i = 1; i < size + 1; i += 2) {
            String title = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i + "]/td[2]/strong")).getText();
            System.out.println(title);//*[@id="content"]/div[2]/div/div[2]/table/tbody/tr[102]/td[2] //*[@id="content"]/div[2]/div/div[2]/table/tbody/tr[102]/td[3] //*[@id="content"]/div[2]/div/div[2]/table/tbody/tr[101]/td[2]/strong
            // /html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[2]/td[2]
            // /html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[12]/td[2]
            // /html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[14]/td[2]
            // String afterReturnRate = "";
        }

        for (int i = 2; i < size + 2; i += 2) {
            String beforeReturnRate = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i + "]/td[2]")).getText();
            System.out.println(beforeReturnRate);
        }

        for (int i = 2; i < size + 2; i += 2) {
            String afterReturnRate = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i +"]/td[3]")).getText();
            System.out.println(afterReturnRate);
        }
    }
}