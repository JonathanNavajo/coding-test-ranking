package com.idealista.application.mapper;

import com.idealista.application.entity.AdVO;
import com.idealista.application.entity.PictureVO;
import com.idealista.application.entity.PublicAd;
import com.idealista.application.entity.QualityAd;

import java.util.List;
import java.util.stream.Collectors;

public class AdsMapper {

    public static PublicAd mapAdVOToPublicAd(AdVO adVO, List<PictureVO> pictureVOS) {
        return new PublicAd(adVO.getId(), adVO.getTypology(), adVO.getDescription(), mapPicturesIntegerToString(adVO.getPictures(), pictureVOS)
                , adVO.getHouseSize(), adVO.getGardenSize());
    }

    public static QualityAd mapAdVOToQualityAd(AdVO adVO, List<PictureVO> pictureVOS) {
        return new QualityAd(adVO.getId(), adVO.getTypology(), adVO.getDescription(), mapPicturesIntegerToString(adVO.getPictures(), pictureVOS)
                , adVO.getHouseSize(), adVO.getGardenSize(), adVO.getScore(), adVO.getIrrelevantSince());
    }

    private static List<String> mapPicturesIntegerToString(List<Integer> pictures, List<PictureVO> pictureVOS) {
        return pictures.stream().map(item -> {
            return pictureVOS.stream().filter(pictureVO -> pictureVO.getId().equals(item)).map(PictureVO::getUrl).findFirst().orElse(null);
        }).collect(Collectors.toList());
    }
}
