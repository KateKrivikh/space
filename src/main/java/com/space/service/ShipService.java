package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;

import java.util.List;
import java.util.Map;

public interface ShipService {

    List<Ship> getAll(Map<String, String> params, Integer page, Integer size, ShipOrder order);

    Integer getCount(Map<String, String> params);

    Ship getById(Long id);

    void delete(Long id);

    void save(Ship ship);
}
