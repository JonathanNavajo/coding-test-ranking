package com.idealista.application.mapper;

import com.idealista.application.entity.AdVO;
import com.idealista.application.entity.PictureVO;
import com.idealista.application.entity.PublicAd;

import java.util.List;
import java.util.stream.Collectors;

public class PublicAdsMapper {

    public PublicAd mapAdVOToPublicAd(AdVO adVO, List<PictureVO> pictureVOS) {
        return new PublicAd(adVO.getId(), adVO.getTypology(), adVO.getDescription(), mapPicturesIntegerToString(adVO.getPictures(), pictureVOS)
                , adVO.getHouseSize(), adVO.getGardenSize());
    }

    private List<String> mapPicturesIntegerToString(List<Integer> pictures, List<PictureVO> pictureVOS) {
        return pictures.stream().map(item -> {
            return pictureVOS.stream().filter(pictureVO -> pictureVO.getId().equals(item)).map(PictureVO::getUrl).findFirst().orElse(null);
        }).collect(Collectors.toList());
    }
}
