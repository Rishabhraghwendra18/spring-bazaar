package com.springbazaar.server.services;

import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.entities.ReviewEntity;
import com.springbazaar.server.entities.UsersEntity;
import com.springbazaar.server.exceptionHandlers.ApplicationException;
import com.springbazaar.server.repository.InventoryRepository;
import com.springbazaar.server.repository.ReviewRepository;
import com.springbazaar.server.repository.UserRepository;
import com.springbazaar.server.requestresponse.ReviewRequest;
import com.springbazaar.server.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, InventoryRepository inventoryRepository, UserRepository userRepository,JwtUtil jwtUtil){
        this.reviewRepository=reviewRepository;
        this.inventoryRepository=inventoryRepository;
        this.userRepository=userRepository;
        this.jwtUtil=jwtUtil;
    }
    public ReviewEntity saveReview(ReviewRequest reviewRequest, String token) {
        String userId = jwtUtil.getSubjectFromToken(token);

        ReviewEntity review = new ReviewEntity();
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());

        Optional<UsersEntity> userOptional = userRepository.findById(userId);
        UsersEntity user = userOptional.orElseThrow(() ->  new ApplicationException(HttpStatus.NOT_FOUND.value(), "User not found"));
        ReviewEntity userReview = reviewRepository.findByUserId(user);
        if (userReview != null){
            throw new ApplicationException(HttpStatus.ALREADY_REPORTED.value(), "User already reviewed the product");
        }

        Optional<InventoryEntity> itemOptional = inventoryRepository.findById(reviewRequest.getItemId());
        InventoryEntity item = itemOptional.orElseThrow(() ->  new ApplicationException(HttpStatus.NOT_FOUND.value(), "Item not found"));

        review.setUserId(user);
        review.setInventoryItemId(item);
        return reviewRepository.save(review);
    }

    public List<ReviewEntity> getTopReviewsForProduct(Integer productId, int limit) {
        // Fetch up to 'limit' reviews for the given product, ordered by created_at DESC
        Optional<InventoryEntity> itemOptional = inventoryRepository.findById(productId);
        InventoryEntity item = itemOptional.orElseThrow(()-> new ApplicationException(HttpStatus.NOT_FOUND.value(), "Item with item id: "+productId+" not found"));
        Pageable pageable = PageRequest.of(0, limit);
        return reviewRepository.findByInventoryItemIdOrderByRatingDesc(item,pageable);
    }
}
