package com.WebApp.recipe.service;

import com.WebApp.recipe.entity.Unit;
import com.WebApp.recipe.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    private static final Map<String, Double> MassConvertingFactor = Map.of(
            "kg", 1000.0,
            "g", 1.0);

    private static final Map<String, Double> VolumeConvertingFactor = Map.of(
            "l", 1000.0,
            "ml", 1.0,
            "tsp", 4.29,
            "tbsp", 14.79,
            "cup", 236.59);

    @Autowired
    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public double convertToBase(double quantity, String unit) {
        Double MassConvertValue = MassConvertingFactor.get(unit);
        Double VolumeConvertValue = VolumeConvertingFactor.get(unit);

        if (MassConvertValue == null && VolumeConvertValue == null) {
            throw new IllegalArgumentException("Invalid unit name: " + unit); //if it's an error

        }

        if (MassConvertValue != null) {
            return (MassConvertValue * quantity);
        } else {
            return (VolumeConvertValue * quantity);
        }
    }

    @Override
    public double convertToAnyUnit(String unit, String targetUnit, double quantity) {
        double base = convertToBase(quantity, unit);
        if (MassConvertingFactor.containsKey(unit) && MassConvertingFactor.containsKey(targetUnit)) {
            Double MassConvertValue = MassConvertingFactor.get(targetUnit);
            return (base / MassConvertValue);
        }

        else if (VolumeConvertingFactor.containsKey(unit) && VolumeConvertingFactor.containsKey(targetUnit)) {
            Double VolumeConvertValue = VolumeConvertingFactor.get(targetUnit);
            return (base / VolumeConvertValue);
        }

        throw new IllegalArgumentException("Mass can't be converted to volume unit");
    }

    @Override
    public double adjustAmount(double quantity, double factor) {
        return (quantity * factor);
    }

    @Override
    @Transactional
    public Unit findByNameElseAdd(Unit unit) {
        Optional<Unit> foundUnit = unitRepository.findFirstByName(unit.getName());

        if (foundUnit.isPresent()) {
            return foundUnit.get();
        }
        else {
            return unitRepository.save(unit);
        }
    }
}
