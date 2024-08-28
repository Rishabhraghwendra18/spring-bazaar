package com.springbazaar.server.controllers;

import com.springbazaar.server.entities.ReviewEntity;
import com.springbazaar.server.requestresponse.ReviewRequest;
import com.springbazaar.server.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class Review {
    private final ReviewService reviewService;

    @Autowired
    public Review(ReviewService reviewService){
        this.reviewService=reviewService;
    }
    @PostMapping
    public ResponseEntity<ReviewEntity> addReview(@RequestHeader("Authorization") String jwtToken, @RequestBody ReviewRequest reviewRequest) {
        // Logic to save review
        ReviewEntity savedReview = reviewService.saveReview(reviewRequest,jwtToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewEntity>> getReviewsByProduct(@PathVariable Integer productId,@RequestParam(required = false,defaultValue = "4") Integer limit) {
        List<ReviewEntity> reviews = reviewService.getTopReviewsForProduct(productId, limit);
        return ResponseEntity.ok(reviews);
    }
}
