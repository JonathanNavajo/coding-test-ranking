package com.idealista.application.service;

import com.idealista.application.entity.AdVO;
import com.idealista.application.entity.PublicAd;
import com.idealista.application.entity.QualityAd;

import java.util.List;

public interface IAdsService {

    public List<PublicAd> findPublicAds();

    public List<QualityAd> findQualityAds();

    public List<AdVO> calculateScore();
}
