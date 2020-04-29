package ua.memphis.tacocloud.data.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.memphis.tacocloud.entities.Ingredient;
import ua.memphis.tacocloud.entities.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcTacoRepositoryImpl implements TacoRepository {

    private SimpleJdbcInsert tacoInserter;
    private SimpleJdbcInsert tacoIngredientInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcTacoRepositoryImpl(JdbcTemplate jdbc) {
        this.tacoInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco").usingGeneratedKeyColumns("id");
        this.tacoIngredientInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Ingredients");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Taco save(Taco taco) {
        taco.setCreatedAt(new Date());
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngreddientToTaco(ingredient, tacoId);
        }

        return taco;
    }


    private long saveTacoInfo(Taco taco) {

        @SuppressWarnings("unchecked")
        Map<String, Object> values =
                objectMapper.convertValue(taco, Map.class);
        values.put("createdAt", taco.getCreatedAt());

        long tacoId = tacoInserter.executeAndReturnKey(values).longValue();

        return tacoId;
    }


    private void saveIngreddientToTaco(Ingredient ingredient, long tacoId) {

        Map<String, Object> values = new HashMap<>();
        values.put("taco", tacoId);
        values.put("ingredient", ingredient.getId());
        tacoIngredientInserter.execute(values);

    }


}
