package model;

import javax.persistence.*;

/**
 * @author Igor on 15.02.19.
 */
@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(optional = false, mappedBy = "address")
    private User user;

    @Column(name = "street", nullable = false)
    private String street;

    public Address() {
    }

    public Address(User user, String street) {
        this.user = user;
        this.street = street;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                '}';
    }
}
