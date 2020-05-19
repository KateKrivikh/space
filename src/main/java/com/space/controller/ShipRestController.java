package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipIdValidator;
import com.space.service.ShipService;
import com.space.service.ShipValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/ships")
public class ShipRestController {

    @Autowired
    private ShipService shipService;

    @Autowired
    private ShipIdValidator shipIdValidator;

    @Autowired
    private ShipValidator shipValidator;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Ship>> getShipsList(
            @RequestParam Map<String, String> params,
            @RequestParam(value = "order", required = false, defaultValue = "ID") String orderName,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize) {

        List<Ship> ships = this.shipService.getAll(params, pageNumber, pageSize, ShipOrder.valueOf(orderName.toUpperCase()));
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Integer> getShipsCount(@RequestParam Map<String, String> params) {
        Integer count = this.shipService.getCount(params);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> getShip(@PathVariable("id") Long id) {
        BindingResult idValidation = new DataBinder(id).getBindingResult();
        shipIdValidator.validate(id, idValidation);
        if (idValidation.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Ship ship = this.shipService.getById(id);
        if (ship == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship, BindingResult result) {
        shipValidator.validate(ship, result);
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.shipService.save(ship);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> updateShip(@PathVariable("id") Long id, @RequestBody Ship shipParams, BindingResult result) {
        shipIdValidator.validate(id, result);
        if (result.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Ship ship = this.shipService.getById(id);
        if (ship == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        boolean hasData = setParamsForUpdate(ship, shipParams);
        if (!hasData)
            return new ResponseEntity<>(ship, HttpStatus.OK);

        shipValidator.validate(ship, result);
        if (result.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        this.shipService.save(ship);
        return new ResponseEntity<>(ship, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Ship> deleteShip(@PathVariable("id") Long id) {
        BindingResult idValidation = new DataBinder(id).getBindingResult();
        shipIdValidator.validate(id, idValidation);
        if (idValidation.hasErrors())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Ship ship = this.shipService.getById(id);
        if (ship == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        this.shipService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean setParamsForUpdate(Ship ship, Ship shipParams) {
        boolean hasData = false;

        if (shipParams.getName() != null) {
            ship.setName(shipParams.getName());
            hasData = true;
        }
        if (shipParams.getPlanetName() != null) {
            ship.setPlanetName(shipParams.getPlanetName());
            hasData = true;
        }
        if (shipParams.getShipType() != null) {
            ship.setShipType(shipParams.getShipType());
            hasData = true;
        }
        if (shipParams.getProdDate() != null) {
            ship.setProdDate(shipParams.getProdDate());
            hasData = true;
        }
        if (shipParams.getUsed() != null) {
            ship.setUsed(shipParams.getUsed());
            hasData = true;
        }
        if (shipParams.getSpeed() != null) {
            ship.setSpeed(shipParams.getSpeed());
            hasData = true;
        }
        if (shipParams.getCrewSize() != null) {
            ship.setCrewSize(shipParams.getCrewSize());
            hasData = true;
        }

        return hasData;
    }

}
