package com.sangsanginib.seleniumstarter.dto;

@lombok.Data
public class CrawlingDatas {
    //회사명
    private String company;
    //종목명
    private String fdName;
    //만기일
    private String exDt;
    //잔존기간
    private String rmnngDays;
    //매수수익률
    private String rtrnRate;
    //예금환산수익률
    private String dpstCnvrsRtrnRate;
    //세후수익률
    private String taxrtRate;
    //신용등급
    private String crdtRtng;
}
