package com.springbazaar.server.services;

import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HomeServiceTest {
    private HomeService homeServiceTest;

    @Mock
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp(){
        homeServiceTest = new HomeService(inventoryRepository);
    }
    @Test
    public void testGetAllInventoryItems() {
        InventoryEntity item1 = new InventoryEntity();
        InventoryEntity item2 = new InventoryEntity();
        List<InventoryEntity> items = Arrays.asList(item1, item2);

        when(inventoryRepository.findAll()).thenReturn(items);

        List<InventoryEntity> result = homeServiceTest.getAllInventoryItems();

        assertEquals(2, result.size());
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    public void testGetTopInventoryItems() {
        InventoryEntity item1 = new InventoryEntity();
        InventoryEntity item2 = new InventoryEntity();
        List<InventoryEntity> items = Arrays.asList(item1, item2);
        Pageable pageable = PageRequest.of(0, 2);

        when(inventoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(items, pageable, items.size()));

        List<InventoryEntity> result = homeServiceTest.getTopInventoryItems(2);

        assertEquals(2, result.size());
        verify(inventoryRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetItemById() {
        InventoryEntity item = new InventoryEntity();
        item.setId(1);
        Optional<InventoryEntity> optionalItem = Optional.of(item);

        when(inventoryRepository.findById(1)).thenReturn(optionalItem);

        InventoryEntity result = homeServiceTest.getItemById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(inventoryRepository, times(1)).findById(1);
    }
    @Test
    public void testGetItemById_NotFound() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.empty());

        InventoryEntity result = homeServiceTest.getItemById(1);

        assertNull(result);
        verify(inventoryRepository, times(1)).findById(1);
    }

    @Test
    public void testSearchProductsByTitle() {
        InventoryEntity item1 = new InventoryEntity();
        item1.setItemTitle("Test Item");
        InventoryEntity item2 = new InventoryEntity();
        item2.setItemTitle("Another Item");
        List<InventoryEntity> items = Arrays.asList(item1, item2);

        when(inventoryRepository.findByItemTitleContainingIgnoreCase("item")).thenReturn(items);

        List<InventoryEntity> result = homeServiceTest.searchProductsByTitle("item");

        assertEquals(2, result.size());
        verify(inventoryRepository, times(1)).findByItemTitleContainingIgnoreCase("item");
    }
}