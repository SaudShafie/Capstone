package org.example.capstone1.Service;

import org.example.capstone1.Model.Purchase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PurchaseService {
    private ArrayList<Purchase> purchases = new ArrayList<>();

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    public Purchase getByProductIdAndMerchantId(String ProductId, String merchantId) {
        for (Purchase x : purchases) {
            if (x.getMerchantId().equals(merchantId) && x.getProductId().equals(ProductId)) {
                return x;
            }
        }
        return null;
    }

    public int getQuantityAndCancelPurchase(String userId, String productId, String merchantId) {
        int p = 0;
        for (int i = 0; i < purchases.size(); i++) {
            if (purchases.get(i).getUserId().equals(userId)
                    && purchases.get(i).getProductId().equals(productId)
                    && purchases.get(i).getMerchantId().equals(merchantId)) {
                p = purchases.get(i).getQuantity();
                purchases.remove(i);
                return p;
            }
        }
        return p;
    }

    public Purchase getByProductIdAndUserId(String productId, String userId) {
        for (Purchase x : purchases) {
            if (x.getUserId().equals(userId) && x.getProductId().equals(productId)) {
                return x;
            }
        }
        return null;
    }
    public ArrayList<Purchase> getByUserId(String userId) {
        ArrayList<Purchase> purchases1=new ArrayList<>();
        for (Purchase x : purchases) {
            if (x.getUserId().equals(userId)) {
                purchases1.add(x);
            }
        }
        return purchases1;
    }

}
