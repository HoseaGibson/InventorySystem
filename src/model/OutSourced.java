/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author hosea
 */
public class OutSourced extends Part {
    
    private final StringProperty companyName;
    
    public OutSourced(){
        super();
        companyName = new SimpleStringProperty();
    }
    
    public String getCompanyName(){
        return this.companyName.get();
    }
    
    public void setCompanyName(String companyName){
        this.companyName.set(companyName);
    }
    
}
