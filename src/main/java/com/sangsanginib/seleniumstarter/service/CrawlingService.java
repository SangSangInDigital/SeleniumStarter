package com.sangsanginib.seleniumstarter.service;

import com.sangsanginib.seleniumstarter.dto.CrawlingDatas;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final Logger logger = LoggerFactory.getLogger(CrawlingService.class);

    public List<CrawlingDatas> crawlerTruefriend(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        try {

            // 크롤링 작업 수행
            driver.get("https://truefriend.com/main/mall/opendecision/DecisionInfo.jsp?cmd=TF02da010100");

            Thread.sleep(1000);

            //데이터 크롤링
            List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div/div[2]/table/tbody/tr"));

            int size = webElements.size();
            logger.info("****Before 한국투자증권 crawling****");

            for (int i = 1; i < size + 1; i += 2) {
                //종목명
                String fdName = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i + "]/td[2]/strong")).getText();
                //만기일
                String exDt = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i + "]/td[4]")).getText().replaceAll("\\.","-");
                //잔존기간
                String rmnngDays = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + (i + 1) + "]/td[1]")).getText();
                //매수수익률
                String rtrnRate = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + (i + 1) + "]/td[2]")).getText();
                rtrnRate = splitPercent(rtrnRate);
                //예금환산수익률
                String dpstCnvrsRtrnRate = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i + "]/td[8]")).getText();
                dpstCnvrsRtrnRate = splitPercent(dpstCnvrsRtrnRate);
                //세후수익률
                String taxrtRate = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + (i + 1) + "]/td[3]")).getText();
                taxrtRate = splitPercent(taxrtRate);
                //신용등급
                String rareString = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i + "]/td[2]")).getText();
                String[] splitStrings = rareString.split("[:,\\n]");
                String crdtRtng = splitStrings[splitStrings.length-2].trim();


                logger.info("한국투자증권=="+fdName);

                CrawlingDatas data  = setData("한국투자증권",fdName,exDt,rmnngDays,rtrnRate,dpstCnvrsRtrnRate,taxrtRate,crdtRtng);

                list.add(data);
            }
            logger.info("****after 한국투자증권 crawling****");
            // WebDriver 종료
            driver.quit();
        } catch (Exception e) {
            logger.error("====한국투자증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====한국투자증권 crawling error end====");
        }
        return list;
    }

    public List<CrawlingDatas> crawlerShinhan(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        try {
            driver.get("https://www.shinhansec.com/siw/wealth-management/bond-rp/5901/view.do");
            Thread.sleep(1000);
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        }catch (Exception e){
            logger.error("====신한투자증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====신한투자증권 crawling error end====");
        }
        int maxAttempts = 10;
        int attemptCount = 0;

        while (attemptCount < maxAttempts) {
            try {
                 // 더보기 버튼 클릭
                WebElement element = driver.findElement(By.xpath("//*[@id=\"main\"]/button"));
                if (element.isDisplayed() && element.isEnabled()) {
                    element.click();
                    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
                    attemptCount++;
                } else {
                    break;
                }

            } catch (org.openqa.selenium.NoSuchElementException e) {
                logger.error("====신한투자증권 crawling error start====");
                logger.error(e.toString());
                logger.error(e.getMessage());
                logger.error("====신한투자증권 crawling error end====");
                break; // 루프 종료
            }
        }
        try {
            List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"main\"]/table/tbody/tr"));
            logger.info("****Before 신한투자증권 crawling****");
            int size = webElements.size();
            for (int i = 1; i < size + 1; i++) {
                //종목명
                String fdName = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr["+i+"]/td[2]/a")).getText();
                //만기일
                String exDt = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[" + i + "]/td[3]")).getText().replaceAll("\\.","-");
                //잔존기간
                String rmnngDays = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[" + i + "]/td[4]")).getText();
                //매수수익률
                String rtrnRate = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[" + i + "]/td[5]")).getText();
                rtrnRate = splitPercent(rtrnRate);
                //예금환산수익률
                String dpstCnvrsRtrnRate = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[" + i + "]/td[7]")).getText();
                dpstCnvrsRtrnRate = splitPercent(dpstCnvrsRtrnRate);
                //세후수익률
                String taxrtRate = "";
                //신용등급
                String crdtRtng = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[" + i + "]/td[8]")).getText();

                logger.info("신한투자증권==" + fdName);

                CrawlingDatas data = setData("신한투자증권", fdName, exDt, rmnngDays, rtrnRate, dpstCnvrsRtrnRate, taxrtRate, crdtRtng);
                list.add(data);

            }
            logger.info("****After 신한투자증권 crawling****");
        }catch (Exception e){
            logger.error("====신한투자증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====신한투자증권 crawling error end====");
        }
        return list;
    }



    public List<CrawlingDatas> crawlerSamsung(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        try{
            driver.get("https://www.samsungpop.com/?MENU_CODE=M1231752589437");
            Thread.sleep(1000);
            driver.switchTo().frame("frmContent");
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));


            for(int i =0 ; i<10;i++){
                // 스크롤할 div 요소 가져오기
                WebElement scrollDiv = driver.findElement(By.xpath("//*[@id=\"tab_contents\"]/div/div[1]/div[2]/div"));
                // JavaScript 를 실행하여 스크롤을 제일 밑으로 내리기
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollTo(0, arguments[0].scrollHeight)", scrollDiv);
                Thread.sleep(1000);
            }


            List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"tab_contents\"]/div/div[1]/div[2]/div/table/tbody/tr"));
            logger.info("****Before 삼성증권 crawling****");
            int size = webElements.size();

            for (int i = 1; i < size + 1; i += 3) {
                //종목명
                String fdName = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[3]/div/div/div/div[1]/div[2]/div/table/tbody/tr["+i+"]/td[2]/a")).getText().split("[:,\\n]")[0];
                //만기일, 잔존기간
                String rareString = driver.findElement(By.xpath("//*[@id=\"tab_contents\"]/div/div[1]/div[2]/div/table/tbody/tr["+i+"]/td[3]")).getText();
                String[] splitStrings = rareString.split("[:,\\n]");
                String exDt = splitStrings[1].substring(1,splitStrings[1].length()-1);
                //잔존기간
                String rmnngDays = remainingPeriod(exDt);
                //매수수익률
                String rtrnRate = driver.findElement(By.xpath("//*[@id=\"tab_contents\"]/div/div[1]/div[2]/div/table/tbody/tr["+i+"]/td[4]/span/span")).getText();
                rtrnRate = splitPercent(rtrnRate.substring(1,rtrnRate.length()-1));
                //예금환산수익률
                String dpstCnvrsRtrnRate = driver.findElement(By.xpath("//*[@id=\"tab_contents\"]/div/div[1]/div[2]/div/table/tbody/tr["+i+"]/td[6]")).getText();
                dpstCnvrsRtrnRate = splitPercent(dpstCnvrsRtrnRate);
                //세후수익률
                String taxrtRate = driver.findElement(By.xpath("//*[@id=\"tab_contents\"]/div/div[1]/div[2]/div/table/tbody/tr["+i+"]/td[7]")).getText();
                taxrtRate = splitPercent(taxrtRate);
                //신용등급
                String crdtRtng = driver.findElement(By.xpath("//*[@id=\"tab_contents\"]/div/div[1]/div[2]/div/table/tbody/tr["+i+"]/td[10]")).getText();


                logger.info("삼성증권=="+fdName);

                CrawlingDatas data  = setData("삼성증권",fdName,exDt,rmnngDays,rtrnRate,dpstCnvrsRtrnRate,taxrtRate,crdtRtng);

                list.add(data);
            }
            logger.info("****After 삼성증권 crawling****");
        }catch (Exception e){
            logger.error("====삼성투자증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====삼성투자증권 crawling error end====");
        }
        return list;
    }

    public List<CrawlingDatas> crawlerMirae(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        try{
            driver.get("https://securities.miraeasset.com/hks/hks4036/r01.do");
            Thread.sleep(1000);

            List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"list\"]/tbody/tr"));
            int size = webElements.size();

            logger.info("****Before 미래에셋 crawling****");
            for (int i = 1; i < size + 1; i++) {
                //종목명
                String fdName = driver.findElement(By.xpath("//*[@id=\"list\"]/tbody/tr["+i+"]/td[2]/a")).getText();
                //만기일
                String exDt = driver.findElement(By.xpath("//*[@id=\"list\"]/tbody/tr["+i+"]/td[5]")).getText().replaceAll("\\.","-");
                //잔존기간
                String year = driver.findElement(By.xpath("//*[@id=\"list\"]/tbody/tr["+i+"]/td[3]")).getText();
                String day = driver.findElement(By.xpath("//*[@id=\"list\"]/tbody/tr["+i+"]/td[4]")).getText();
                String rmnngDays ="";
                if(year.equals("0")){
                    rmnngDays = day+"일";
                }else{
                    rmnngDays = year+"년"+day+"일";
                }
                //매수수익률
                String rtrnRate = driver.findElement(By.xpath("//*[@id=\"list\"]/tbody/tr["+i+"]/td[6]")).getText();
                //예금환산수익률
                String dpstCnvrsRtrnRate = "";
                //세후수익률
                String taxrtRate = driver.findElement(By.xpath("//*[@id=\"list\"]/tbody/tr["+i+"]/td[7]")).getText();
                //신용등급
                String crdtRtng = "";

                logger.info("미래에셋증권==" + fdName);

                CrawlingDatas data = setData("미래에셋증권", fdName, exDt, rmnngDays, rtrnRate, dpstCnvrsRtrnRate, taxrtRate, crdtRtng);
                list.add(data);

            }
            logger.info("****After 미래에셋증권 crawling****");
        }catch (Exception e){
            logger.error("====삼성투자증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====삼성투자증권 crawling error end====");
        }
        return list;
    }

    public List<CrawlingDatas> crawlerKb(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();

        try {
            driver.get("https://www.kbsec.com/go.able?linkcd=s010602010000");
            Thread.sleep(1000);
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        }catch (Exception e){
            logger.error("====KB증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====KB증권 crawling error end====");
        }
        int maxAttempts = 10;
        int attemptCount = 0;

        while (attemptCount < maxAttempts) {
            try {
                // 더보기 버튼 클릭
                WebElement element = driver.findElement(By.xpath("//*[@id=\"nextBtn\"]/button/span"));
                if (element.isDisplayed() && element.isEnabled()) {
                    element.click();
                    Thread.sleep(1000);
                    attemptCount++;
                } else {
                    break;
                }

            } catch (Exception e) {
                logger.error("====KB증권 crawling error start====");
                logger.error(e.toString());
                logger.error(e.getMessage());
                logger.error("====KB증권 crawling error end====");
                break; // 루프 종료
            }
        }
        try {
            List<WebElement> webElements = driver.findElements(By.xpath("/html/body/div[2]/div[4]/form[1]/div[3]/div/div/div/table/tbody/tr"));
            logger.info("****Before KB증권 crawling****");
            int size = webElements.size();
            for (int i = 1; i < size + 1; i+=2) {
                //종목명
                String fdName = driver.findElement(By.xpath("/html/body/div[2]/div[4]/form[1]/div[3]/div/div/div/table/tbody/tr["+i+"]/td[1]/a")).getText();
                //만기일
                String exDt = driver.findElement(By.xpath("/html/body/div[2]/div[4]/form[1]/div[3]/div/div/div/table/tbody/tr["+(i+1)+"]/td[2]")).getText().replaceAll("/","-");
                //잔존기간
                String rmnngDays = driver.findElement(By.xpath("/html/body/div[2]/div[4]/form[1]/div[3]/div/div/div/table/tbody/tr["+(i+1)+"]/td[4]")).getText();
                String[] splitDays = rmnngDays.split(",");
                StringBuilder sb = new StringBuilder();
                for(int k=0; k<splitDays.length;k++){
                    sb.append(splitDays[k]);
                }
                int year = Integer.parseInt(sb.toString())/365;
                int days = Integer.parseInt(sb.toString())%365;
                rmnngDays = "";
                if(year!=0){
                    rmnngDays = year+"년";
                }
                rmnngDays = rmnngDays+days+"일";
                //매수수익률
                String rtrnRate = driver.findElement(By.xpath("/html/body/div[2]/div[4]/form[1]/div[3]/div/div/div/table/tbody/tr["+i+"]/td[5]")).getText();
                //예금환산수익률
                String dpstCnvrsRtrnRate = driver.findElement(By.xpath("/html/body/div[2]/div[4]/form[1]/div[3]/div/div/div/table/tbody/tr["+i+"]/td[6]")).getText();
                //세후수익률
                String taxrtRate = driver.findElement(By.xpath("/html/body/div[2]/div[4]/form[1]/div[3]/div/div/div/table/tbody/tr["+(i+1)+"]/td[6]")).getText();
                //신용등급
                String crdtRtng = driver.findElement(By.xpath("/html/body/div[2]/div[4]/form[1]/div[3]/div/div/div/table/tbody/tr["+i+"]/td[7]")).getText();

                logger.info("KB증권==" + fdName);

                CrawlingDatas data = setData("KB증권", fdName, exDt, rmnngDays, rtrnRate, dpstCnvrsRtrnRate, taxrtRate, crdtRtng);
                list.add(data);

            }
            logger.info("****After KB증권 crawling****");
        }catch (Exception e){
            logger.error("====KB증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====KB증권 crawling error end====");
        }


        return list;
    }

    public List<CrawlingDatas> crawlerPage06(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        // 회사채
        driver.get("https://www.myasset.com/myasset/mall/item/bond/MA_0602000_T1.cmd");
        // TO DO:

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        // 국공채
        driver.get("https://www.myasset.com/myasset/mall/item/bond/MA_0602000_T2.cmd");
        // TO DO:
        return list;
    }

    public List<CrawlingDatas> crawlerKiwoom(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();

        try{
            driver.get("https://www.kiwoom.com/wm/bnd/od010/bndOdListView");
            Thread.sleep(1000);

            List<WebElement> webElements = driver.findElements(By.xpath("/html/body/main/section/div/div/div[3]/div[2]/section/div[3]/div[1]/table/tbody/tr"));
            int size = webElements.size();

            logger.info("****Before 키움증권 crawling****");
            for (int i = 1; i < size + 1; i++) {
                //종목명
                String fdName = driver.findElement(By.xpath("//*[@id=\"listTbody\"]/tr["+i+"]/td[1]/div/div[2]/span")).getText();
                //만기일
                String exDt = driver.findElement(By.xpath("//*[@id=\"listTbody\"]/tr["+i+"]/td[7]")).getText().replaceAll("\\.","-");
                //잔존기간
                String rmnngDays = driver.findElement(By.xpath("//*[@id=\"listTbody\"]/tr["+i+"]/td[8]")).getText();
                //매수수익률
                String rtrnRate = driver.findElement(By.xpath("//*[@id=\"listTbody\"]/tr["+i+"]/td[2]")).getText();
                //예금환산수익률
                String dpstCnvrsRtrnRate = driver.findElement(By.xpath("//*[@id=\"listTbody\"]/tr["+i+"]/td[4]")).getText();
                //세후수익률
                String taxrtRate = driver.findElement(By.xpath("//*[@id=\"listTbody\"]/tr["+i+"]/td[3]")).getText();
                //신용등급
                String crdtRtng = driver.findElement(By.xpath("//*[@id=\"listTbody\"]/tr["+i+"]/td[10]")).getText();

                logger.info("키움증권==" + fdName);

                CrawlingDatas data = setData("키움증권", fdName, exDt, rmnngDays, rtrnRate, dpstCnvrsRtrnRate, taxrtRate, crdtRtng);
                list.add(data);

            }
            logger.info("****After 키움증권 crawling****");
        }catch (Exception e){
            logger.error("====키움증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====키움증권 crawling error end====");
        }

        return list;
    }

    public List<CrawlingDatas> crawlerDaishin(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        try{
            driver.get("https://www.daishin.com/g.ds?m=1019&p=1210&v=797");
            Thread.sleep(1000);

            List<WebElement> webElements = driver.findElements(By.xpath("/html/body/div[2]/div[8]/div/div/div/table/tbody/tr"));
            int size = webElements.size();

            logger.info("****Before 대신증권 crawling****");
            for (int i = 1; i < size + 1; i++) {
                //종목명
                String fdName = driver.findElement(By.xpath("//*[@id=\"excel\"]/tbody/tr["+i+"]/td[1]/div/div/p[2]/a[1]")).getText();
                //만기일
                String exDt = driver.findElement(By.xpath("//*[@id=\"excel\"]/tbody/tr["+i+"]/td[3]/div")).getText().split("[:,\\n]")[0].replaceAll("/","-");
                //잔존기간
                String rmnngDays = remainingPeriod(exDt);
                //매수수익률
                String rtrnRate = driver.findElement(By.xpath("//*[@id=\"excel\"]/tbody/tr["+i+"]/td[4]/div/strong")).getText();
                //예금환산수익률
                String dpstCnvrsRtrnRate = driver.findElement(By.xpath("//*[@id=\"excel\"]/tbody/tr["+i+"]/td[7]/div")).getText();
                //세후수익률
                String taxrtRate = driver.findElement(By.xpath("//*[@id=\"excel\"]/tbody/tr["+i+"]/td[6]/div")).getText();
                //신용등급
                String[] crdtRtngArr = driver.findElement(By.xpath("/html/body/div[2]/div[8]/div/div/div/table/tbody/tr["+i+"]/td[1]/div/div/p[1]/img[1]")).getAttribute("alt").split(" ");
                String crdtRtng = "";
                if(crdtRtngArr.length==2){
                    crdtRtng = crdtRtngArr[1];
                }
                logger.info("대신증권==" + fdName);

                CrawlingDatas data = setData("대신증권", fdName, exDt, rmnngDays, rtrnRate, dpstCnvrsRtrnRate, taxrtRate, crdtRtng);
                list.add(data);

            }
            logger.info("****After 대신증권 crawling****");
        }catch (Exception e){
            logger.error("====대신증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====대신증권 crawling error end====");
        }

        return list;
    }

    public String remainingPeriod(String exDt){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate targetDate = LocalDate.parse(exDt, formatter);
        long yearsDifference = ChronoUnit.YEARS.between(currentDate, targetDate);
        long daysDifference = ChronoUnit.DAYS.between(currentDate, targetDate);
        //잔존기간
        String rmnngDays = "";
        if(yearsDifference !=0){
            rmnngDays = yearsDifference + "년";
        }
        rmnngDays = rmnngDays + daysDifference + "일";
        return rmnngDays;
    }

    public String splitPercent(String str){
        if(str.contains("%")){
            str = str.substring(0,str.length()-1);
        }
        return str;
    }

    private CrawlingDatas setData(String company, String fdName, String exDt, String rmnngDays,
                                  String rtrnRate, String dpstCnvrsRtrnRate, String taxrtRate, String crdtRtng) {

        CrawlingDatas data = new CrawlingDatas();

        //회사명
        data.setCompany(company);
        //종목명
        data.setFdName(fdName);
        //만기일
        data.setExDt(exDt);
        //잔존기간
        data.setRmnngDays(rmnngDays);
        //매수수익률
        data.setRtrnRate(rtrnRate);
        //예금환산수익률
        data.setDpstCnvrsRtrnRate(dpstCnvrsRtrnRate);
        //세후수익률
        data.setTaxrtRate(taxrtRate);
        //신용등급
        data.setCrdtRtng(crdtRtng);

        return data;
    }
}
