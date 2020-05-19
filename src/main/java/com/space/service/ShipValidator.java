package com.space.service;

import com.space.model.Ship;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class ShipValidator implements Validator {

    public static final int NAME_MAX_LENGTH = 50;
    public static final int PLANET_MAX_LENGTH = 50;
    public static final int PROD_DATE_MIN = 2800;
    public static final int PROD_DATE_MAX = 3019;
    public static final double SPEED_MIN = 0.01;
    public static final double SPEED_MAX = 0.99;
    public static final int CREW_SIZE_MIN = 1;
    public static final int CREW_SIZE_MAX = 9999;
    public static final int RATING_MULTIPLIER = 80;
    public static final double RATING_K_USED_TRUE = 0.5;
    public static final double RATING_K_USED_FALSE = 1;
    public static final int RATING_CURRENT_YEAR = 3019;
    public static final int RATING_PLUS_YEAR = 1;


    @Override
    public boolean supports(Class<?> clazz) {
        return Ship.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Ship ship = (Ship) target;

        if (ship.getName() == null || ship.getName().isEmpty())
            errors.rejectValue("name", "value.empty");
        else if (ship.getName().length() > NAME_MAX_LENGTH)
            errors.rejectValue("name", "value.tooLong");

        if (ship.getPlanetName() == null || ship.getPlanetName().isEmpty())
            errors.rejectValue("planetName", "value.empty");
        else if (ship.getPlanetName().length() > PLANET_MAX_LENGTH)
            errors.rejectValue("planetName", "value.tooLong");

        if (ship.getShipType() == null)
            errors.rejectValue("shipType", "value.empty");

        if (ship.getProdDate() == null)
            errors.rejectValue("prodDate", "value.empty");
        else {
            int year = ship.getProdDate().toLocalDate().getYear();
            if (year < PROD_DATE_MIN || year > PROD_DATE_MAX)
                errors.rejectValue("prodDate", "value.notInRange");
        }

        if (ship.getUsed() == null)
            ship.setUsed(false);

        if (ship.getSpeed() == null)
            errors.rejectValue("speed", "value.empty");
        else {
            double newSpeed = (double) Math.round(ship.getSpeed() * 100) / 100;
            if (newSpeed < SPEED_MIN || newSpeed > SPEED_MAX)
                errors.rejectValue("speed", "value.notInRange");
            else
                ship.setSpeed(newSpeed);
        }

        if (ship.getCrewSize() == null)
            errors.rejectValue("crewSize", "value.empty");
        else if (ship.getCrewSize() < CREW_SIZE_MIN || ship.getCrewSize() > CREW_SIZE_MAX)
            errors.rejectValue("crewSize", "value.notInRange");

        if (!errors.hasErrors()) {
            double newRating = RATING_MULTIPLIER * ship.getSpeed() * (ship.getUsed() ? RATING_K_USED_TRUE : RATING_K_USED_FALSE) /
                    (RATING_CURRENT_YEAR - ship.getProdDate().toLocalDate().getYear() + RATING_PLUS_YEAR);
            newRating = (double) Math.round(newRating * 100) / 100;
            ship.setRating(newRating);
        }
    }

}
