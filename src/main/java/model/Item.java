package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by tyuly on 29.01.2017.
 */
@Entity
public class Item extends BaseEntity {
    @Column
    private String name;
    @Column
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
