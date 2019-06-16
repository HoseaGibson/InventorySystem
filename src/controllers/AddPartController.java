/*
 * Add Part
 */
package controllers;

// Imports
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author hosea
 */
public class AddPartController implements Initializable {

    @FXML
    private RadioButton radioPartInHouse;

    @FXML
    private RadioButton radioPartOutSourced;

    @FXML
    private TextField txtPartName;

    @FXML
    private TextField txtPartInventory;

    @FXML
    private TextField txtPartPriceCost;

    @FXML
    private TextField txtPartMax;

    @FXML
    private TextField txtPartCompany;

    @FXML
    private TextField txtPartMin;
    
    @FXML
    private Label lblAddPartID;
    
    @FXML
    private Label lblChangeInOut;
    
    // Variables
    private boolean isOutSourced;
    private String exceptionMessage = new String();
    private int partsID;

    // Methods to handle button events 
    @FXML
    void onClickCancelPart(ActionEvent event) throws IOException {
        
        Parent add = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        Scene addScene = new Scene(add);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addScene);
        window.show();

    }

    @FXML
    void onClickRadioInHouse(ActionEvent event) {
        
        isOutSourced = false;
        lblChangeInOut.setText("Machine ID");
        txtPartCompany.setPromptText("Machine ID");
        radioPartOutSourced.setSelected(false);

    }

    @FXML
    void onClickRadioOutSourced(ActionEvent event) {
        
        isOutSourced = true;
        lblChangeInOut.setText("Company Name");
        txtPartCompany.setPromptText("Company Name");
        radioPartInHouse.setSelected(false);

    }

    @FXML
    void onClickSavePart(ActionEvent event) throws IOException {
        
        String name = txtPartName.getText();
        String inv = txtPartInventory.getText();
        String price = txtPartPriceCost.getText();
        String min = txtPartMin.getText();
        String max = txtPartMax.getText();
        String comp = txtPartCompany.getText();

        try {
            exceptionMessage = Part.isValid(name, Integer.parseInt(min), 
                                            Integer.parseInt(max), Integer.parseInt(inv), 
                                            Double.parseDouble(price), exceptionMessage);
            
            if (exceptionMessage.length() > 0) {
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Adding Part");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                
                exceptionMessage = "";
            }
            else {
                
                if (isOutSourced == false) {
                    
                    System.out.println("Part name: " + name);
                    
                    InHouse inHousePart = new InHouse();
                    inHousePart.setPartsID(partsID);
                    inHousePart.setPartsName(name);
                    inHousePart.setPartsPrice(Double.parseDouble(price));
                    inHousePart.setPartsInStock(Integer.parseInt(inv));
                    inHousePart.setPartsMin(Integer.parseInt(min));
                    inHousePart.setPartsMax(Integer.parseInt(max));
                    inHousePart.setMachineID(Integer.parseInt(comp));
                    
                    Inventory.addParts(inHousePart);
                    
                } else {
                    
                    System.out.println("Part name: " + name);
                    
                    OutSourced outSourcedPart = new OutSourced();
                    outSourcedPart.setPartsID(partsID);
                    outSourcedPart.setPartsName(name);
                    outSourcedPart.setPartsPrice(Double.parseDouble(price));
                    outSourcedPart.setPartsInStock(Integer.parseInt(inv));
                    outSourcedPart.setPartsMin(Integer.parseInt(min));
                    outSourcedPart.setPartsMax(Integer.parseInt(max));
                    outSourcedPart.setCompanyName(comp);
                    
                    Inventory.addParts(outSourcedPart);
                    
                }

                Parent addPartSave = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
                Scene scene = new Scene(addPartSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch(NumberFormatException e) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
            
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partsID = Inventory.partCount();
        lblAddPartID.setText("Auto-Gen: " + partsID);
        
    }      
}
