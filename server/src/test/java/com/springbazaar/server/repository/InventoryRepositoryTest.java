package com.springbazaar.server.repository;

import com.springbazaar.server.entities.InventoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase
class InventoryRepositoryTest {
    @Container
    @ServiceConnection
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.37")).withUsername("root").withPassword("1").withDatabaseName("spring-bazaar");

    @Autowired
    private InventoryRepository repositoryTest;
    @Test
    void canEstablishConnectionTest(){
        assertTrue(mySQLContainer.isCreated());
        assertTrue(mySQLContainer.isRunning());
    }
    @Test
    public void whenFindBySellerId_thenReturnInventoryList() {
        // Arrange
        InventoryEntity inventory1 = new InventoryEntity();
        inventory1.setSellerId("seller1");
        inventory1.setItemQuantity(10);
        inventory1.setItemTitle("Item1");
        inventory1.setItemDescription("Description1");
        inventory1.setItemPrice(100.0f);
        inventory1.setItemPhoto("photo1.jpg");
        repositoryTest.save(inventory1);

        InventoryEntity inventory2 = new InventoryEntity();
        inventory2.setSellerId("seller1");
        inventory2.setItemQuantity(20);
        inventory2.setItemTitle("Item2");
        inventory2.setItemDescription("Description2");
        inventory2.setItemPrice(150.0f);
        inventory2.setItemPhoto("photo2.jpg");
        repositoryTest.save(inventory2);

        // Act
        List<InventoryEntity> inventoryList = repositoryTest.findAllBySellerId("seller1");

        // Assert
        assertEquals(inventoryList.size(),2);
        assertEquals(inventoryList.get(0).getSellerId(),"seller1");
        assertEquals(inventoryList.get(1).getSellerId(),"seller1");
    }
}