package com.sangsanginib.seleniumstarter.service;

import com.sangsanginib.seleniumstarter.entity.CrawlingData;
import com.sangsanginib.seleniumstarter.repository.CrawlingRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlingService {
    private Logger logger = LoggerFactory.getLogger(CrawlingService.class);
    private final CrawlingRepository crawlingRepository;
    @Transactional(readOnly = true)
    public void crawlerPage01 (ChromeDriver driver){
        try{

            // 크롤링 작업 수행
            logger.info("****remoteDriver1****");

            logger.info("****Before driver1.get****");
            driver.get("https://truefriend.com/main/mall/opendecision/DecisionInfo.jsp?cmd=TF02da010100");

            Thread.sleep(10000);

            //데이터 크롤링
            List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div/div[2]/table/tbody/tr"));

            int size = webElements.size();
            logger.info("****size: "+size);
            logger.info("****Before crawling****");
            for (int i = 1; i < size + 1; i += 2) {
                CrawlingData data = new CrawlingData();
                String title = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i + "]/td[2]/strong")).getText();
                logger.info(title);
                data.setCompany(title);
                crawlingRepository.save(data);
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
    @Transactional(readOnly = true)
    public void crawlerPage02 (ChromeDriver driver){
        driver.get("https://www.shinhansec.com/siw/wealth-management/bond-rp/5901/view.do");
    }@Transactional(readOnly = true)
    public void crawlerPage03 (ChromeDriver driver){
        driver.get("https://www.kbsec.com/go.able?linkcd=s010602010000");
    }@Transactional(readOnly = true)
    public void crawlerPage04 (ChromeDriver driver){
        driver.get("https://www.samsungpop.com/?MENU_CODE=M1231752589437");
    }@Transactional(readOnly = true)
    public void crawlerPage05 (ChromeDriver driver){
        driver.get("https://securities.miraeasset.com/hks/hks4036/r01.do");
    }

}
