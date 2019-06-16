
package model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
/**
 *
 * @author hosea
 */
public class Inventory {
    //  Observable list for Part and Products
    private final static ObservableList<Part> allParts = FXCollections.observableArrayList(); //Parts
    private final static ObservableList<Products> allProducts = FXCollections.observableArrayList(); //Products
    
    // Variables for part and product counts
    private static int partsCount = 0;
    private static int productsCount = 0;
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /**
     * 
     * @param parts 
     * Create methods for parts
     * add parts
     * get the amount of parts in list
     * lookup parts
     * lookup parts name
     * update parts
     * delete parts
     * get all parts in list
     */
    public static void addParts(Part parts){
        allParts.add(parts);
    }
    
    public static void deleteParts(Part parts){
        allParts.remove(parts);
    }
    
    public static void updatePart(int index, Part parts){
        allParts.set(index, parts);
    }
    
    public static int partCount(){
        partsCount++;
        return partsCount;
    }
   
    public static int lookupPart(String findParts) {
        boolean isFound = false;
        int index = 0;
        if (isInteger(findParts)) {
            for (int i = 0; i < allParts.size(); i++) {
                if(Integer.parseInt(findParts) == allParts.get(i).getPartsID()) {
                    index = i;
                    isFound = true;
                }
            }
        }
        else {
            for (int i = 0; i < allParts.size(); i++) {
                findParts = findParts.toLowerCase();
                if (findParts.equals(allParts.get(i).getPartsName().toLowerCase())) {
                    index = i;
                    isFound = true;
                }
            }
        }

        if (isFound == true) {
            return index;
        }
        else {
            System.out.println("No parts found.");
            return -1;
        }
    }
    
    public static boolean deletePart(Part findParts){
        boolean isFound = false;
        for(int i = 0; i < allProducts.size(); i++){
            if(allProducts.get(i).getProductsParts().contains(findParts)){
                if (!allProducts.get(i).getProductsParts().isEmpty()){
                    isFound = true;
                }       
            }
        }
        return isFound;
    }
    
    
    
     
    /**************************************Products************************************/
    
    /**
     * Products
     * @return
     */
    public static ObservableList<Products> getAllProducts(){
        return allProducts;
    }
    
    public static void addProducts(Products product){
        allProducts.add(product);
    }
    
    public static void deleteProducts(Products product){
        allProducts.remove(product);
    }
    
    public static void updateProducts(int index, Products product){
        allProducts.set(index, product);
    }
    
    public static int productsCount(){
        productsCount++;
        return productsCount;
    }
   
    public static int lookupProducts(String findProduct){
        boolean isFound = false;
        int index = 0;
        if(isInteger(findProduct)){
            for(int i = 0; i < allProducts.size(); i++){
                if(Integer.parseInt(findProduct) == allProducts.get(i).getProductsID()){
                    index = i;
                    isFound = true;
                }
            }
        }
        else {
            for (int i = 0; i < allProducts.size(); i++) {
                if (findProduct.equals(allProducts.get(i).getProductsName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
        if (isFound == true) {
            return index;
        }
        else {
            System.out.println("No products found.");
            return -1;
        }
    }
    
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean deletedProducts(Products product) {
        boolean isFound = false;
        int productID = product.getProductsID();
        for (int i=0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductsID() == productID) {
                if (!allProducts.get(i).getProductsParts().isEmpty()) {
                    isFound = true;
                }
            }
        }
       return isFound;
    } 
}
