package org.example.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1.ApiResponse.ApiResponse;
import org.example.capstone1.Model.Merchant;
import org.example.capstone1.Model.MerchantStock;
import org.example.capstone1.Model.Product;
import org.example.capstone1.Service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @GetMapping("/get-merchants")
    public ResponseEntity<?> getMerchant(){
        ArrayList<Merchant> merchants= merchantService.getMerchants();
        if (merchants.isEmpty()){
            return ResponseEntity.status(200).body(new ApiResponse("There is no Merchants"));
        }
        return ResponseEntity.status(200).body(merchants);
    }
    @PostMapping("/add-merchant")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantService.addMerchant(merchant)){
            return ResponseEntity.status(200).body(new ApiResponse("New merchant has been added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant id should be unique"));
    }
    @PutMapping("/update-merchant/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id,@RequestBody @Valid Merchant merchant, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantService.updateMerchant(id,merchant)){
            return ResponseEntity.status(200).body(new ApiResponse("merchant has been updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant id not found!"));
    }
    @DeleteMapping("/delete-merchant/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){
        if (merchantService.deleteMerchant(id)){
            return ResponseEntity.status(200).body(new ApiResponse("merchant has been deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant id not found!"));
    }
    @GetMapping("/merchant-report/{merchantId}")
    public ResponseEntity<?> getMerchantReport(@PathVariable String merchantId) {
        if (!merchantService.checkId(merchantId)) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        return ResponseEntity.status(200).body(merchantService.getMerchantReport(merchantId));
    }
    //Top Selling Product
    @GetMapping("/merchant-top-selling-product/{merchantId}")
    public ResponseEntity<?> getTopSelling(@PathVariable String merchantId) {
        if (!merchantService.checkId(merchantId)) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        Product topSellingProduct = merchantService.getTopSellingProduct(merchantId);
        if (topSellingProduct == null) {
            return ResponseEntity.status(400).body(new ApiResponse("No top selling product found for this merchant yet"));
        }
        return ResponseEntity.status(200).body(topSellingProduct);
    }
    @GetMapping("/get-low-in-stock/{merchantId}")
    public ResponseEntity<?> lowInStock(@PathVariable String merchantId){
        ArrayList<MerchantStock> lowInStock = merchantService.getLowInStock(merchantId);
        if (lowInStock.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("there is nothing in low"));
        }
        return ResponseEntity.status(200).body(lowInStock);
    }
}
