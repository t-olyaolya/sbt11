import com.sun.javafx.charts.Legend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Bid;
import model.Item;
import model.User;

import javax.swing.text.html.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyuly on 29.01.2017.
 */
public class Controller {

    public TextField password;
    public TextField login;
    public Button ok;
    public Label info;
    public Button loginButton;
    public ListView userList;
    public ListView bidList;
    public ListView itemList;

    private ControllerDB controllerDB = new ControllerDB();
    static Stage stage = new Stage();

    public Controller() throws IOException {
    }

    public void loginListener(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("info.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("main.fxml"));
        nameAndPass(root);
        if (controllerDB.auth(login.getText(), password.getText())) {
            newMainWindow(root2);
        }
        else {
            newInfoWindow("Неверный пароль", root);
        }
    }

    public void registryListener(MouseEvent mouseEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("info.fxml"));
        nameAndPass(root);
        if (controllerDB.checkName(login.getText(), password.getText())) {
            newInfoWindow("Пользователь с таким именем уже есть", root);
            controllerDB.closeSession();
            }
            else {
                controllerDB.closeSession();
                controllerDB.createUser(login.getText(), password.getText());
                newInfoWindow("Регистрация закончена", root);
            }
    }

   public void okListener(MouseEvent mouseEvent) {
        stage.close();
        info.setText("");
        
    }

    public void newInfoWindow(String infoStr, Parent root) {
        stage.setScene(new Scene(root, 300, 300));
        AnchorPane pane = (AnchorPane) root.getChildrenUnmodifiable().get(0);
        for (Node node:pane.getChildren()
                ) {
            if (node instanceof Label){
                ((Label) node).setText(infoStr);
            }
        }
        stage.show();
    }

    public List<String> getListUser() {
        List<User> users = controllerDB.getUsers();
        List<String> usersNew = new ArrayList<String>();
        for (User u: users) {
            usersNew.add(u.getName() );
        }
        return usersNew;

    }

    public List<String> getListItem() {
        List<Item> items = controllerDB.getItems();
        List<String> itemsNew = new ArrayList<String>();
        for (Item i: items) {
            itemsNew.add(i.getName() + " " + i.getUser().getName() );
        }
        return itemsNew;

    }

    public List<String> getListBid() {
        List<Bid> bids = controllerDB.getBids();
        List<String> bidsNew = new ArrayList<String>();
        for (Bid b: bids) {
            bidsNew.add(b.getUser().getName() + " " + b.getItem().getName()+ " " + b.getItem().getUser().getName());
        }
        return bidsNew;

    }


    public void newMainWindow(Parent root) {
        ObservableList<String> oList = FXCollections.observableArrayList(getListUser());
        ObservableList<String> oList1 = FXCollections.observableArrayList(getListItem());
        ObservableList<String> oList2 = FXCollections.observableArrayList(getListBid());
        stage.setScene(new Scene(root, 600, 350));
        setList(0,oList,root);
        setList(1,oList1,root);
        setList(2,oList2,root);
        Stage stage1 = (Stage) loginButton.getScene().getWindow();
        stage1.close();
        stage.show();
        controllerDB.closeSession();
    }

    public void setList(int i, ObservableList list, Parent root) {
        HBox pane = (HBox) root.getChildrenUnmodifiable().get(0);
        Node node = pane.getChildren().get(i);
        if (node instanceof FlowPane){
            Node node2 = ((FlowPane) node).getChildren().get(1);
            if (node2 instanceof ListView) {
                    ((ListView) node2).setItems(list);
                }

            }
    }

    public void nameAndPass(Parent root) {
        if ((password.getText().length() == 0) || ((password.getText().length() == 0))) {
            newInfoWindow("Введите имя пользователя и пароль", root);
        }
    }
}
