/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package tableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author User
 */
public class TryController implements Initializable {

    @FXML
    private TableView<Person> table;
    @FXML
    private TableColumn<Person, Integer> id;
    @FXML
    private TableColumn<Person, String> fname;
    @FXML
    private TableColumn<Person, String> mname;
    @FXML
    private TableColumn<Person, String> lname;
    @FXML
    private TableColumn<Person, Integer> cntct;
    @FXML
    private TableColumn<Person, Integer> status;
    @FXML
    private Button insertBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TextField search_field;
    @FXML
    private ChoiceBox<String> deptBox;
    @FXML
    private TextField id_field;
    @FXML
    private TextField fname_field;
    @FXML
    private TextField mname_field;
    @FXML
    private TextField lname_field;
    @FXML
    private TextField cntct_field;
    @FXML
    private TextField status_field;
    @FXML
    private ImageView imageView;
    @FXML
    private AnchorPane parent;
    
    private boolean defaultMode= true;
    private String lightMode = getClass().getResource("persInfo.css").toExternalForm();
    private String darkMode = getClass().getResource("darkMode.css").toExternalForm();
    
    Image lightIcon = new Image(getClass().getResource("lm.png").toExternalForm());
    Image darkIcon = new Image(getClass().getResource("nm.png").toExternalForm());

