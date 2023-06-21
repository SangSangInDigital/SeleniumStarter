package com.sangsanginib.seleniumstarter;

import com.sangsanginib.seleniumstarter.pages.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class SeleniumStarterApplication {
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
//    public static final String WEB_DRIVER_PATH = "C:\\chromedriver.exe";
	public static final String WEB_DRIVER_PATH = "/home/developer/chrome/chromedriver";
    private static Logger logger = LoggerFactory.getLogger(SeleniumStarterApplication.class);
    public static void main(String[] args) {
        try{
            ConfigurableApplicationContext context =  SpringApplication.run(SeleniumStarterApplication.class, args);
            context.getBean(SeleniumStarterApplication.class).crawling();
            context.close();

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    public static void crawling() {
        // WebDriver 설정
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		CrawlerPage01 crawlerPage01 = new CrawlerPage01();
//		CrawlerPage02 crawlerPage02 = new CrawlerPage02();
//		CrawlerPage03 crawlerPage03 = new CrawlerPage03();
//		CrawlerPage04 crawlerPage04 = new CrawlerPage04();
		//CrawlerPage05 crawlerPage05 = new CrawlerPage05();
		crawlerPage01.getBondsData();
//		crawlerPage02.getBondsData();
//		crawlerPage03.getBondsData();
//		crawlerPage04.getBondsData();
		//crawlerPage05.getBondsData();
    }
}
