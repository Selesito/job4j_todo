package ru.job4j.todo.store;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.List;

public interface Store {

    Item add(Item item, String[] ids);

    boolean replace(int id);

    List<Item> findAll();

    Item findById(int id);

    void delete(int id);

    User addUser(User user);

    User findByEmail(String email);

    User findByIdUser(int id);

    Collection<Category> allCategories();
}
