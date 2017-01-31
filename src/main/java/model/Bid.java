package model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by tyuly on 29.01.2017.
 */
@Entity
public class Bid extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public void setItem(Item item) {
        this.item = item;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public User getUser() {
        return user;
    }
}
