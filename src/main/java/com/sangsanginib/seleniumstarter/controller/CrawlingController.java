package com.sangsanginib.seleniumstarter.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class CrawlingController {

    private final Logger logger = LoggerFactory.getLogger(CrawlingController.class);
    private final CrawlingService crawlingService;
    @GetMapping("/crawling01")
    public ResponseEntity<List<CrawlingDatas>> crawling01(){
        List<CrawlingDatas>  dataList = new ArrayList<>();
        if(!checkHoliday()){
            logger.info("******controller start******");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*"); // Selenium WebDriver 를 사용하여 다른 도메인의 브라우저에 접근
            chromeOptions.addArguments("--headless=new"); // GUI 없는 Headless 모드로 실행 (필요에 따라 제외 가능)
            chromeOptions.addArguments("--no-sandbox"); // Sandbox 모드 비활성화 -> 호환성 문제를 해결하고 Chrome 실행의 안정성을 높이기 위해
            chromeOptions.addArguments("--disable-dev-shm-usage"); // /dev/shm 사용 비활성화 -> Docker 컨테이너 환경에서 Chrome 실행 시 메모리 제한 관련 문제를 해결

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
            dataList.addAll(crawlingService.crawlerYuanta(driver));

            driver = new ChromeDriver(chromeOptions);
            dataList.addAll(crawlingService.crawlerKiwoom(driver));

            driver = new ChromeDriver(chromeOptions);
            dataList.addAll(crawlingService.crawlerDaishin(driver));
            driver.quit();
            logger.info("******controller end******");
        }

        return ResponseEntity.status(HttpStatus.OK).body(dataList);

    }
    public boolean checkHoliday(){
        boolean isHoliday = false;
        // 인증키 발급 2023년 7월 27일 by 권용휘
        // 2년마다 갱신해야함. https://www.data.go.kr/ -> 특일 api
        String apiKey = "0ADwRCUVapjZESe5Rn%2FmqFl%2BkDOlhdVjMLw1GNQlirntPxKM1igWpz4U5UNztVOQi9vtfKyd475P5rifmppnlA%3D%3D";
        String apiUrl = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";

        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(today.getTime());

        try {
            // API 호출
//            URL url = new URL(apiUrl + "?ServiceKey=" + apiKey + "&solYear=" + date.substring(0, 4) + "&solMonth=09&_type=json");
            URL url = new URL(apiUrl + "?ServiceKey=" + apiKey + "&solYear=" + date.substring(0, 4) + "&solMonth=" + date.substring(4, 6)+"&_type=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // API 호출 결과 읽기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // API 결과 파싱
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
            JsonObject body = jsonObject.getAsJsonObject("response").getAsJsonObject("body");
            if(!body.get("items").toString().equals("\"\"")){
                String day = "";
                if(body.getAsJsonObject("items").get("item").isJsonObject()){
                    day = body.getAsJsonObject("items").getAsJsonObject("item").get("locdate").toString();
                    if(day.equals(date)) isHoliday = true;
                }else{
                    JsonArray itemArray = body.getAsJsonObject("items").getAsJsonArray("item");
                    for (JsonElement element : itemArray) {
                        JsonObject itemObject = element.getAsJsonObject();
                        day = itemObject.get("locdate").toString();
                        if(day.equals(date)) {
                            isHoliday = true;
                            break;
                        }
                    }
                }
            }else{
                isHoliday = false;
            }
            if (isHoliday) {
                logger.info("오늘은 공휴일입니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return isHoliday;
    }
}

