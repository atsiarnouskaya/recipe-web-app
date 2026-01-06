package com.WebApp.recipe.Services.RecipeSevices;


import com.WebApp.recipe.Entities.RecipeEntities.Unit;

public interface UnitService {

    double convertToBase(double quantity, String unit);

    double convertToAnyUnit(String unit, String targetUnit, double quantity);

    double adjustAmount(double quantity, double factor);

    Unit findByNameElseAdd(Unit unit);
}
