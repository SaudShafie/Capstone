package org.example.capstone1.Service;

import org.example.capstone1.Model.MerchantStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantStockService {
    ArrayList<MerchantStock> merchantStocks=new ArrayList<>();

    public ArrayList<MerchantStock> getMerchantStocks() {
        return merchantStocks;
    }
    public boolean addMerchantStocks(MerchantStock merchantStock){
        for (MerchantStock x : merchantStocks){
            if (x.getId().equals(merchantStock.getId())){
                return false;
            }

        }
        merchantStocks.add(merchantStock);
        return true;

    }
    public boolean updateMerchantStocks(String id , MerchantStock merchantStock){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)){
                merchantStocks.set(i,merchantStock);
                return true;
            }
        }
        return false;
    }
    public  boolean deleteMerchantStocks(String id){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }
    public boolean addToStock(String id,int adds){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)){
                merchantStocks.get(i).setStock(merchantStocks.get(i).getStock()+adds);
                return true;
            }
        }
        return false;
    }
    public boolean checkId(String id){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)){
                return true;
            }
        }
        return false;
    }
    public boolean checkMerchantId(String merchantId){
        for (MerchantStock merchantStock : merchantStocks){
            if (merchantStock.getMerchantId().equals(merchantId)){
                return true;
            }
        }
        return false;
    }
    public boolean inStockByIdOfProduct(String id){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getProductId().equals(id)){
                if (merchantStocks.get(i).getStock()>0)return true;

            }
        }
        return false;
    }
    public boolean hasEnoughStock(String productId, String merchantId, int quantity){
        if (quantity <= 0){
            return false;
        }
        for (MerchantStock ms : merchantStocks){
            if (ms.getProductId().equals(productId) && ms.getMerchantId().equals(merchantId)){
                return ms.getStock() >= quantity;
            }
        }
        return false;
    }
    public String findBestOfferMerchantId(String productId, int quantity){
        if (quantity <= 0){
            return null;
        }
        String bestMerchantId = null;
        for (MerchantStock ms : merchantStocks){
            if (ms.getProductId().equals(productId) && ms.getStock() >= quantity){
                if (bestMerchantId == null || ms.getMerchantId().compareTo(bestMerchantId) < 0){
                    bestMerchantId = ms.getMerchantId();
                }
            }
        }
        return bestMerchantId;
    }
    public void reduceStock(String productId, String merchantId, int quantity){
        for (MerchantStock ms : merchantStocks){
            if (ms.getProductId().equals(productId) &&
                    ms.getMerchantId().equals(merchantId)){
                if (quantity <= 0 || ms.getStock() < quantity){
                    return;
                }
                ms.setStock(ms.getStock() - quantity);
                ms.setSoldQuantity(ms.getSoldQuantity() + quantity);
                return;
            }
        }
    }
    public MerchantStock getByProductIdAndMerchantId(String ProductId,String merchantId){
        for (MerchantStock x: merchantStocks){
            if (x.getMerchantId().equals(merchantId)&&x.getProductId().equals(ProductId)){
                return x;
            }
        }
        return null;
    }
    public void increaseStock(String productId, String merchantId, int quantity){

        for (MerchantStock ms : merchantStocks){

            if (ms.getProductId().equals(productId) &&
                    ms.getMerchantId().equals(merchantId)){

                ms.setStock(ms.getStock() + quantity);

                ms.setSoldQuantity(
                        Math.max(0, ms.getSoldQuantity() - quantity)
                );
            }
        }
    }
    public ArrayList<MerchantStock> lowInStock(String merchantId){
        ArrayList<MerchantStock> merchantStocks1= new ArrayList<>();
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getMerchantId().equals(merchantId)){
                if (merchantStocks.get(i).getStock()<5){
                    merchantStocks1.add(merchantStocks.get(i));
                }
            }
        }
        return merchantStocks1;
    }

}
