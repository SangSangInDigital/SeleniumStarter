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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlingService {
    private final Logger logger = LoggerFactory.getLogger(CrawlingService.class);

    public List<CrawlingDatas> crawlerPage01(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        try {

            // 크롤링 작업 수행
            driver.get("https://truefriend.com/main/mall/opendecision/DecisionInfo.jsp?cmd=TF02da010100");

            Thread.sleep(10000);

            //데이터 크롤링
            List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"content\"]/div[2]/div/div[2]/table/tbody/tr"));

            int size = webElements.size();
            logger.info("****Before 한국투자증권 crawling****");

            for (int i = 1; i < size + 1; i += 2) {
                //종목명
                String fdName = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i + "]/td[2]/strong")).getText();
                //만기일
                String exDt = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[2]/div/div[2]/table/tbody/tr[" + i + "]/td[4]")).getText();
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

    public List<CrawlingDatas> crawlerPage02(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        try {
            driver.get("https://www.shinhansec.com/siw/wealth-management/bond-rp/5901/view.do");
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
                String fdName = driver.findElement(By.xpath("//*[@id=\"list\"]/tbody/tr["+i+"]/td[2]/a")).getText();
                //만기일
                String exDt = driver.findElement(By.xpath("//*[@id=\"main\"]/table/tbody/tr[" + i + "]/td[3]")).getText();
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



    public List<CrawlingDatas> crawlerPage03(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        try{
            driver.get("https://www.samsungpop.com/?MENU_CODE=M1231752589437");
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
                String exDt = splitStrings[1].replaceAll("-",".").substring(1,splitStrings[1].length()-1);
                String rmnngDays = splitStrings[0];
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

        }catch (Exception e){
            logger.error("====삼성투자증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====삼성투자증권 crawling error end====");
        }
        return list;
    }

    public List<CrawlingDatas> crawlerPage04(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        try{
            driver.get("https://securities.miraeasset.com/hks/hks4036/r01.do");

            List<WebElement> webElements = driver.findElements(By.xpath("//*[@id=\"list\"]/tbody/tr"));
            int size = webElements.size();

            logger.info("****Before 미래에셋 crawling****");
            for (int i = 1; i < size + 1; i++) {
                //종목명
                String fdName = driver.findElement(By.xpath("//*[@id=\"list\"]/tbody/tr["+i+"]/td[2]/a")).getText();
                //만기일
                String exDt = driver.findElement(By.xpath("//*[@id=\"list\"]/tbody/tr["+i+"]/td[5]")).getText();
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

        }catch (Exception e){
            logger.error("====삼성투자증권 crawling error start====");
            logger.error(e.toString());
            logger.error(e.getMessage());
            logger.error("====삼성투자증권 crawling error end====");
        }
        return list;
    }

    public List<CrawlingDatas> crawlerPage05(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        driver.get("https://www.kbsec.com/go.able?linkcd=s010602010000");
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

    public List<CrawlingDatas> crawlerPage07(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        driver.get("https://www.kiwoom.com/wm/bnd/od010/bndOdListView");
        // TO DO:

        return list;
    }

    public List<CrawlingDatas> crawlerPage08(ChromeDriver driver) {
        List<CrawlingDatas> list = new ArrayList<>();
        driver.get("https://www.daishin.com/g.ds?m=1019&p=1210&v=797");
        // TO DO:

        return list;
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
