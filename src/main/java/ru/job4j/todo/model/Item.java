package ru.job4j.todo.model;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
    private Timestamp created;
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Item() {
    }

    public static Item of(String description, boolean done, User user) {
        Item item = new Item();
        item.description = description;
        item.done = done;
        item.user = user;
        item.created = new Timestamp(new Date().getTime());
        return item;
    }

    public static Item of(Integer id, String description, boolean done, User user) {
        Item item = new Item();
        item.id = id;
        item.description = description;
        item.done = done;
        item.user = user;
        item.created = new Timestamp(new Date().getTime());
        return item;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return done == item.done && Objects.equals(id, item.id)
                && Objects.equals(description, item.description)
                && Objects.equals(created, item.created) && Objects.equals(user, item.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, done, user);
    }
}
