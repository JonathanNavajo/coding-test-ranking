package com.idealista.application.service;

import com.idealista.application.entity.AdVO;
import com.idealista.application.entity.PictureVO;
import com.idealista.application.entity.PublicAd;
import com.idealista.application.entity.QualityAd;
import com.idealista.application.mapper.AdsMapper;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class AdsServiceImpl implements IAdsService {

    @Autowired
    private InMemoryPersistence inMemoryPersistence;

    @Value("${keyWords}")
    private List<String> keyWords;

    /**
     * Method for ordering relevant ads
     * @return relevant ad list
     */

    @Override
    public List<PublicAd> findPublicAds() {

        // Sort list based on the score

        return inMemoryPersistence.getAds()
                .stream()
                .filter(item -> item.getScore() != null && item.getScore() > 40)
                .sorted((o1, o2) -> o2.getScore().compareTo(o1.getScore()))
                .map(item -> AdsMapper.mapAdVOToPublicAd(item, inMemoryPersistence.getPictures()))
                .collect(Collectors.toList());
    }

    /**
     * Method for show irrelevant ads
     * @return irrelevant ad list
     */

    @Override
    public List<QualityAd> findQualityAds() {

        return inMemoryPersistence.getAds()
                .stream()
                .filter(item -> item.getScore() != null && item.getScore() < 40)
                .map(item -> AdsMapper.mapAdVOToQualityAd(item, inMemoryPersistence.getPictures()))
                .collect(Collectors.toList());
    }

    /**
     * Calculate score for all ads
     * @return ad list with score
     */

    @Override
    public List<AdVO> calculateScore() {
        return inMemoryPersistence.getAds().stream()
                .peek(item -> {
                    Integer score = 0;
                    score += checkComplete(item);
                    score += checkDescription(item);
                    score += checkPhotos(item);
                    item.setScore(score);
                }).collect(Collectors.toList());
    }

    private Integer checkComplete(AdVO adVO) {

        if (adVO.getPictures().size() == 0) return 0;

        if (adVO.getDescription().length() > 0 && adVO.getHouseSize() != null && adVO.getHouseSize() > 0 &&
                ((("FLAT").equals(adVO.getTypology())) || ("CHALET").equals(adVO.getTypology()) && adVO.getGardenSize() != null && adVO.getGardenSize() > 0))
            return 40;

        if (("GARAGE").equals(adVO.getTypology())) {
            return 40;
        }

        return 0;
    }

    private Integer checkDescription(AdVO adVO) {

        String[] descriptionSplit = new String[]{};
        if (adVO.getDescription() != null) {
            descriptionSplit = adVO.getDescription().toUpperCase().split("(?=[,.])|\\s+");

        }

        int score = 0;

        if (descriptionSplit.length > 0) score += 5;

        if (("FLAT").equals(adVO.getTypology())) {
            if (descriptionSplit.length >= 20 && descriptionSplit.length < 50) {
                score += 10;
            } else if (descriptionSplit.length >= 50) {
                score += 30;
            }
        } else if ("CHALET".equals(adVO.getTypology())) {
            if (descriptionSplit.length >= 50) {
                score += 20;
            }
        }

        return score + Arrays.stream(descriptionSplit).mapToInt(item -> keyWords.contains(item) ? 5 : 0).sum();

    }

    private Integer checkPhotos(AdVO adVO) {
        AtomicReference<Integer> score = new AtomicReference<>(0);
        if (adVO.getPictures().size() == 0) {
            score.updateAndGet(v -> v - 10);
        } else {
            adVO.getPictures().forEach(item -> {
                String quality = inMemoryPersistence.getPictures().stream().filter(pictureVO -> pictureVO.getId().equals(item))
                        .map(PictureVO::getQuality).findFirst().orElse(null);
                score.updateAndGet(v -> v + (("HD").equals(quality) ? 20 : 10));
            });
        }

        return score.get();
    }
}