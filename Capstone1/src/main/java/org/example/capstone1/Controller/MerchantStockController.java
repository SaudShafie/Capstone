package org.example.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1.ApiResponse.ApiResponse;
import org.example.capstone1.Model.MerchantStock;
import org.example.capstone1.Service.MerchantStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;
    @GetMapping("/get-merchants-stocks")
    public ResponseEntity<?> getMerchantStock(){
        ArrayList<MerchantStock> merchantStocks= merchantStockService.getMerchantStocks();
        if (merchantStocks.isEmpty()){
            return ResponseEntity.status(200).body(new ApiResponse("There is no Merchant Stocks"));
        }
        return ResponseEntity.status(200).body(merchantStocks);
    }
    @PostMapping("/add-merchant-stock")
    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantStockService.addMerchantStocks(merchantStock)){
            return ResponseEntity.status(200).body(new ApiResponse("New merchant stock has been added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant stock id should be unique"));
    }
    @PutMapping("/update-merchant-stock/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id,@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantStockService.updateMerchantStocks(id,merchantStock)){
            return ResponseEntity.status(200).body(new ApiResponse("merchant stock has been updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant stock id not found!"));
    }
    @DeleteMapping("/delete-merchant-stock/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id){
        if (merchantStockService.deleteMerchantStocks(id)){
            return ResponseEntity.status(200).body(new ApiResponse("merchant stock has been deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant stock id not found!"));
    }
    @PutMapping("/add-to-stock/{id}/{amount}")
    public ResponseEntity<?> addToStock(@PathVariable String id ,@PathVariable int amount){
        if (amount<=0){
            return  ResponseEntity.status(400).body(new ApiResponse("You can not add 0 or less to the stock"));
        }
        if (merchantStockService.addToStock(id,amount)){
            return ResponseEntity.status(200).body(new ApiResponse("The stock has been updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant stock id not found!"));
    }

}
