/*
 *Add Product Controller 
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
import javax.xml.bind.ValidationException;
import model.Inventory;
import static model.Inventory.getAllParts;
import model.Part;
import model.Products;

/**
 * FXML Controller class
 *
 * @author hosea
 */
public class AddProductController implements Initializable {
    
    // Create Variables
    private final ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private String exceptionMessage = new String();
    private int productID;

    @FXML
    private TextField lblProductIDNumber;

    @FXML
    private TextField tableProductName;

    @FXML
    private TextField tableProductInv;

    @FXML
    private TextField tableProductPrice;

    @FXML
    private TextField tableProductMax;

    @FXML
    private TextField tableProductMin;

    @FXML
    private TextField tableProductSearch;

    @FXML
    private TableView<Part> tableProductAdd;
    
    @FXML
    private TableColumn<Part, Integer> addPartIDCol;
    
    @FXML
    private TableColumn<Part, String> addPartNameCol;

    @FXML
    private TableColumn<Part, Integer> addPartInvCol;

    @FXML
    private TableColumn<Part, Double> addPartPriceCol;

    @FXML
    private TableView<Part> tableProductDelete;
    
    @FXML
    private TableColumn<Part, Integer> deletePartIDCol;

    @FXML
    private TableColumn<Part, String> deletePartNameCol;

    @FXML
    private TableColumn<Part, Integer> deletePartInvCol;

    @FXML
    private TableColumn<Part, Double> deletePartPriceCol;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        addPartIDCol.setCellValueFactory(cellData -> cellData.getValue().pID().asObject());
        addPartNameCol.setCellValueFactory(cellData -> cellData.getValue().pName());
        addPartInvCol.setCellValueFactory(cellData -> cellData.getValue().pStock().asObject());
        addPartPriceCol.setCellValueFactory(cellData -> cellData.getValue().pPrice().asObject());
        deletePartIDCol.setCellValueFactory(cellData -> cellData.getValue().pID().asObject());
        deletePartNameCol.setCellValueFactory(cellData -> cellData.getValue().pName());
        deletePartInvCol.setCellValueFactory(cellData -> cellData.getValue().pStock().asObject());
        deletePartPriceCol.setCellValueFactory(cellData -> cellData.getValue().pPrice().asObject());
        
        updateAddPartTableView();
        updateDeletePartTableView();
        
        productID = Inventory.productsCount();
        lblProductIDNumber.setText("Auto-Gen: " + productID);
        
    }
    
    /*
    * Create Methods for search fields and buttons to handle specific events 
    */

    @FXML
    void handleClearSearch(ActionEvent event) {
        
        updateAddPartTableView();
        tableProductSearch.setText("");
    }
    
    @FXML
    void handleAddProductCancel(ActionEvent event) throws IOException {
        
        Parent add = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        Scene addScene = new Scene(add);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addScene);
        window.show();

    }

    @FXML
    void handleAddProductSave(ActionEvent event) throws ValidationException, IOException {
        
        String prodName = tableProductName.getText();
        String prodInv = tableProductInv.getText();
        String prodPrice = tableProductPrice.getText();
        String prodMin = tableProductMin.getText();
        String prodMax = tableProductMax.getText();

        try{
            exceptionMessage = Products.isValid(prodName, Integer.parseInt(prodMin), 
                                                Integer.parseInt(prodMax), Integer.parseInt(prodInv), 
                                                Double.parseDouble(prodPrice), currentParts, exceptionMessage);
            
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            }
            else {
                
                System.out.println("Product name: " + prodName);
                
                Products newProd = new Products();
                
                newProd.setProductsID(productID);
                newProd.setProductsName(prodName);
                newProd.setProductsInStock(Integer.parseInt(prodInv));
                newProd.setProductsPrice(Double.parseDouble(prodPrice));
                newProd.setProductsMin(Integer.parseInt(prodMin));
                newProd.setProductsMax(Integer.parseInt(prodMax));
                newProd.setProductParts(currentParts);
                Inventory.addProducts(newProd);

                Parent addProductSaveParent = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
                Scene scene = new Scene(addProductSaveParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch (NumberFormatException e) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Product");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
        }
    }

    @FXML
    void onClickhandleAdd(ActionEvent event) {
        
        Part part = tableProductAdd.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        updateDeletePartTableView();
        
    }
    
    @FXML
    void onClickhandleDelete(ActionEvent event) {
        
        Part parts = tableProductDelete.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Part Deletion");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to delete " + parts.getPartsName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            System.out.println("Part deleted.");
            currentParts.remove(parts);
        }
        else {
            System.out.println("You clicked cancel.");
        }
        
    }

    @FXML
    void handleSearch(ActionEvent event) {
        
        String searchPart = tableProductSearch.getText();
        
        int partIndex = -1;
        
        if (Inventory.lookupPart(searchPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part not found");
            alert.setContentText("The search term entered does not match any known parts.");
            alert.showAndWait();
        }
        else {
            partIndex = Inventory.lookupPart(searchPart);
            Part tempPart = getAllParts().get(partIndex);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(tempPart);
            tableProductAdd.setItems(tempPartList);
        }
    }
    
    // Handles Table View to Add Parts to products screen
    public void updateAddPartTableView() {
        
        tableProductAdd.setItems(getAllParts());
        
    }
    
    // Handle table view to delete parts from product screen 
    public void updateDeletePartTableView() {
        
        tableProductDelete.setItems(currentParts);
        
    }
    
}
