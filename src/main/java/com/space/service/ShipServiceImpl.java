package com.space.service;

import com.space.controller.ShipOrder;
import com.space.controller.ShipSpecifications;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShipServiceImpl implements ShipService {

    @Autowired
    ShipRepository shipRepository;

    @Override
    public List<Ship> getAll(Map<String, String> params, Integer page, Integer size, ShipOrder order) {
        return shipRepository.findAll(ShipSpecifications.isFilteredShip(params),
                PageRequest.of(page, size, Sort.by(order.getFieldName()))).getContent();
    }

    @Override
    public Integer getCount(Map<String, String> params) {
        return shipRepository.findAll(ShipSpecifications.isFilteredShip(params)).size();
    }

    @Override
    public Ship getById(Long id) {
        return shipRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        shipRepository.deleteById(id);
    }

    @Override
    public void save(Ship ship) {
        shipRepository.save(ship);
    }
}
