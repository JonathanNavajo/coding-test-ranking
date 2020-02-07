package com.idealista.infrastructure.controllers;

import com.idealista.application.entity.PublicAd;
import com.idealista.application.entity.QualityAd;
import com.idealista.application.entity.ScoreAd;
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
        return new ResponseEntity<List<QualityAd>>(adsService.findQualityAds(), HttpStatus.OK);
    }

    @GetMapping("/publicAds")
    public ResponseEntity<List<PublicAd>> publicListing() {
        //TODO rellena el cuerpo del método
        return new ResponseEntity<List<PublicAd>>(adsService.findPublicAds(), HttpStatus.OK);
    }

    @GetMapping("/scoreAds")
    public ResponseEntity<List<ScoreAd>> calculateScore() {
        //TODO rellena el cuerpo del método
        return new ResponseEntity<List<ScoreAd>>(adsService.calculateScore(), HttpStatus.OK);
    }
}
