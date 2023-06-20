package com.sangsanginib.seleniumstarter.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* 삼성증권 장외채권 데이터 크롤러 */
public class CrawlerPage04 {
    private Logger logger = LoggerFactory.getLogger(CrawlerPage01.class);
    public void getBondsData() {

        // Xvfb 실행 명령어 설정
        String xvfbCommand = "Xvfb :99 -ac -screen 0 1280x1024x16";

        // WebDriver 경로 설정
        String driverPath = "/home/developer/chrome/chromedriver";

        // Xvfb 실행
        try {
//            Runtime.getRuntime().exec(xvfbCommand);
//            Thread.sleep(2000); // Xvfb가 실행되기를 기다림
//            DesiredCapabilities caps = new DesiredCapabilities();
//            caps.setPlatform(Platform.LINUX); // 사용하는 플랫폼에 맞게 설정

            // ChromeOptions 설정
            ChromeOptions chromeOptions = new ChromeOptions();
//            chromeOptions.merge(caps);
//            chromeOptions.addArguments("--display=:99"); // Xvfb 디스플레이 설정
//            chromeOptions.setBinary("/usr/bin/google-chrome-stable"); // Chrome 실행 파일 경로 설정
            chromeOptions.addArguments("--remote-allow-origins=*");
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            // WebDriver 설정
//            System.setProperty("webdriver.chrome.driver", driverPath);
//            WebDriver driver = new ChromeDriver(chromeOptions);

            // 크롤링 작업 수행
            logger.info("****수정됨****");
            logger.info("****remoteDriver4****");
            ChromeDriver driver = new ChromeDriver(chromeOptions);

//            WebDriver driver = new RemoteWebDriver((new URL( "https://truefriend.com/main/mall/opendecision/DecisionInfo.jsp?cmd=TF02da010100")), chromeOptions);
            logger.info("****Before driver4.get****");
            logger.info(driver.getCurrentUrl());
            driver.get("https://truefriend.com/main/mall/opendecision/DecisionInfo.jsp?cmd=TF02da010100");
            logger.info(driver.getCurrentUrl());
            Thread.sleep(10000);


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
            // WebDriver 종료
            driver.quit();
        } catch (Exception e) {
            logger.error("====한국투자증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====한국투자증권 crawling error end====");
        }





















//        String xvfbCommand = "Xvfb :99 -screen 0 1024x768x24 -fbdir /var/run";
//        logger.info("****ProcessBuilder****");
//        ProcessBuilder xvfbProcessBuilder = new ProcessBuilder("bash", "-c", xvfbCommand);
//        try {
//            Process xvfbProcess = xvfbProcessBuilder.start();
//            Thread.sleep(2000); // Xvfb가 시작될 때까지 잠시 대기
//            logger.info("****ProcessBuilder start****");
//
//            // ChromeDriver 설정
//            ChromeDriverService service = new ChromeDriverService.Builder()
//                    .usingDriverExecutable(new File("/home/developer/chrome/chromedriver"))
//                    .usingAnyFreePort()
//                    .build();
//            logger.info("****ChromeDriverService****");
//            // ChromeOptions 설정
//            ChromeOptions chromeOptions = new ChromeOptions();
//            chromeOptions.addArguments("--remote-allow-origins=*");
//            chromeOptions.addArguments("--headless");
//            chromeOptions.addArguments("--no-sandbox");
//            chromeOptions.addArguments("--disable-dev-shm-usage");
//
//            // WebDriver 생성
//            WebDriver driver = new ChromeDriver(service, chromeOptions);
//            logger.info("****WebDriver****");
//            // 크롤링 로직 작성
//            // ...
//            driver.get("https://truefriend.com/main/mall/opendecision/DecisionInfo.jsp?cmd=TF02da010100");
//            logger.info("****driver****");
//            logger.info(driver.getPageSource());
//            // WebDriver 종료
//            driver.quit();
//
//            // Xvfb 종료
//            xvfbProcess.destroy();
//        } catch (Exception e) {
//            logger.error("====한국투자증권 crawling error start====");
//            logger.error(e.toString());
//            logger.error(e.getMessage());
//            logger.error("====한국투자증권 crawling error end====");
//        }


















//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--remote-allow-origins=*");   // 해당 부분 추가
//        chromeOptions.addArguments("--headless");
//        chromeOptions.addArguments("--no-sandbox");
//        chromeOptions.addArguments("--disable-dev-shm-usage");
//        ChromeDriver driver = new ChromeDriver(chromeOptions);
//        try{
//            driver.get("https://www.samsungpop.com/?MENU_CODE=M1231752589437");
//
//            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
//
//            driver.switchTo().frame("frmContent");
//
//            logger.info("****삼성증권 start****");
//            logger.info(driver.getPageSource());
//            logger.info("****삼성증권 end****");
//        }catch (Exception e){
//            logger.error("====한국투자증권 crawling error start====");
//            logger.error(e.toString());
//            logger.error(e.getMessage());
//            logger.error("====한국투자증권 crawling error end====");
//        }finally {
//            driver.close();
//        }
    }
}