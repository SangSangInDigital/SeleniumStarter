package com.sangsanginib.seleniumstarter.repository;

import com.sangsanginib.seleniumstarter.entity.CrawlingData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CrawlingRepository {
    private final EntityManager em;

    public void save(CrawlingData crawlingData){em.persist(crawlingData);}


}
