package com.rental.ezcars.specification;

import com.rental.ezcars.entity.Vehicle;
import com.rental.ezcars.dto.VehicleSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class VehicleSpecification {

    public static Specification<Vehicle> searchVehicles(VehicleSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (criteria.getSearchText() != null && !criteria.getSearchText().isEmpty()) {
                String searchText = "%" + criteria.getSearchText().toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("make")), searchText),
                    cb.like(cb.lower(root.get("model")), searchText),
                    cb.like(cb.lower(root.get("transmission")), searchText),
                    cb.like(cb.lower(root.get("fuelType")), searchText),
                    cb.like(cb.lower(root.get("details")), searchText)
                ));
            }

            if (criteria.getMake() != null) {
                predicates.add(cb.like(cb.lower(root.get("make")), "%" + criteria.getMake().toLowerCase() + "%"));
            }
            if (criteria.getModel() != null) {
                predicates.add(cb.like(cb.lower(root.get("model")), "%" + criteria.getModel().toLowerCase() + "%"));
            }
            if (criteria.getYear() != null) {
                predicates.add(cb.equal(root.get("year"), criteria.getYear()));
            }
            if (criteria.getTransmission() != null) {
                predicates.add(cb.equal(root.get("transmission"), criteria.getTransmission()));
            }
            if (criteria.getFuelType() != null) {
                predicates.add(cb.equal(root.get("fuelType"), criteria.getFuelType()));
            }
            if (criteria.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
            }
            if (criteria.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
            }
            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}