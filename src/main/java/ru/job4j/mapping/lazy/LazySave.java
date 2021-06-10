package ru.job4j.mapping.lazy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class LazySave {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Brand volkswagen = Brand.of("Volkswagen");
            Brand audi = Brand.of("Audi");
            session.save(Model.of("Polo", volkswagen));
            session.save(Model.of("Jetta", volkswagen));
            session.save(Model.of("Golf", volkswagen));
            session.save(Model.of("Passat", volkswagen));
            session.save(Model.of("Arteon", volkswagen));
            session.save(Model.of("A8", audi));
            session.save(volkswagen);
            session.save(audi);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
