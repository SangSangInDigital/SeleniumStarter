package com.sangsanginib.seleniumstarter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* 신한투자증권 장외채권 데이터 크롤러 */
public class CrawlerPage02 {

    public void getBondsData() throws InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");   // 해당 부분 추가
        ChromeDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.shinhansec.com/siw/wealth-management/bond-rp/5901/view.do");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        int maxAttempts = 10;
        int attemptCount = 0;

        while (attemptCount < maxAttempts) {
            try {
                WebElement element = driver.findElement(By.xpath("//*[@id=\"main\"]/button"));
                System.out.println("더보기 버튼 클릭########################");
                if (element.isDisplayed() && element.isEnabled()) {
                    element.click();
                    Thread.sleep(1000);
                    attemptCount++;
                } else {
                    break;
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {
                break; // 루프 종료
            }
        }

        List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"main\"]/table/tbody/tr"));
        System.out.println(webElements.size());

        int size = webElements.size();

        for (int i = 1; i < size + 1; i++) {
            String title = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[" + i + "]/td[2]/a")).getText();
            System.out.println(title);//*[@id="content"]/div[2]/div/div[2]/table/tbody/tr[102]/td[2] //*[@id="content"]/div[2]/div/div[2]/table/tbody/tr[102]/td[3] //*[@id="content"]/div[2]/div/div[2]/table/tbody/tr[101]/td[2]/strong
            String endDate = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[" + i + "]/td[3]")).getText();
            System.out.println(endDate);
        }
    }
}