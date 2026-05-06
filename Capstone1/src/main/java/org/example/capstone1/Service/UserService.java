package org.example.capstone1.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1.Model.Product;
import org.example.capstone1.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ProductService productService;
    private final MerchantStockService merchantStockService;
    ArrayList<User> users= new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }
    public boolean addUser(User user){
        for (User x : users){
            if (x.getId().equals(user.getId())){
                return false;
            }

        }
        users.add(user);
        return true;
    }
    public boolean updateUser(String id , User user){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                users.set(i,user);
                return true;
            }
        }
        return false;
    }
    public boolean deleteUser(String id){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean checkId(String id){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    public boolean isAdmin(String id){
        User user = getById(id);
        return user != null && user.getRole().equals("Admin");
    }
    public boolean isCustomer(String id){
        User user = getById(id);
        return user != null && user.getRole().equals("Customer");
    }
    public User getById(String id){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                return users.get(i);
            }
        }
        return null;
    }
    public void deductFromBalance(String userId,double amount){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userId)){
                users.get(i).setBalance(users.get(i).getBalance()-amount);
            }
        }


    }
    public void addToBalance(String userId,double amount){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(userId)){
                users.get(i).setBalance(users.get(i).getBalance()+amount);
            }
        }


    }

    public void addPurchase(String userId, String productId, String merchantId, int quantity) {
        User user = getById(userId);
        if (user == null || quantity <= 0) {
            return;
        }
        user.getPurchases().add(new String[]{productId, merchantId, String.valueOf(quantity)});
    }

    public int getQuantityAndCancelPurchase(String userId, String productId, String merchantId) {
        User user = getById(userId);
        if (user == null) {
            return 0;
        }
        ArrayList<String[]> purchases = user.getPurchases();
        for (int i = 0; i < purchases.size(); i++) {
            String[] purchase = purchases.get(i);
            if (purchase.length >= 3
                    && purchase[0].equals(productId)
                    && purchase[1].equals(merchantId)) {
                int quantity = Integer.parseInt(purchase[2]);
                purchases.remove(i);
                return quantity;
            }
        }
        return 0;
    }

    public ArrayList<String[]> getPurchasesByUserId(String userId) {
        User user = getById(userId);
        if (user == null) {
            return new ArrayList<>();
        }
        return user.getPurchases();
    }

    public boolean checkProductId(String productId) {
        return productService.checkId(productId);
    }

    public boolean checkMerchantId(String merchantId) {
        return merchantStockService.checkMerchantId(merchantId);
    }

    public boolean hasEnoughStock(String productId, String merchantId, int quantity) {
        return merchantStockService.hasEnoughStock(productId, merchantId, quantity);
    }

    public String findBestOfferMerchantId(String productId, int quantity) {
        return merchantStockService.findBestOfferMerchantId(productId, quantity);
    }

    public Product getProductById(String productId) {
        return productService.getById(productId);
    }

    public void reduceStock(String productId, String merchantId, int quantity) {
        merchantStockService.reduceStock(productId, merchantId, quantity);
    }

    public void increaseStock(String productId, String merchantId, int quantity) {
        merchantStockService.increaseStock(productId, merchantId, quantity);
    }
}
