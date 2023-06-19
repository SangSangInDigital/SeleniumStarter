package com.sangsanginib.seleniumstarter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* 한국투자증권 장외채권 데이터 크롤러 */
public class CrawlerPage01 {
    private Logger logger = LoggerFactory.getLogger(CrawlerPage01.class);
    public void getBondsData() {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*");   // Selenium WebDriver를 사용하여 다른 도메인의 브라우저에 접근
            chromeOptions.addArguments("--headless");  // GUI 없는 Headless 모드로 실행 (필요에 따라 제외 가능)
            chromeOptions.addArguments("--no-sandbox"); // Sandbox 모드 비활성화 -> 호환성 문제를 해결하고 Chrome 실행의 안정성을 높이기 위해
            chromeOptions.addArguments("--disable-dev-shm-usage"); // /dev/shm 사용 비활성화 -> Docker 컨테이너 환경에서 Chrome 실행 시 메모리 제한 관련 문제를 해결
            chromeOptions.addArguments("--lang=ko_KR.UTF-8");
//            chromeOptions.setHeadless(false);

            ChromeDriver driver = new ChromeDriver(chromeOptions);
        try{
            logger.info("****Before driver.get****");
            driver.get("https://truefriend.com/main/mall/opendecision/DecisionInfo.jsp?cmd=TF02da010100");
            Thread.sleep(10000);
            logger.info("****After driver.get****");
            logger.info(driver.getPageSource());
            logger.info(driver.getCastIssueMessage());
            logger.info(driver.getCurrentUrl());
            logger.info(driver.getTitle());
            logger.info(driver.getWindowHandle());



            logger.info("****Before driver.find****");
            List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div/div[2]/table/tbody/tr"));
            logger.info("****After driver.find****");

            int size = webElements.size();
            logger.info("****size: "+size);
            logger.info("****Before title****");
            for (int i = 1; i < size + 1; i += 2) {
                String title = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i + "]/td[2]/strong")).getText();
                logger.info(title);
            }
            logger.info("****After title****");
            logger.info("****Before beforeReturnRate****");
            for (int i = 2; i < size + 2; i += 2) {
                String beforeReturnRate = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i + "]/td[2]")).getText();
                logger.info(beforeReturnRate);
            }
            logger.info("****After beforeReturnRate****");
            logger.info("****Before afterReturnRate****");
            for (int i = 2; i < size + 2; i += 2) {
                String afterReturnRate = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i +"]/td[3]")).getText();
                logger.info(afterReturnRate);
            }
            logger.info("****After afterReturnRate****");
        }catch (Exception e){
            logger.error("====한국투자증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====한국투자증권 crawling error end====");
        }finally {
            driver.close();
        }
    }
}