package com.sangsanginib.seleniumstarter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* 미래에셋증권 장외채권 데이터 크롤러 */
public class CrawlerPage05 {

    public void getBondsData() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://securities.miraeasset.com/hks/hks4036/r01.do");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        System.out.println(driver.getPageSource());

/*        int maxAttempts = 10;
        int attemptCount = 0;

        boolean elementFound = false;
        while (!elementFound && attemptCount < maxAttempts) {
            try {
                WebElement element = driver.findElement(By.xpath("//*[@id=\"main\"]/button"));
                element.click();
                elementFound = true;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                attemptCount++;
            }
        }

        List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"main\"]/table/tbody/tr"));
        System.out.println(webElements.size());

        int size = webElements.size();

        for (int i = 1; i < size + 1; i ++) {
            String title = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr["+ i +"]/td[2]/a")).getText();
            System.out.println(title);//*[@id="content"]/div[2]/div/div[2]/table/tbody/tr[102]/td[2] //*[@id="content"]/div[2]/div/div[2]/table/tbody/tr[102]/td[3] //*[@id="content"]/div[2]/div/div[2]/table/tbody/tr[101]/td[2]/strong
            String endDate = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr["+ i +"]/td[3]")).getText();
            System.out.println(endDate);
        }*/
    }
}