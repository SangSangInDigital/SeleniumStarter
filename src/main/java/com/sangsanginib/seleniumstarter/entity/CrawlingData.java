package com.sangsanginib.seleniumstarter.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.time.LocalDateTime;


@Table(name = "crawling_data")
@Getter
@Setter
public class CrawlingData {
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
