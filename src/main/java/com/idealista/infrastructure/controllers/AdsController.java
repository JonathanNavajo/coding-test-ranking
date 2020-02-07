package com.idealista.infrastructure.controllers;

import com.idealista.application.entity.PublicAd;
import com.idealista.application.entity.QualityAd;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdsController {

    //TODO añade url del endpoint
    public ResponseEntity<List<QualityAd>> qualityListing() {
        //TODO rellena el cuerpo del método
        return ResponseEntity.notFound().build();
    }

    //TODO añade url del endpoint
    public ResponseEntity<List<PublicAd>> publicListing() {
        //TODO rellena el cuerpo del método
        return ResponseEntity.notFound().build();
    }

    //TODO añade url del endpoint
    public ResponseEntity<Void> calculateScore() {
        //TODO rellena el cuerpo del método
        return ResponseEntity.notFound().build();
    }
}
