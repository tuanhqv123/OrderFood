package com.restaurant.orderfood.service.impl;

import com.restaurant.orderfood.model.RestaurantTable;
import com.restaurant.orderfood.repository.RestaurantTableRepository;
import com.restaurant.orderfood.service.TableService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final RestaurantTableRepository tableRepository;

    @Override
    public RestaurantTable getTableById(Integer id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Table not found with id: " + id));
    }

    @Override
    @Transactional
    public RestaurantTable getOrCreateTable(Integer id) {
        return tableRepository.findById(id)
                .orElseGet(() -> {
                    RestaurantTable newTable = new RestaurantTable();
                    newTable.setId(id);
                    newTable.setStatus(RestaurantTable.TableStatus.AVAILABLE);
                    return tableRepository.save(newTable);
                });
    }

    @Override
    public List<RestaurantTable> getAllTables() {
        return tableRepository.findAll();
    }

    @Override
    public List<RestaurantTable> getTablesByStatus(RestaurantTable.TableStatus status) {
        return tableRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public RestaurantTable updateTableStatus(Integer id, RestaurantTable.TableStatus status) {
        RestaurantTable table = getTableById(id);
        table.setStatus(status);
        return tableRepository.save(table);
    }

    @Override
    @Transactional
    public void deleteTable(Integer id) {
        tableRepository.deleteById(id);
    }
}