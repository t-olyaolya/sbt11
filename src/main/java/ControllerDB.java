import model.Bid;
import model.Item;
import model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.List;
import java.util.Properties;

/**
 * Created by tyuly on 29.01.2017.
 */
public class ControllerDB {

    private static SessionFactory sessionFactory= null;
    private static ServiceRegistry serviceRegistry= null;
    private static Session session = null;
    private static Transaction tx = null;

    public SessionFactory configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.configure();
        Properties properties = configuration.getProperties();
        serviceRegistry= new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();
        sessionFactory= configuration.buildSessionFactory(serviceRegistry);

        return sessionFactory;
    }

    public void createSession()  {
        if (session == null) {
            sessionFactory = configureSessionFactory();
            session = sessionFactory.openSession();
            fillDB();
        }
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        tx = session.beginTransaction();
    }

    public void closeSession() {
        if (session != null) {
            session.close();
        }
    }

    public void fillDB()  {
        User user1 = createUser("User1", "1111");
        User user2 = createUser("User2", "2222");
        Item item1 = createItem("Item1", user1, "1");
        Item item2 = createItem("Item2", user2, "2");
        Item item3 = createItem("Item3", user1, "3");
        Bid bid = createBid(user1, item2);
        Bid bid2 = createBid(user2, item1);
    }

   public User createUser(String name, String password){
        User user = new User();
        try {
            createSession();
            user.setName(name);
            user.setPassword(password);
            session.persist(user);
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        }
        return user;
    }



    public Item createItem(String name, User user, String description) {
        Item item = new Item();
        try {
            createSession();
            item.setName(name);
            item.setDescription(description);
            item.setUser(user);
            user.setItem(item);
            session.persist(item);
            session.persist(user);
            tx.commit();
            session.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        }
        return item;
    }

    public Bid createBid(User user, Item item)  {
        Bid bid = new Bid();
        try {
            createSession();
            bid.setUser(user);
            user.setBid(bid);
            bid.setItem(item);
            session.persist(bid);
            session.persist(user);
            tx.commit();
            session.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        }
        return bid;
    }


    public  List<User> getUsers() {
        List<User> userList = null;
        try {
            userList = session.createCriteria(User.class).list();
            session.flush();
            tx.commit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        }
        return userList;
    }

    public  List<Item> getItems() {
        List<Item> itemList = null;
        try {
            createSession();
            itemList = session.createCriteria(Item.class).list();
            session.flush();
            tx.commit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        }
        return itemList;
    }

    public  List<Bid> getBids() {
        List<Bid> bidList = null;
        try {
            createSession();
            bidList = session.createCriteria(Bid.class).list();
            session.flush();
            tx.commit();

        }
        catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        }
        return bidList;
    }

    public boolean auth(String username, String password) {
        try {
            createSession();
            List<User> userList = session.createCriteria(User.class).list();
            for (User user : userList) {
                if ((user.getName().equals(username)) && (user.getPassword().equals(password))) {
                    return true;
                }
            }
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        }
        return false;
    }

    public boolean checkName(String username, String password) {
        try {
            createSession();
            List<User> userList = session.createCriteria(User.class).list();
            for (User user : userList) {
                if ((user.getName().equals(username))) {
                    return true;
                }
            }
            session.flush();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        }
        return false;
    }

}
