package com.idealista.application.mapper;

import com.idealista.application.entity.AdVO;
import com.idealista.application.entity.PictureVO;
import com.idealista.application.entity.PublicAd;
import com.idealista.application.entity.QualityAd;

import java.util.List;
import java.util.stream.Collectors;

public class AdsMapper {

    public static PublicAd mapAdVOToPublicAd(AdVO adVO, List<PictureVO> pictureVOS) {
        return PublicAd.builder()
                .id(adVO.getId())
                .typology(adVO.getTypology())
                .description(adVO.getDescription())
                .pictureUrls(mapPicturesIntegerToString(adVO.getPictures(), pictureVOS))
                .houseSize(adVO.getHouseSize())
                .gardenSize(adVO.getGardenSize())
                .build();
    }

    public static QualityAd mapAdVOToQualityAd(AdVO adVO, List<PictureVO> pictureVOS) {
        return QualityAd.builder()
                .id(adVO.getId())
                .typology(adVO.getTypology())
                .description(adVO.getDescription())
                .pictureUrls(mapPicturesIntegerToString(adVO.getPictures(), pictureVOS))
                .houseSize(adVO.getHouseSize())
                .gardenSize(adVO.getGardenSize())
                .score(adVO.getScore())
                .irrelevantSince(adVO.getIrrelevantSince())
                .build();
    }

    private static List<String> mapPicturesIntegerToString(List<Integer> pictures, List<PictureVO> pictureVOS) {
        return pictures.stream()
                .map(item -> pictureVOS.stream().filter(pictureVO -> pictureVO.getId().equals(item))
                        .map(PictureVO::getUrl).findFirst().orElse(null)).collect(Collectors.toList());
    }
}
