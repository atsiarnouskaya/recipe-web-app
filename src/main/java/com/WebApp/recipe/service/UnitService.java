package com.WebApp.recipe.service;


import com.WebApp.recipe.entity.Unit;

public interface UnitService {

    double convertToBase(double quantity, String unit);

    double convertToAnyUnit(String unit, String targetUnit, double quantity);

    double adjustAmount(double quantity, double factor);

    Unit findByNameElseAdd(Unit unit);
}
