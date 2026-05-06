package org.example.capstone1.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone1.Model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final ProductService productService;
    private final PurchaseService purchaseService;
    private final MerchantStockService merchantStockService;
    private ArrayList<Merchant>merchants= new ArrayList<>();
    public ArrayList<Merchant> getMerchants(){
        return merchants;

    }
    public boolean addMerchant(Merchant merchant){
        for (Merchant x : merchants){
            if (x.getId().equals(merchant.getId())){
                return false;
            }

        }
        merchants.add(merchant);
        return true;
    }
    public boolean updateMerchant(String id , Merchant merchant){
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)){
                merchants.set(i,merchant);
                return true;
            }
        }
        return false;
    }
    public boolean deleteMerchant(String id){
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)){
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean checkId(String id){
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    public Merchant getById(String id){
        for (Merchant x: merchants){
            if (x.getId().equals(id)){
                return x;
            }
        }
        return null;
    }////
    public Product getTopSellingProduct(String merchantId){
        ArrayList<MerchantStock> stocks = merchantStockService.getMerchantStocks();
        int max = 0;
        String topProductId = null;
        for (MerchantStock ms : stocks){
            if (ms.getMerchantId().equals(merchantId)){

                if (ms.getSoldQuantity() > max){
                    max = ms.getSoldQuantity();
                    topProductId = ms.getProductId();
                }
            }
        }
        if (topProductId == null) return null;
        return productService.getById(topProductId);
    }

}
