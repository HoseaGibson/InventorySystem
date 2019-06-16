/*
 * Modify Product controller
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.ValidationException;
import model.Inventory;
import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;
import model.Part;
import model.Products;

/**
 * FXML Controller class
 *
 * @author hosea
 */
public class ModifyProductController implements Initializable {
     
    //Variables
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private int productIndex = productToModifyIndex();
    private String exceptionMessage = new String();
    private int productID;
    
    @FXML
    private Label lblModProdID;
      
    @FXML
    private TextField txtModProductIDNumber;

    @FXML
    private TextField txtModProductName;

    @FXML
    private TextField txtModProductInv;

    @FXML
    private TextField txtModProductPrice;

    @FXML
    private TextField txtModProductMax;

    @FXML
    private TextField txtModProductMin;
    
    @FXML
    private TextField txtModSearch;

    @FXML
    private TableView<Part> tableModProductAdd;

    @FXML
    private TableColumn<Part, Integer> addModPartIDCol;

    @FXML
    private TableColumn<Part, String> addModPartNameCol;

    @FXML
    private TableColumn<Part, Integer> addModPartInvCol;

    @FXML
    private TableColumn<Part, Double> addModPartPriceCol;

    @FXML
    private TableView<Part> tableModProductDelete;
    
    @FXML
    private TableColumn<Part, Integer> addModPartIDColDelete;

    @FXML
    private TableColumn<Part, String> addModPartNameColDelete;

    @FXML
    private TableColumn<Part, Integer> addModPartInvColDelete;

    @FXML
    private TableColumn<Part, Double> addModPartPriceColDelete;
    
    private static int modifyProductIndex;
    
   
    // Methods to handle btn and events
    public static int productToModifyIndex() {
        return modifyProductIndex;
    }
   
    @FXML
    void handleClearSearch(ActionEvent event) {
        updateAddPartsTableView();
        txtModSearch.setText("");
    }

    @FXML
    void onClickModProductAdd(ActionEvent event) {
        
        Part part = tableModProductAdd.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        updateDeletePartsTableView();

    }

    @FXML
    void onClickModProductCancel(ActionEvent event) throws IOException {
        
        Parent add = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        Scene addScene = new Scene(add);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addScene);
        window.show();

    }

    @FXML
    void onClickModProductDelete(ActionEvent event) {
        
        Part parts = tableModProductDelete.getSelectionModel().getSelectedItem();
        
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
    void onClickModProductSave(ActionEvent event) throws IOException, ValidationException {
        
        String prodsName = txtModProductName.getText();
        String prodsInv = txtModProductInv.getText();
        String prodsPrice = txtModProductPrice.getText();
        String prodsMin = txtModProductMin.getText();
        String prodsMax = txtModProductMax.getText();

        try{
            exceptionMessage = Products.isValid(prodsName, Integer.parseInt(prodsMin), 
                                                Integer.parseInt(prodsMax), Integer.parseInt(prodsInv), 
                                                Double.parseDouble(prodsPrice), currentParts, exceptionMessage);
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            }
            else {
                
                System.out.println("Product name: " + prodsName);
                
                Products newProd = new Products();
                newProd.setProductsID(productID);
                newProd.setProductsName(prodsName);
                newProd.setProductsInStock(Integer.parseInt(prodsInv));
                newProd.setProductsPrice(Double.parseDouble(prodsPrice));
                newProd.setProductsMin(Integer.parseInt(prodsMin));
                newProd.setProductsMax(Integer.parseInt(prodsMax));
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
    void onClickModProductSearch(ActionEvent event) {
        
        String searchPart = txtModSearch.getText();
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
            Part tempPart = getAllParts().get(partIndex);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(tempPart);
            
            tableModProductAdd.setItems(tempPartList);
            
        }

    }


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Products product = getAllProducts().get(productIndex);
        
        productID = getAllProducts().get(productIndex).getProductsID();
        lblModProdID.setText("Auto-Gen: " + productID);
        txtModProductName.setText(product.getProductsName());
        txtModProductInv.setText(Integer.toString(product.getProductsInStock()));
        txtModProductPrice.setText(Double.toString(product.getProductsPrice()));
        txtModProductMin.setText(Integer.toString(product.getProductsMin()));
        txtModProductMax.setText(Integer.toString(product.getProductsMax()));
        currentParts = product.getProductsParts();
        addModPartIDCol.setCellValueFactory(cellData -> cellData.getValue().pID().asObject());
        addModPartNameCol.setCellValueFactory(cellData -> cellData.getValue().pName());
        addModPartInvCol.setCellValueFactory(cellData -> cellData.getValue().pStock().asObject());
        addModPartPriceCol.setCellValueFactory(cellData -> cellData.getValue().pPrice().asObject());
        addModPartIDColDelete.setCellValueFactory(cellData -> cellData.getValue().pID().asObject());
        addModPartNameColDelete.setCellValueFactory(cellData -> cellData.getValue().pName());
        addModPartInvColDelete.setCellValueFactory(cellData -> cellData.getValue().pStock().asObject());
        addModPartPriceColDelete.setCellValueFactory(cellData -> cellData.getValue().pPrice().asObject());
        
        updateAddPartsTableView();
        updateDeletePartsTableView();
       
    }

    public void updateAddPartsTableView() {
        tableModProductAdd.setItems(getAllParts());
    }

    public void updateDeletePartsTableView() {
        tableModProductDelete.setItems(currentParts);
    }   
}
