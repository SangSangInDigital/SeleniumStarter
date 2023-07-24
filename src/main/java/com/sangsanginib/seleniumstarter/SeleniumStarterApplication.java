package com.sangsanginib.seleniumstarter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SeleniumStarterApplication {
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
//         public static final String WEB_DRIVER_PATH = "C:\\chromedriver.exe";
        public static final String WEB_DRIVER_PATH = "/home/developer/crawling/chromedriver";
    private static Logger logger = LoggerFactory.getLogger(SeleniumStarterApplication.class);

    public static void main(String[] args) {
        try {
            System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
            ConfigurableApplicationContext context = SpringApplication.run(SeleniumStarterApplication.class, args);
            context.getBean(SeleniumStarterApplication.class);
        } catch (Exception e) {
            logger.error(e.toString());
        }

    }

}
