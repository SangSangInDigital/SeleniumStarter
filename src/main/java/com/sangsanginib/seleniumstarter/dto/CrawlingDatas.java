package com.sangsanginib.seleniumstarter.dto;

import java.time.LocalDateTime;
@lombok.Data
public class CrawlingDatas {
    private String company;
    private String fdName;
    private String exDt;
    private int rmnngDays;
    private String rtrnRate;
    private String dpstCnvrsRtrnRate;
    private String taxrtRate;
    private String crdtRtng;
    private LocalDateTime regdt;
    private String delYn;
}
