/*
 *Main Controller
 */
package controllers;

// Imports
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import static model.Inventory.deletePart;
import static model.Inventory.deleteParts;
import static model.Inventory.deleteProducts;
import static model.Inventory.deletedProducts;
import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;
import model.Part;
import model.Products;

/**
 * Controller
 * @author hosea
 */
public class MainController implements Initializable {
    
    // Variables
    Stage stage;
    Scene scene;
    
    @FXML
    private TableView<Part> tableViewParts;
    
    @FXML
    private TextField txtSearchPartField;
    
    @FXML
    private TableColumn<Part, Integer> partIDColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> inventoryPartColoumn;

    @FXML
    private TableColumn<Part, Double> priceCostColumn;
    
    @FXML
    private TableView<Products> tableViewProducts;
    
    @FXML
    private TextField txtSearchProductsField;

    @FXML
    private TableColumn<Products, Integer> productIDColumn;

    @FXML
    private TableColumn<Products, String> productNameColumn;

    @FXML
    private TableColumn<Products, Integer> inventoryProductColumn;

    @FXML
    private TableColumn<Products, Double> priceProductColumn;
    
    private static Part modifyPart;
    private static int modifyPartIndex;
    private static int modifyProductIndex;
    
    // Methods for variables created
    public static int partToModifyIndex() {
        return modifyPartIndex;
    }

    public static int productToModifyIndex() {
        return modifyProductIndex;
    }
    
    
    // Methods to handle buttons for events 
    @FXML
    void onClickAddParts(ActionEvent event) throws IOException {
        
        Parent add = FXMLLoader.load(getClass().getResource("/views/AddPart.fxml"));
        Scene addScene = new Scene(add);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addScene);
        window.show();
            
    }

    @FXML
    void onClickAddProducts(ActionEvent event) throws IOException {
        
        Parent add = FXMLLoader.load(getClass().getResource("/views/AddProduct.fxml"));
        Scene addScene = new Scene(add);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addScene);
        window.show();

    }
    
    @FXML
    void clearPartsSearch(ActionEvent event) {
        
        updatePartsTableView();
        txtSearchPartField.setText("");
        
    }
    
    void updatePartsTableView() {
        
        tableViewParts.setItems(getAllParts());
        
    }

    @FXML
    void onClickDeleteParts(ActionEvent event) {
        
        Part part = tableViewParts.getSelectionModel().getSelectedItem();
        
        if (deletePart(part)) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("Part cannot be deleted!");
            alert.setContentText("Part is being used by one or more products.");
            alert.showAndWait();
        }
        else {
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part Deletion");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to delete " + part.getPartsName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                deleteParts(part);
                updatePartsTableView();
                System.out.println("Part " + part.getPartsName() + " was removed.");
            }
            else {
                System.out.println("Part " + part.getPartsName() + " was not removed.");
            }
        }

    }
    
    @FXML
    public void clearProductsSearch(ActionEvent event) {
        
        updateProductsTableView();
        txtSearchProductsField.setText("");
    }
    
    public void updateProductsTableView() {
        
       tableViewProducts.setItems(getAllProducts());
       
    }

    @FXML
    void onClickDeleteProducts(ActionEvent event) {
        
        Products product = tableViewProducts.getSelectionModel().getSelectedItem();
        if (deletedProducts(product)) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Deletion Error");
            alert.setHeaderText("Product cannot be deleted!");
            alert.setContentText("Product is being used by one or more products.");
            alert.showAndWait();
        }
        else {
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Product Deletion");
            alert.setHeaderText("Confirm?");
            alert.setContentText("Are you sure you want to delete " + product.getProductsName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                deleteProducts(product);
                updateProductsTableView();
                System.out.println("Product " + product.getProductsName() + " was removed.");
            }
            else {
                System.out.println("Product " + product.getProductsName() + " was not removed.");
            }
        }


    }

    @FXML
    void onClickExitMain(ActionEvent event) {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
        else {
            System.out.println("You clicked cancel.");
        }

    }

    @FXML
    void onClickModifyParts(ActionEvent event) throws IOException {
        
        modifyPart = tableViewParts.getSelectionModel().getSelectedItem();
        modifyPartIndex = getAllParts().indexOf(modifyPart);
        
        Parent modifyPartParent = FXMLLoader.load(getClass().getResource("/views/ModifyPart.fxml"));
        Scene modifyPartScene = new Scene(modifyPartParent);
        Stage modifyPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        modifyPartStage.setScene(modifyPartScene);
        modifyPartStage.show();

    }

    @FXML
    void onClickModifyProducts(ActionEvent event) throws IOException {
        
        Parent add = FXMLLoader.load(getClass().getResource("/views/ModifyProduct.fxml"));
        Scene addScene = new Scene(add);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addScene);
        window.show();

    }

    @FXML
    void onClickSearchParts(ActionEvent event) {
        
        String searchPart = txtSearchPartField.getText();
        
        int partIndex = -1;
        
        if (Inventory.lookupPart(searchPart) == partIndex) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part not found");
            alert.setContentText("The search term entered does not match any known parts.");
            alert.showAndWait();
        }
        else {
            
            partIndex = Inventory.lookupPart(searchPart);
            Part temp = Inventory.getAllParts().get(partIndex);
            ObservableList<Part> tempList = FXCollections.observableArrayList();
            tempList.add(temp);
            tableViewParts.setItems(tempList);
        }

    }

    @FXML
    void onClickSearchProducts(ActionEvent event) {
        
        String searchProduct = txtSearchProductsField.getText();
        
        int prodIndex = -1;
        
        if (Inventory.lookupProducts(searchProduct) == prodIndex) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Product not found");
            alert.setContentText("The search term entered does not match any known products.");
            alert.showAndWait();
        }
        else {
            
            prodIndex = Inventory.lookupProducts(searchProduct);
            Products tempProduct = Inventory.getAllProducts().get(prodIndex);
            ObservableList<Products> tempProductList = FXCollections.observableArrayList();
            tempProductList.add(tempProduct);
            
            tableViewProducts.setItems(tempProductList);
        }

    }
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partIDColumn.setCellValueFactory(cellData -> cellData.getValue().pID().asObject());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().pName());
        inventoryPartColoumn.setCellValueFactory(cellData -> cellData.getValue().pStock().asObject());
        priceCostColumn.setCellValueFactory(cellData -> cellData.getValue().pPrice().asObject());
        productIDColumn.setCellValueFactory(cellData -> cellData.getValue().prID().asObject());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().prName());
        inventoryProductColumn.setCellValueFactory(cellData -> cellData.getValue().prStock().asObject());
        priceProductColumn.setCellValueFactory(cellData -> cellData.getValue().prPrice().asObject());
        
        updatePartsTableView();
        updateProductsTableView();
        
    }        
}
