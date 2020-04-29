package ua.memphis.tacocloud.data.jdbc;


import ua.memphis.tacocloud.entities.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
