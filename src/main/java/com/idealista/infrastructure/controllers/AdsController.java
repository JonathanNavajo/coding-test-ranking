package com.idealista.infrastructure.controllers;

import com.idealista.application.entity.AdVO;
import com.idealista.application.entity.PublicAd;
import com.idealista.application.entity.QualityAd;
import com.idealista.application.service.IAdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdsController {

    @Autowired
    private IAdsService adsService;

    @GetMapping("/qualityAds")
    public ResponseEntity<List<QualityAd>> qualityListing() {
        //TODO rellena el cuerpo del método
        return new ResponseEntity<>(adsService.findQualityAds(), HttpStatus.OK);
    }

    @GetMapping("/publicAds")
    public ResponseEntity<List<PublicAd>> publicListing() {
        //TODO rellena el cuerpo del método
        return new ResponseEntity<>(adsService.findPublicAds(), HttpStatus.OK);
    }

    @GetMapping("/scoreAds")
    public ResponseEntity<List<AdVO>> calculateScore() {
        //TODO rellena el cuerpo del método
        return new ResponseEntity<>(adsService.calculateScore(), HttpStatus.OK);
    }
}
