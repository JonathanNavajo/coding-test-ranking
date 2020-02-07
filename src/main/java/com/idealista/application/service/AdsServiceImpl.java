package com.idealista.application.service;

import com.idealista.application.entity.PublicAd;
import com.idealista.application.entity.QualityAd;
import com.idealista.application.entity.ScoreAd;
import com.idealista.application.mapper.PublicAdsMapper;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements IAdsService {

    @Autowired
    private InMemoryPersistence inMemoryPersistence;

    private PublicAdsMapper publicAdsMapper;

    @Override
    public List<PublicAd> findPublicAds() {
        return inMemoryPersistence.getAds().stream().filter(item -> item.getScore() != null && item.getScore() > 40)
                .map(item -> publicAdsMapper.mapAdVOToPublicAd(item, inMemoryPersistence.getPictures())).collect(Collectors.toList());
    }

    @Override
    public List<QualityAd> findQualityAds() {
        return null;
    }

    @Override
    public List<ScoreAd> calculateScore() {
        return null;
    }
}
