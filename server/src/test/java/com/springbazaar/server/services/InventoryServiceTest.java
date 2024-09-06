package com.springbazaar.server.services;

import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.exceptionHandlers.ApplicationException;
import com.springbazaar.server.repository.InventoryRepository;
import com.springbazaar.server.utils.CloudinaryUploadFiles;
import com.springbazaar.server.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {
    private InventoryService inventoryServiceTest;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private InventoryRepository inventoryRepository;
    @Mock
    private CloudinaryUploadFiles cloudinaryUploadFiles;

    @BeforeEach
    void setUp(){
        inventoryServiceTest = new InventoryService(inventoryRepository,jwtUtil,cloudinaryUploadFiles);
    }

    @Test
    public void testGetAllSellerProducts() {
        String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        String sellerId = "seller123";
        List<InventoryEntity> items = Arrays.asList(new InventoryEntity(), new InventoryEntity());

        when(jwtUtil.getSubjectFromToken(jwtToken)).thenReturn(sellerId);
        when(inventoryRepository.findAllBySellerId(sellerId)).thenReturn(items);

        List<InventoryEntity> result = inventoryServiceTest.getAllSellerProducts(jwtToken);

        assertEquals(2, result.size());
        verify(jwtUtil, times(1)).getSubjectFromToken(jwtToken);
        verify(inventoryRepository, times(1)).findAllBySellerId(sellerId);
    }

    @Test
    public void testGetAllSellerProducts_DataAccessException() {
        String jwtToken = "validJwtToken";
        String sellerId = "seller123";

        when(jwtUtil.getSubjectFromToken(jwtToken)).thenReturn(sellerId);
        when(inventoryRepository.findAllBySellerId(sellerId)).thenThrow(new DataAccessException("Error") {});

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            inventoryServiceTest.getAllSellerProducts(jwtToken);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getErrorCode());
        verify(jwtUtil, times(1)).getSubjectFromToken(jwtToken);
        verify(inventoryRepository, times(1)).findAllBySellerId(sellerId);
    }

    @Test
    public void testGetProductById() {
        int productId = 1;
        InventoryEntity item = new InventoryEntity();
        item.setId(productId);

        when(inventoryRepository.findById(productId)).thenReturn(Optional.of(item));

        InventoryEntity result = inventoryServiceTest.getProductById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        verify(inventoryRepository, times(1)).findById(productId);
    }

    @Test
    public void testGetProductById_NotFound() {
        int productId = 1;

        when(inventoryRepository.findById(productId)).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            inventoryServiceTest.getProductById(productId);
        });

        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getErrorCode());
        assertEquals("Product with id: 1 not found", exception.getMessage());
        verify(inventoryRepository, times(1)).findById(productId);
    }

    @Test
    public void testAddProduct() throws Exception {
        String jwtToken = "validJwtToken";
        String sellerId = "seller123";
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test image".getBytes());
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setItemTitle("Test Item");
        String filePath = "http://cloudinary.com/test.png";

        when(jwtUtil.getSubjectFromToken(jwtToken)).thenReturn(sellerId);
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(inventoryEntity);
        when(cloudinaryUploadFiles.uploadFile(sellerId,file)).thenReturn(filePath);

        InventoryEntity result = inventoryServiceTest.addProduct(file, inventoryEntity, jwtToken);

        assertNotNull(result);
        assertEquals(sellerId, result.getSellerId());
        assertEquals(filePath, result.getItemPhoto());
        verify(jwtUtil, times(1)).getSubjectFromToken(jwtToken);
        verify(inventoryRepository, times(1)).save(any(InventoryEntity.class));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        int productId = 1;
        String sellerId = "seller123";
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test image".getBytes());
        InventoryEntity existingItem = new InventoryEntity();
        existingItem.setId(productId);
        existingItem.setSellerId(sellerId);
        String filePath = "http://cloudinary.com/test.png";

        when(inventoryRepository.findById(productId)).thenReturn(Optional.of(existingItem));
        when(inventoryRepository.save(any(InventoryEntity.class))).thenReturn(existingItem);
        when(cloudinaryUploadFiles.uploadFile(sellerId,file)).thenReturn(filePath);
//        doReturn(filePath).when(inventoryServiceTest).uploadFile(anyString(), any(MultipartFile.class));

        InventoryEntity result = inventoryServiceTest.updateProduct(file, productId, 10, "Updated description", 100.0f, "Updated title");

        assertNotNull(result);
        assertEquals(10, result.getItemQuantity());
        assertEquals("Updated description", result.getItemDescription());
        assertEquals(100.0f, result.getItemPrice());
        assertEquals("Updated title", result.getItemTitle());
        assertEquals(filePath, result.getItemPhoto());
        verify(inventoryRepository, times(1)).findById(productId);
        verify(inventoryRepository, times(1)).save(any(InventoryEntity.class));
    }

    @Test
    public void testRemoveProduct() {
        int productId = 1;
        InventoryEntity item = new InventoryEntity();
        item.setId(productId);

        when(inventoryRepository.findById(productId)).thenReturn(Optional.of(item));

        InventoryEntity result = inventoryServiceTest.removeProduct(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        verify(inventoryRepository, times(1)).findById(productId);
        verify(inventoryRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testRemoveProduct_NotFound() {
        int productId = 1;

        when(inventoryRepository.findById(productId)).thenReturn(Optional.empty());

        InventoryEntity result = inventoryServiceTest.removeProduct(productId);

        assertNull(result);
        verify(inventoryRepository, times(1)).findById(productId);
        verify(inventoryRepository, times(0)).deleteById(productId);
    }
}