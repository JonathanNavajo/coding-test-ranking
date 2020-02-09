package com.idealista.application.service;

import com.idealista.application.entity.AdVO;
import com.idealista.application.entity.PictureVO;
import com.idealista.application.entity.PublicAd;
import com.idealista.application.entity.QualityAd;
import com.idealista.application.mapper.AdsMapper;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
    public List<AdVO> calculateScore() {
        return inMemoryPersistence.getAds().stream()
                .map(item -> {
                    Integer score = 0;
                    score += checkComplete(item);
                    score += checkDescription(item);
                    score += checkPhotos(item);
                    item.setScore(score);
                    return item;
                }).collect(Collectors.toList());
    }

    private Integer checkComplete(AdVO adVO) {

        if (((adVO.getTypology().equals("PISO") && adVO.getHouseSize() > 0)
                || (adVO.getTypology().equals("CHALET") && adVO.getHouseSize() > 0 && adVO.getGardenSize() > 0))
                    && (adVO.getTypology().equals("GARAJE") && adVO.getDescription().length() == 0 || adVO.getDescription().length() != 0)
                    && adVO.getPictures().size() > 0)
            return 40;

        return 0;
    }

    private Integer checkDescription(AdVO adVO) {


        return score;
    }

    private Integer checkPhotos(AdVO adVO) {
        Integer score = 0;
        if(adVO.getPictures().size() == 0){
            score -= 10;
        }else{
            Integer score = 0;
            adVO.getPictures().stream().map(item ->{
                String quality = inMemoryPersistence.getPictures().stream().filter(pictureVO -> pictureVO.getId().equals(item))
                        .map(PictureVO::getQuality).findFirst().orElse(null);
                score += quality.equals("HD") ? 20 : 10;
            })
        }

        return score;
    }

}
