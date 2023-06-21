package com.sangsanginib.seleniumstarter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.List;

/* 삼성증권 장외채권 데이터 크롤러 */
public class CrawlerPage04 {
    private Logger logger = LoggerFactory.getLogger(CrawlerPage01.class);
    public void getBondsData() {
        try {

            // ChromeOptions 설정
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*"); //크롬 실행하기 위해서 꼭 필요
            chromeOptions.addArguments("--headless=new"); // GUI 없는 Headless 모드로 실행 (필요에 따라 제외 가능)
            chromeOptions.addArguments("--no-sandbox"); // Sandbox 모드 비활성화 -> 호환성 문제를 해결하고 Chrome 실행의 안정성을 높이기 위해
            chromeOptions.addArguments("--disable-dev-shm-usage"); // /dev/shm 사용 비활성화 -> Docker 컨테이너 환경에서 Chrome 실행 시 메모리 제한 관련 문제를 해결

            // 크롤링 작업 수행
            logger.info("****remoteDriver4****");
            ChromeDriver driver = new ChromeDriver(chromeOptions);

            logger.info("****Before driver4.get****");
            driver.get("https://www.samsungpop.com/?MENU_CODE=M1231752589437");

            Thread.sleep(10000);

            List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div/div[2]/table/tbody/tr"));

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
            // WebDriver 종료
            driver.quit();
        } catch (Exception e) {
            logger.error("====한국투자증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====한국투자증권 crawling error end====");
        }
    }
}