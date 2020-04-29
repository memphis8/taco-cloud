package ua.memphis.tacocloud.data.jdbc;

import ua.memphis.tacocloud.entities.Taco;

public interface TacoRepository {

    Taco save(Taco design);

}
