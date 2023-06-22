package com.sangsanginib.seleniumstarter.pages;

import com.sangsanginib.seleniumstarter.entity.CrawlingData;
import com.sangsanginib.seleniumstarter.repository.CrawlingRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/* 한국투자증권 장외채권 데이터 크롤러 */
//@Service
//@RequiredArgsConstructor
public class CrawlerPage01 {
    private Logger logger = LoggerFactory.getLogger(CrawlerPage01.class);
//    private final CrawlingRepository crawlingRepository;
    public void getBondsData() {
        try {

            // ChromeOptions 설정
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*"); // Selenium WebDriver를 사용하여 다른 도메인의 브라우저에 접근
            chromeOptions.addArguments("--headless=new"); // GUI 없는 Headless 모드로 실행 (필요에 따라 제외 가능)
            chromeOptions.addArguments("--no-sandbox"); // Sandbox 모드 비활성화 -> 호환성 문제를 해결하고 Chrome 실행의 안정성을 높이기 위해
            chromeOptions.addArguments("--disable-dev-shm-usage"); // /dev/shm 사용 비활성화 -> Docker 컨테이너 환경에서 Chrome 실행 시 메모리 제한 관련 문제를 해결

            // 크롤링 작업 수행
            logger.info("****remoteDriver1****");
            ChromeDriver driver = new ChromeDriver(chromeOptions);

            logger.info("****Before driver1.get****");
            driver.get("https://truefriend.com/main/mall/opendecision/DecisionInfo.jsp?cmd=TF02da010100");

            Thread.sleep(10000);

            //데이터 크롤링
            List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div/div[2]/table/tbody/tr"));

            int size = webElements.size();
            logger.info("****size: "+size);
            logger.info("****Before crawling****");
            for (int i = 1; i < size + 1; i += 2) {
//                CrawlingData data = new CrawlingData();
                String title = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i + "]/td[2]/strong")).getText();
                logger.info(title);
//                data.setCompany(title);
//                crawlingRepository.save(data);
            }
            logger.info("****After crawling****");
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