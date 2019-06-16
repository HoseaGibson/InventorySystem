/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.bind.ValidationException;

/**
 *
 * @author hosea
 */
public class Products {
     //Declare Variables
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private final IntegerProperty productsID, productsiInStock, productsMin, productsMax ;
    private final StringProperty productsName;
    private final DoubleProperty productsPrice;
    
    //Constructer Method
    public Products(){
        productsID = new SimpleIntegerProperty();
        productsiInStock = new SimpleIntegerProperty();
        productsMin = new SimpleIntegerProperty();
        productsMax = new SimpleIntegerProperty();
        productsName = new SimpleStringProperty();
        productsPrice = new SimpleDoubleProperty();
    }
    
    //Getters
    public IntegerProperty prID(){
        return productsID;
    }
    
    public IntegerProperty prStock(){
        return productsiInStock;
    }
    
    public IntegerProperty prMin(){
        return productsMin;
    }
    
    public IntegerProperty prMax(){
        return productsMax;
    }
    
    public StringProperty prName(){
        return productsName;
    }
    
    public DoubleProperty prPrice(){
        return productsPrice;
    }
    
    public int getProductsID(){
        return this.productsID.get();
    }
    
    public int getProductsInStock(){
        return this.productsiInStock.get();
    }
    
    public int getProductsMin(){
        return this.productsMin.get();
    }
    
    public int getProductsMax(){
        return this.productsMax.get();
    }
    
    public String getProductsName(){
        return this.productsName.get();
    }
    
    public double getProductsPrice(){
        return this.productsPrice.get();
    }
    
    public int getProductCount(){
        return allParts.size();
    }
    
    public ObservableList<Part> getProductsParts(){
        return allParts;
    }
    
    //Setters
    public void setProductsID(int productsID){
        this.productsID.set(productsID);
    }
    
    public void setProductsInStock(int productsiInStock){
        this.productsiInStock.set(productsiInStock);
    }
    
    public void setProductsMax(int productsMax){
        this.productsMax.set(productsMax);
    }
    
    public void setProductsMin(int productsMin){
        this.productsMin.set(productsMin);
    }
    
    public void setProductsName(String productsName){
        this.productsName.set(productsName);
    }
    
    public void setProductsPrice(double productsPrice){
        this.productsPrice.set(productsPrice);
    }
    
    public void setProductParts(ObservableList<Part> allParts){
        this.allParts = allParts;
    }
    
    
    //Validate
    public static String isValid(String name, int min, int max, int inv, double price, ObservableList<Part> parts, String errorMessage) throws ValidationException{
        double totalPrice = 0.00;
        
        for (int i = 0; i < parts.size(); i++) {
             totalPrice = totalPrice + parts.get(i).getPartsPrice();
         }
        
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
        if (totalPrice > price) {
            errorMessage = errorMessage + "Total must be greater than the price!";
        }
        return errorMessage;       
    }    
}