    @FXML
    private Text t1;
    @FXML
    private Text t6;
    @FXML
    private Text t7;
    @FXML
    private Text t8;
    @FXML
    private Text t9;
    @FXML
    private Text t10;
    @FXML
    private Text title;
    @FXML
    private Label t4;
    @FXML
    private Label t3;
    @FXML
    private Label t2;
    @FXML
    private Label t5;
    
//     ObservableList<Person> list = FXCollections.observableArrayList(
//             new Person(1, "Henry", "Recaldo", "Torlao", 60,1),
//             new Person(2, "John", " ", "Doe", 65,1),
//             new Person(3, "Mer", "Ca", "Doe", 80,1),
//             new Person(4, "Ali", "Ba", "Ba", 83,1)
//    );
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        showPerson();
        fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        mname.setCellValueFactory(new PropertyValueFactory<>("mname"));
        lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        cntct.setCellValueFactory(new PropertyValueFactory<>("cntct"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        deptBox.setValue("All");
        deptBox.getItems().addAll("All", "Administrative", "Instruction");
        
        deptBox.setOnAction(this::handleDeptBoxChange);
    } 
    
@FXML
private void switchMode(MouseEvent event) {
    Scene scene = imageView.getScene();
    scene.getStylesheets().add(lightMode);
    if (scene != null) {
        ObservableList<String> stylesheets = scene.getStylesheets();

        if (defaultMode) {
            // If light mode is currently applied, switch to night mode
            scene.getStylesheets().remove(lightMode);
            scene.getStylesheets().add(darkMode); 
            imageView.setImage(darkIcon);
            defaultMode = false;
        } else {
            // If night mode is currently applied, switch to light mode
            scene.getStylesheets().remove(darkMode);
            scene.getStylesheets().add(lightMode); 
            imageView.setImage(lightIcon);
            defaultMode = true;
        }
    }
}
     private static final String DB_URL = "jdbc:mysql://localhost:3306/attendancesystem";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    public Connection getConnection(){
        Connection connection;
        try{
            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            return connection;
        }catch(SQLException e){
            return null;
        }
    }
    // 1st Filter =  on search bar, Filter names depending on what character or String input.
     @FXML
    private void filterTable(KeyEvent event) {
         ObservableList<Person> filteredData = FXCollections.observableArrayList();
         String keyword = search_field.getText();

        for (Person person : getPerson()) {
            // Check if any of the columns contain the search keyword (case-insensitive).
            if (person.getFname().toLowerCase().contains(keyword.toLowerCase())
                || person.getMname().toLowerCase().contains(keyword.toLowerCase())
                || person.getLname().toLowerCase().contains(keyword.toLowerCase())){
                
               
                    filteredData.add(person);
                    table.setItems(filteredData);
            }
        } 
    }
   @FXML
//--------------------------------------------------->>>> 2nd FILTER: Where users have been filtered through choiceBox.
public void filterByDept(ActionEvent event) {
    showPersonDept();
}
// Event handler for the ChoiceBox change
private void handleDeptBoxChange(ActionEvent event) {
    showPersonDept();
}

   public void showPersonDept() {
    ObservableList<Person> person = getPersonDept();
    table.setItems(person);
}

    public ObservableList<Person> getPersonDept() {
    String deptId = deptBox.getValue();
        ObservableList<Person> person = FXCollections.observableArrayList();

        if (deptId != null) {
            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement()) {
                String query = "";
                if(deptId.equals("Administrative")){
                    query = "SELECT * FROM user u " +
                        "JOIN assignment a ON u.user_id = a.user_id " +
                        "JOIN position p ON a.position_id = p.position_id " +
                        "JOIN department d ON p.department_id = d.department_id " +
                        "WHERE d.department_id = 1 && u.user_status = 1 GROUP BY a.user_id";
                }else{
                    query = "SELECT * FROM user u " +
                        "JOIN assignment a ON u.user_id = a.user_id " +
                        "JOIN position p ON a.position_id = p.position_id " +
                        "JOIN department d ON p.department_id = d.department_id " +
                        "WHERE d.department_id = 2 && u.user_status = 1 GROUP BY a.user_id";
                }
                ResultSet rs = statement.executeQuery(query);

                while (rs.next()) {
                    person.add(new Person(
                            rs.getInt("user_id"),
                            rs.getString("user_fname"),
                            rs.getString("user_mname"),
                            rs.getString("user_lname"),
                            rs.getInt("user_cntct"),
                            rs.getInt("user_status")
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            }
        }
        return person;
    }
    
    @FXML
    private void insertRecord(){
       String query = "INSERT INTO user (user_fname, user_mname, user_lname, user_cntct, user_status) VALUES ('" 
                + fname_field.getText() + "', '"
                + mname_field.getText() + "', '"
                + lname_field.getText() + "', '"
                + cntct_field.getText() + "', '"
                + status_field.getText() + "')";

       execQuery(query);
       showPerson();
       clearFields();
    }
    @FXML
    private void editBtn(ActionEvent event) { 
        Person selectedItem = table.getSelectionModel().getSelectedItem();
    // ------------------------------------------------->>>> 3rd Filter = Insert filter, if no data input on First Name Field, LastName Field, Contact Field, and Status Field.
        if (fname_field.getText() != null && lname_field.getText() != null
              && cntct_field.getText() != null && status_field.getText() != null) {
            String query = "UPDATE user SET " +
                "user_id = '" + id_field.getText() + "', " +
                "user_fname = '" + fname_field.getText() + "', " +
                "user_mname = '" + mname_field.getText() + "', " +
                "user_lname = '" + lname_field.getText() + "', " +
                "user_cntct = '" + cntct_field.getText() + "', " +
                "user_status = '" + status_field.getText() + "' "+
                "WHERE user_id = " + selectedItem.getId();
            execQuery(query);
            showPerson();
            clearFields();
        }else{
          // Handle case when no row is selected or handle error.
          // You can show a message or perform other actions here.
        }
    }
    @FXML
     public void showOnFields() {
        // Get the selected item from the table.
        Person selectedItem = table.getSelectionModel().getSelectedItem();
        // If an item is selected, populate the text fields with the item's data.
        if (selectedItem != null) {
            id_field.setText(selectedItem.getId() + "");
            fname_field.setText(selectedItem.getFname());
            mname_field.setText(selectedItem.getMname());
            lname_field.setText(selectedItem.getLname());
            cntct_field.setText(selectedItem.getCntct() + "");
            status_field.setText(selectedItem.getStatus() + "");
        }
    }
//    @FXML
//public void editRow() throws SQLException {
//    // Get the selected item from the table.
//    Person selectedItem = table.getSelectionModel().getSelectedItem();
//
//    // If an item is selected, update the data in the database.
//    if (selectedItem != null) {
//        // Update the data in the database.
//        String query = "UPDATE person SET fname = ?, mname = ?, lname = ?, age = ?, status = ? WHERE id = ?";
//        PreparedStatement statement = connection.prepareStatement(query);
//
//        // Bind the values of the fields to the prepared statement parameters.
//        statement.setString(1, fname_field.getText());
//        statement.setString(2, mname_field.getText());
//        statement.setString(3, lname_field.getText());
//        statement.setInt(4, Integer.parseInt(age_field.getText()));
//        statement.setInt(5, Integer.parseInt(status_field.getText()));
//        statement.setInt(6, selectedItem.getId());
//
//        // Execute the prepared statement.
//        statement.executeUpdate();
//
//        // Close the prepared statement.
//        statement.close();
//    }
//}

     
    public void showPerson(){
        ObservableList<Person> person = (ObservableList<Person>) getPerson();
        table.setItems(person);
    }
    public ObservableList<Person> getPerson(){
        ObservableList<Person> person = FXCollections.observableArrayList();
        try (Connection connection = getConnection();
            Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE user_status = 1");
            
            while (rs.next()) {
                  person.add(new Person(
                    rs.getInt("user_id"),
                    rs.getString("user_fname"),
                    rs.getString("user_mname"),
                    rs.getString("user_lname"),
                    rs.getInt("user_cntct")  ,
                    rs.getInt("user_status")     
                  ));
            }

        } catch (SQLException e) {
        }
        return person;
    }
    
    private void execQuery(String query){
        Connection con = getConnection();
        Statement st;
        try{
            st = con.createStatement();
            st.executeUpdate(query);
        }catch(SQLException e){
        }
    }
    @FXML
    private void clearing(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        id_field.clear();
        fname_field.clear();
        mname_field.clear();
        lname_field.clear();
        cntct_field.clear();
        status_field.clear();
    }
    /**
     * Initializes the controller class.
     */
    
     @FXML
private void deactivate(ActionEvent event) {
    Person selectedItem = table.getSelectionModel().getSelectedItem();
    
    if (selectedItem != null) {
        int selectID = selectedItem.getId();
        String query = "UPDATE user SET user_status = 0 WHERE user_id = " + selectID;
    
        execQuery(query);
        showPerson();
        clearFields();
    } else {
        // Handle case when no row is selected or handle error.
        // You can show a message or perform other actions here.
    }
}


 
}
