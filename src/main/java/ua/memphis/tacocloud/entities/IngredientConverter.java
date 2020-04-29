package ua.memphis.tacocloud.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.memphis.tacocloud.data.jdbc.IngredientRepository;
import ua.memphis.tacocloud.data.jdbc.JdbcIngredientRepositoryImpl;

@Component
public class IngredientConverter implements Converter<String,Ingredient> {

    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findOne(id);
    }
}
