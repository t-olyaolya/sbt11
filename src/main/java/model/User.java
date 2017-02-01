package model;

import javax.persistence.*;
import java.util.ArrayList;
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
    private List<Item> itemList = new ArrayList<Item>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Bid> bidList = new ArrayList<Bid>();


    public void setName(String name) {
        this.name = name;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void setItem (Item item) {
        itemList.add(item);
    }

    public void setBidList(List<Bid> bidList) {
        this.bidList = bidList;
    }

    public void setBid (Bid bid) {
        bidList.add(bid);
    }

    public String getName() {
        return name;
    }

    public List<Item> getItemList() {
        return itemList;
    }
}
