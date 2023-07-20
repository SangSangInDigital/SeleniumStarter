package com.sangsanginib.seleniumstarter.controller;

import com.sangsanginib.seleniumstarter.dto.CrawlingDatas;
import com.sangsanginib.seleniumstarter.service.CrawlingService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CrawlingController {
    private final Logger logger = LoggerFactory.getLogger(CrawlingController.class);
    private final CrawlingService crawlingService;
    @GetMapping("/crawling01")
    public ResponseEntity<List<CrawlingDatas>> crawling01(){
        logger.info("******controller start******");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*"); // Selenium WebDriver 를 사용하여 다른 도메인의 브라우저에 접근
        chromeOptions.addArguments("--headless=new"); // GUI 없는 Headless 모드로 실행 (필요에 따라 제외 가능)
        chromeOptions.addArguments("--no-sandbox"); // Sandbox 모드 비활성화 -> 호환성 문제를 해결하고 Chrome 실행의 안정성을 높이기 위해
        chromeOptions.addArguments("--disable-dev-shm-usage"); // /dev/shm 사용 비활성화 -> Docker 컨테이너 환경에서 Chrome 실행 시 메모리 제한 관련 문제를 해결

        List<CrawlingDatas>  dataList = new ArrayList<>();

        ChromeDriver driver = new ChromeDriver(chromeOptions);
        dataList.addAll(crawlingService.crawlerTruefriend(driver));

        driver = new ChromeDriver(chromeOptions);
        dataList.addAll(crawlingService.crawlerShinhan(driver));

        driver = new ChromeDriver(chromeOptions);
        dataList.addAll(crawlingService.crawlerSamsung(driver));

        driver = new ChromeDriver(chromeOptions);
        dataList.addAll(crawlingService.crawlerMirae(driver));

        driver = new ChromeDriver(chromeOptions);
        dataList.addAll(crawlingService.crawlerKb(driver));

        driver = new ChromeDriver(chromeOptions);
        dataList.addAll(crawlingService.crawlerPage06(driver));

        driver = new ChromeDriver(chromeOptions);
        dataList.addAll(crawlingService.crawlerKiwoom(driver));

        driver = new ChromeDriver(chromeOptions);
        dataList.addAll(crawlingService.crawlerDaishin(driver));
        driver.quit();
        logger.info("******controller end******");
        return ResponseEntity.status(HttpStatus.OK).body(dataList);
    }

}
