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
    //    public static final String WEB_DRIVER_PATH = "C:\\chromedriver.exe";
    public static final String WEB_DRIVER_PATH = "/home/developer/chrome/chromedriver";
    private static Logger logger = LoggerFactory.getLogger(SeleniumStarterApplication.class);

    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext context = SpringApplication.run(SeleniumStarterApplication.class, args);
            context.getBean(SeleniumStarterApplication.class);
            crawling();
            context.close();
        } catch (Exception e) {
            logger.error(e.toString());
        }

    }

    public static void crawling() {
        // WebDriver 설정
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        RestTemplate restTemplate = new RestTemplate();

        // 호출할 컨트롤러의 URL
        String url = "http://localhost:8082/crawling";

        // 컨트롤러 호출
        restTemplate.getForObject(url, String.class);

    }
}
