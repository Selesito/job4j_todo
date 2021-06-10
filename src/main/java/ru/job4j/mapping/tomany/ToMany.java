package ru.job4j.mapping.tomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ToMany {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            var volkswagen = Brand.of("Volkswagen");
            session.beginTransaction();
            session.save(Model.of("Polo"));
            session.save(Model.of("Jetta"));
            session.save(Model.of("Golf"));
            session.save(Model.of("Passat"));
            session.save(Model.of("Arteon"));
            volkswagen.addModel(session.find(Model.class, 1));
            volkswagen.addModel(session.find(Model.class, 2));
            volkswagen.addModel(session.find(Model.class, 3));
            volkswagen.addModel(session.find(Model.class, 4));
            volkswagen.addModel(session.find(Model.class, 5));
            session.save(volkswagen);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
