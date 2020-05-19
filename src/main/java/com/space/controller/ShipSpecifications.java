package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.util.Map;

public class ShipSpecifications {

    public static Specification<Ship> isNameContains(String name) {
        return (Specification<Ship>) (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Ship> isPlanetContains(String planet) {
        return (Specification<Ship>) (root, query, builder) -> builder.like(root.get("planetName"), "%" + planet + "%");
    }

    public static Specification<Ship> isShipTypeEqual(ShipType shipType) {
        return (Specification<Ship>) (root, query, builder) -> builder.equal(root.get("shipType"), shipType);
    }

    public static Specification<Ship> isProdDateAfter(Long after) {
        return (Specification<Ship>) (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("prodDate"), new Date(after));
    }

    public static Specification<Ship> isProdDateBefore(Long before) {
        return (Specification<Ship>) (root, query, builder) -> builder.lessThanOrEqualTo(root.get("prodDate"), new Date(before));
    }

    public static Specification<Ship> isUsedEqual(Boolean isUsed) {
        return (Specification<Ship>) (root, query, builder) -> builder.equal(root.get("isUsed"), isUsed);
    }


    public static Specification<Ship> isSpeedGraterThanOrEqualTo(Double valueMin) {
        return (Specification<Ship>) (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("speed"), valueMin);
    }

    public static Specification<Ship> isSpeedLessThanOrEqualTo(Double valueMax) {
        return (Specification<Ship>) (root, query, builder) -> builder.lessThanOrEqualTo(root.get("speed"), valueMax);
    }

    public static Specification<Ship> isCrewSizeGraterThanOrEqualTo(Integer valueMin) {
        return (Specification<Ship>) (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("crewSize"), valueMin);
    }

    public static Specification<Ship> isCrewSizeLessThanOrEqualTo(Integer valueMax) {
        return (Specification<Ship>) (root, query, builder) -> builder.lessThanOrEqualTo(root.get("crewSize"), valueMax);
    }

    public static Specification<Ship> isRatingGraterThanOrEqualTo(Double valueMin) {
        return (Specification<Ship>) (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("rating"), valueMin);
    }

    public static Specification<Ship> isRatingLessThanOrEqualTo(Double valueMax) {
        return (Specification<Ship>) (root, query, builder) -> builder.lessThanOrEqualTo(root.get("rating"), valueMax);
    }



    public static Specification<Ship> isFilteredShip(Map<String, String> params) {
        Specification<Ship> spec = (root, query, builder) -> builder.isTrue(builder.literal(true));

        if (params.containsKey("name"))
            spec = spec.and(isNameContains(params.get("name")));
        if (params.containsKey("planet"))
            spec = spec.and(isPlanetContains(params.get("planet")));
        if (params.containsKey("shipType"))
            spec = spec.and(isShipTypeEqual(ShipType.valueOf(params.get("shipType").toUpperCase())));

        if (params.containsKey("after"))
            spec = spec.and(isProdDateAfter(Long.parseLong(params.get("after"))));
        if (params.containsKey("before"))
            spec = spec.and(isProdDateBefore(Long.parseLong(params.get("before"))));

        if (params.containsKey("isUsed"))
            spec = spec.and(isUsedEqual(Boolean.valueOf(params.get("isUsed"))));

        if (params.containsKey("minSpeed"))
            spec = spec.and(isSpeedGraterThanOrEqualTo(Double.parseDouble(params.get("minSpeed"))));
        if (params.containsKey("maxSpeed"))
            spec = spec.and(isSpeedLessThanOrEqualTo(Double.parseDouble(params.get("maxSpeed"))));

        if (params.containsKey("minCrewSize"))
            spec = spec.and(isCrewSizeGraterThanOrEqualTo(Integer.parseInt(params.get("minCrewSize"))));
        if (params.containsKey("maxCrewSize"))
            spec = spec.and(isCrewSizeLessThanOrEqualTo(Integer.parseInt(params.get("maxCrewSize"))));

        if (params.containsKey("minRating"))
            spec = spec.and(isRatingGraterThanOrEqualTo(Double.parseDouble(params.get("minRating"))));
        if (params.containsKey("maxRating"))
            spec = spec.and(isRatingLessThanOrEqualTo(Double.parseDouble(params.get("maxRating"))));

        return spec;
    }
}
