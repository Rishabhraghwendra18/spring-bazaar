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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private InventoryRepository inventoryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtil jwtUtil;

    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        reviewService = new ReviewService(reviewRepository, inventoryRepository, userRepository, jwtUtil);
    }

    @Test
    void saveReview_Success() {
        // Arrange
        String token = "validToken";
        String userId = "user123";
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setRating(5);
        reviewRequest.setComment("Great product!");
        reviewRequest.setItemId(1);

        UsersEntity user = new UsersEntity();
        user.setEmail(userId);

        InventoryEntity item = new InventoryEntity();
        item.setId(1);

        when(jwtUtil.getSubjectFromToken(token)).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reviewRepository.findByUserId(user)).thenReturn(null);
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(item));
        when(reviewRepository.save(any(ReviewEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        ReviewEntity result = reviewService.saveReview(reviewRequest, token);

        // Assert
        assertNotNull(result);
        assertEquals(5, result.getRating());
        assertEquals("Great product!", result.getComment());
        assertEquals(user, result.getUserId());
        assertEquals(item, result.getInventoryItemId());

        verify(reviewRepository).save(any(ReviewEntity.class));
    }

    @Test
    void saveReview_UserNotFound() {
        // Arrange
        String token = "validToken";
        String userId = "user123";
        ReviewRequest reviewRequest = new ReviewRequest();

        when(jwtUtil.getSubjectFromToken(token)).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> reviewService.saveReview(reviewRequest, token));
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getErrorCode());
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void saveReview_UserAlreadyReviewed() {
        // Arrange
        String token = "validToken";
        String userId = "user123";
        ReviewRequest reviewRequest = new ReviewRequest();

        UsersEntity user = new UsersEntity();
        user.setEmail(userId);

        when(jwtUtil.getSubjectFromToken(token)).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reviewRepository.findByUserId(user)).thenReturn(new ReviewEntity());

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> reviewService.saveReview(reviewRequest, token));
        assertEquals(HttpStatus.ALREADY_REPORTED.value(), exception.getErrorCode());
        assertEquals("User already reviewed the product", exception.getMessage());
    }

    @Test
    void saveReview_ItemNotFound() {
        // Arrange
        String token = "validToken";
        String userId = "user123";
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setItemId(1);

        UsersEntity user = new UsersEntity();
        user.setEmail(userId);

        when(jwtUtil.getSubjectFromToken(token)).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(reviewRepository.findByUserId(user)).thenReturn(null);
        when(inventoryRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> reviewService.saveReview(reviewRequest, token));
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getErrorCode());
        assertEquals("Item not found", exception.getMessage());
    }

    @Test
    void getTopReviewsForProduct_Success() {
        // Arrange
        Integer productId = 1;
        int limit = 5;

        InventoryEntity item = new InventoryEntity();
        item.setId(productId);

        List<ReviewEntity> expectedReviews = Arrays.asList(
                new ReviewEntity(), new ReviewEntity(), new ReviewEntity()
        );

        when(inventoryRepository.findById(productId)).thenReturn(Optional.of(item));
        when(reviewRepository.findByInventoryItemIdOrderByRatingDesc(eq(item), any(PageRequest.class)))
                .thenReturn(expectedReviews);

        // Act
        List<ReviewEntity> result = reviewService.getTopReviewsForProduct(productId, limit);

        // Assert
        assertEquals(expectedReviews, result);
        verify(reviewRepository).findByInventoryItemIdOrderByRatingDesc(eq(item), eq(PageRequest.of(0, limit)));
    }

    @Test
    void getTopReviewsForProduct_ItemNotFound() {
        // Arrange
        Integer productId = 1;
        int limit = 5;

        when(inventoryRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> reviewService.getTopReviewsForProduct(productId, limit));
        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getErrorCode());
        assertEquals("Item with item id: 1 not found", exception.getMessage());
    }
}