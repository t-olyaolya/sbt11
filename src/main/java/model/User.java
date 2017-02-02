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
    @Column
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Item> itemList = new ArrayList<Item>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Bid> bidList = new ArrayList<Bid>();

    public User() {

    }

    public User(String  name, String password) {
        this.name = name;
        this.password = password;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public boolean checkPassword (String password) {
        if (!(this.password.equals(password)))
            return false;
        return true;
    }


}
