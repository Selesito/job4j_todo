package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.function.Function;

public class HbmItem implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private static final class Lazy {
        private static final Store INST = new HbmItem();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Item add(Item item) {
        tx(session -> session.save(item));
        return item;
    }

    @Override
    public boolean replace(Item item) {
        return tx(session -> {
            session.update(item);
            return true;
        });
    }

    @Override
    public List<Item> findAll() {
        return this.tx(
                session -> session.createQuery(
                        "from ru.job4j.todo.model.Item").list()
        );
    }

    @Override
    public Item findById(int id) {
        return this.tx(
                session -> session.get(Item.class, id)
        );
    }

    @Override
    public void delete(int id) {
        this.tx(
                session -> {
                    Item item = new Item();
                    item.setId(id);
                    session.delete(item);
                    return true;
                }
        );

    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
