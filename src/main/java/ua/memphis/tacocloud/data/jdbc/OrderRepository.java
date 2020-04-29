package ua.memphis.tacocloud.data.jdbc;

import ua.memphis.tacocloud.entities.Order;

public interface OrderRepository {

    Order save(Order order);

}
