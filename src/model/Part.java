/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.*;

/**
 *
 * @author hosea
 */
public abstract class Part {
    
    //Declare Variables
    private final IntegerProperty partsID, inStock, min, max ;
    private final StringProperty partsName;
    private final DoubleProperty partPrice;
    
    //Constructer Method
    public Part(){
        partsID = new SimpleIntegerProperty();
        inStock = new SimpleIntegerProperty();
        min = new SimpleIntegerProperty();
        max = new SimpleIntegerProperty();
        partsName = new SimpleStringProperty();
        partPrice = new SimpleDoubleProperty();
    }
    
    //Getters
    public IntegerProperty pID(){
        return partsID;
    }
    
    public IntegerProperty pStock(){
        return inStock;
    }
    
    public IntegerProperty pMin(){
        return min;
    }
    
    public IntegerProperty pMax(){
        return max;
    }
    
    public StringProperty pName(){
        return partsName;
    }
    
    public DoubleProperty pPrice(){
        return partPrice;
    }
    
    public int getPartsID(){
        return this.partsID.get();
    }
    
    public int getInStock(){
        return this.inStock.get();
    }
    
    public int getPartsMin(){
        return this.min.get();
    }
    
    public int getPartsMax(){
        return this.max.get();
    }
    
    public String getPartsName(){
        return this.partsName.get();
    }
    
    public double getPartsPrice(){
        return this.partPrice.get();
    }
    
    //Setters
    public void setPartsID(int partsID){
        this.partsID.set(partsID);
    }
    
    public void setPartsInStock(int inStock){
        this.inStock.set(inStock);
    }
    
    public void setPartsMax(int max){
        this.max.set(max);
    }
    
    public void setPartsMin(int min){
        this.min.set(min);
    }
    
    public void setPartsName(String partsName){
        this.partsName.set(partsName);
    }
    
    public void setPartsPrice(double partPrice){
        this.partPrice.set(partPrice);
    }
    
    //Validate
    public static String isValid(String name, int min, int max, int inv, double price, String errorMessage){
        
        
        if (name == null) {
            errorMessage = errorMessage + "Please enter name!";
        }
        if (inv < 1) {
            errorMessage = errorMessage + "The inventory cannot be less than 1! ";
        }
        if (price <= 0) {
            errorMessage = errorMessage + "Please enter a positive value! ";
        }
        if (max < min) {
            errorMessage = errorMessage + "The Max must be greater Min. ";
        }
        if (inv < min || inv > max) {
            errorMessage = errorMessage + "The inventory must be between the Min and Max. ";
        }
        return errorMessage;         
    }   
}
