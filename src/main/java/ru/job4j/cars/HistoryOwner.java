package ru.job4j.cars;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "history_owner")
public class HistoryOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "history_owner", joinColumns = {@JoinColumn(name = "driver_id",
            nullable = false, updatable = false)}, inverseJoinColumns =
            {@JoinColumn(name = "car_id", nullable = false, updatable = false)})
    private List<Driver> drivers = new ArrayList<>();

    public static HistoryOwner of() {
        HistoryOwner historyOwner = new HistoryOwner();
        return historyOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HistoryOwner that = (HistoryOwner) o;
        return id == that.id && Objects.equals(drivers, that.drivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, drivers);
    }
}
