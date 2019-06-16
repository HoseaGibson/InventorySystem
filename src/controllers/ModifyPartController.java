/*
 * Modify Part Contoller.
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
import static model.Inventory.getAllParts;
import model.OutSourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author hosea
 */
public class ModifyPartController implements Initializable {

    @FXML
    private RadioButton radioModPartInHouse;
    @FXML
    private RadioButton radioModPartOutSourced;
    @FXML
    private Label lblModPartID;
    @FXML
    private TextField txtModPartName;
    @FXML
    private TextField txtModPartInventory;
    @FXML
    private TextField txtModPartPriceCost;
    @FXML
    private TextField txtModPartMax;
    @FXML
    private TextField txtModPartCompany;
    @FXML
    private TextField txtModPartMin;
    @FXML
    private Label lblCompName;
    
    // Variables
    private boolean isOutSourced;
    private static int modifyPartIndex;
    int partIndex = partToModifyIndex();
    private String exceptionMessage = new String();
    private int partID;
    
    // Methods for handling buttons and events
    public static int partToModifyIndex() {
        return modifyPartIndex;
    }
    
    @FXML
    void onClickRadioInHouse(ActionEvent event) {
        
        isOutSourced = false;
        radioModPartOutSourced.setSelected(false);
        lblCompName.setText("Machine ID");
        txtModPartCompany.setText("");
        txtModPartCompany.setPromptText("Machine ID");
        
    }

    @FXML
    void onClickRadioOutSourced(ActionEvent event) {
        
        isOutSourced = true;
        radioModPartInHouse.setSelected(false);
        lblCompName.setText("Company Name");
        txtModPartCompany.setText("");
        txtModPartCompany.setPromptText("Company Name");
        
    }

    @FXML
    void onClickModCancelPart(ActionEvent event) throws IOException {
        
        Parent add = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        Scene addScene = new Scene(add);
        
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addScene);
        window.show();
        
    }

    @FXML
    void onClickModSavePart(ActionEvent event) throws IOException {
        
        String mName = txtModPartName.getText();
        String mInv = txtModPartInventory.getText();
        String mPrice = txtModPartPriceCost.getText();
        String mMin = txtModPartMin.getText();
        String mMax = txtModPartMax.getText();
        String mComp = txtModPartCompany.getText();

        try {
            exceptionMessage = Part.isValid(mName, Integer.parseInt(mMin), 
                                                Integer.parseInt(mMax), Integer.parseInt(mInv), 
                                                Double.parseDouble(mPrice), exceptionMessage);
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
            }
            else {
                if (isOutSourced == false) {
                    
                    System.out.println("Part name: " + mName);
                    
                    InHouse inPart = new InHouse();
                    inPart.setPartsID(partID);
                    inPart.setPartsName(mName);
                    inPart.setPartsInStock(Integer.parseInt(mInv));
                    inPart.setPartsPrice(Double.parseDouble(mPrice));
                    inPart.setPartsMin(Integer.parseInt(mMin));
                    inPart.setPartsMax(Integer.parseInt(mMax));
                    inPart.setMachineID(Integer.parseInt(mComp));
                    Inventory.updatePart(partIndex, inPart);
                    
                }
                else {
                    
                    System.out.println("Part name: " + mName);
                    
                    OutSourced outPart = new OutSourced();
                    outPart.setPartsID(partID);
                    outPart.setPartsName(mName);
                    outPart.setPartsInStock(Integer.parseInt(mInv));
                    outPart.setPartsPrice(Double.parseDouble(mPrice));
                    outPart.setPartsMin(Integer.parseInt(mMin));
                    outPart.setPartsMax(Integer.parseInt(mMax));
                    outPart.setCompanyName(mComp);
                    
                    Inventory.updatePart(partIndex, outPart);
                }

                Parent modifyProductSave = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
                Scene scene = new Scene(modifyProductSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            }
        }
        catch (NumberFormatException e) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Part");
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();
            
        }
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Part part = getAllParts().get(partIndex);
        
        partID = getAllParts().get(partIndex).getPartsID();
        lblModPartID.setText("Auto-Gen: " + partID);
        txtModPartName.setText(part.getPartsName());
        txtModPartInventory.setText(Integer.toString(part.getInStock()));
        txtModPartPriceCost.setText(Double.toString(part.getPartsPrice()));
        txtModPartMin.setText(Integer.toString(part.getPartsMin()));
        txtModPartMax.setText(Integer.toString(part.getPartsMax()));
        
        if (part instanceof InHouse) {
            
            lblCompName.setText("Machine ID");
            txtModPartCompany.setText(Integer.toString(((InHouse) getAllParts().get(partIndex)).getMachineID()));
            radioModPartInHouse.setSelected(true);
            
        }
        else {
            
            lblCompName.setText("Company Name");
            txtModPartCompany.setText(((OutSourced) getAllParts().get(partIndex)).getCompanyName());
            radioModPartOutSourced.setSelected(true);
            
        }   
    }       
}
