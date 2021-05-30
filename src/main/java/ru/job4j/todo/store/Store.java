package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;

import java.util.List;

public interface Store {

    Item add(Item item);

    boolean replace(Item item);

    List<Item> findAll();

    Item findById(int id);

    void delete(int id);
}
