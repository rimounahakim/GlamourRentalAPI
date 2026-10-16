package se.glamourrental.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 60)
    private String category;

    @Column(nullable = false, length = 20)
    private String size;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerDay;

    // har kopplat jag klänningen till de personer som hyr den.
    @ManyToMany(mappedBy = "items")
    private Set<Person> persons = new LinkedHashSet<>();

    protected Item() {
    }

    public Item(String name, String category, String size, BigDecimal pricePerDay) {
        this.name = name;
        this.category = category;
        this.size = size;
        this.pricePerDay = pricePerDay;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public BigDecimal getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(BigDecimal pricePerDay) { this.pricePerDay = pricePerDay; }
    public Set<Person> getPersons() { return persons; }
}
