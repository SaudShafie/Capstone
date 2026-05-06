package org.example.capstone1.Service;

import org.example.capstone1.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductService {
    ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        return products;
    }
    public boolean addProduct(Product product){
        for (Product x : products){
            if (x.getId().equals(product.getId())){
                return false;
            }

        }
        products.add(product);
        return true;
    }
    public boolean updateProduct(String id, Product product){
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)){
                products.set(i,product);
                return true;
            }
        }
        return false;
    }
    public boolean deleteProduct(String id){
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean checkId(String id){
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    public Product getById(String id){
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)){
                return products.get(i);
            }
        }
        return null;
    }

    public boolean checkName(String name){
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
