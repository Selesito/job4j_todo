package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
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
    public Item add(Item item, String[] ids) {
        tx(session -> {
            for (String id : ids) {
                Category category = session.find(Category.class, Integer.parseInt(id));
                item.addCity(category);
            }
            session.save(item);
            return null;
        });
        return item;
    }

    @Override
    public boolean replace(int id) {
        return tx(session -> {
                    final Query query = session.createQuery(
                            "update Item set done=true where id=:id");
                    query.setParameter("id", id);
                    query.executeUpdate();
                    return true;
                }
        );
    }

    @Override
    public List<Item> findAll() {
        return tx(session -> session.createQuery(
                "select distinct i from Item i join fetch i.categories"
        ).list());
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

    @Override
    public User addUser(User user) {
        tx(session -> session.save(user));
        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = null;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from User where email = :email");
            query.setParameter("email", email);
            if (!query.getResultList().isEmpty())  {
                user = (User) query.getResultList().get(0);
            }
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public User findByIdUser(int id) {
        return this.tx(
                session -> session.get(User.class, id)
        );
    }

    public Collection<Category> allCategories() {
        List<Category> rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();

            rsl = session.createQuery("select c from Category c", Category.class).list();

            session.getTransaction().commit();
        } catch (Exception e) {
            sf.getCurrentSession().getTransaction().rollback();
        }
        return rsl;
    }
}
