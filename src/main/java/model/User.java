package model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tyuly on 29.01.2017.
 */
@Entity
@Table(name = "user_table")
public class User extends BaseEntity{
    @Column
    private String name;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Item> itemList;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Bid bid;

    public void setBid(Integer bid) {
        super.setBid(bid);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public String getName() {
        return name;
    }

    public Bid getBid() {
        return bid;
    }

    public List<Item> getItemList() {
        return itemList;
    }
}
