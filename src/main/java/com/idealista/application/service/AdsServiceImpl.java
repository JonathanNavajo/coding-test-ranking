package com.idealista.application.service;

import com.idealista.application.entity.AdVO;
import com.idealista.application.entity.PublicAd;
import com.idealista.application.entity.QualityAd;
import com.idealista.application.entity.ScoreAd;
import com.idealista.application.mapper.AdsMapper;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements IAdsService {

    @Autowired
    private InMemoryPersistence inMemoryPersistence;

    @Override
    public List<PublicAd> findPublicAds() {

        // Sort AdsList based on the score
        Collections.sort(inMemoryPersistence.getAds(), (o1, o2) -> new Integer(o2.getScore()).compareTo(new Integer(o1.getScore())));

        return inMemoryPersistence.getAds()
                .stream()
                .filter(item -> item.getScore() != null && item.getScore() > 40)
                .map(item -> AdsMapper.mapAdVOToPublicAd(item, inMemoryPersistence.getPictures()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QualityAd> findQualityAds() {

        return inMemoryPersistence.getAds()
                .stream()
                .filter(item -> item.getScore() != null && item.getScore() < 40)
                .map(item -> AdsMapper.mapAdVOToQualityAd(item, inMemoryPersistence.getPictures()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScoreAd> calculateScore() {
        return null;
    }

}
