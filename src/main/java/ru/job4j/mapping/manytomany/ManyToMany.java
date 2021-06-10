package ru.job4j.mapping.manytomany;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ManyToMany {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book one = Book.of(500, "Зеленая миля");
            Book two = Book.of(280, "Богатый папа");
            Book third = Book.of(790, "Побег из шоушенко");

            Author first = Author.of("Stephen King");
            Author second = Author.of("Robert Kiyosaki");
            first.getBooks().add(one);
            first.getBooks().add(third);
            second.getBooks().add(two);

            session.persist(first);
            session.persist(second);

            Author author = session.get(Author.class, 2);
            session.remove(author);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
