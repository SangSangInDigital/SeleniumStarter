package com.sangsanginib.seleniumstarter;

import com.sangsanginib.seleniumstarter.pages.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeleniumStarterApplication {
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:\\chromedriver.exe";
    // public static final String WEB_DRIVER_PATH = System.getProperty("user.dir")/chromedriver.exe;

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(SeleniumStarterApplication.class, args).getBean(SeleniumStarterApplication.class).crawling();
    }

    public void crawling() throws InterruptedException {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
//      CrawlerPage01 crawlerPage01 = new CrawlerPage01();
        CrawlerPage02 crawlerPage02 = new CrawlerPage02();
//		CrawlerPage03 crawlerPage03 = new CrawlerPage03();
//		CrawlerPage04 crawlerPage04 = new Crawl1010erPage04();
//        CrawlerPage05 crawlerPage05 = new CrawlerPage05();
//      crawlerPage01.getBondsData();
        crawlerPage02.getBondsData();
//		crawlerPage03.getBondsData();
//		crawlerPage04.getBondsData();
        //crawlerPage05.getBondsData();
    }
}
