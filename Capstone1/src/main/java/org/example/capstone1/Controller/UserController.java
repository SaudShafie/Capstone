package org.example.capstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone1.ApiResponse.ApiResponse;
import org.example.capstone1.Model.Product;
import org.example.capstone1.Model.Purchase;
import org.example.capstone1.Model.User;
import org.example.capstone1.Service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ProductService productService;
    private final MerchantService merchantService;
    private final MerchantStockService merchantStockService;
    private final PurchaseService purchaseService;

    @GetMapping("/get-users")
    public ResponseEntity<?> getUsers() {
        ArrayList<User> users = userService.getUsers();
        if (users.isEmpty()) {
            return ResponseEntity.status(200).body(new ApiResponse("There is no users"));
        }
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (userService.addUser(user)) {
            return ResponseEntity.status(200).body(new ApiResponse("New user has been added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("user id should be unique"));
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (userService.updateUser(id, user)) {
            return ResponseEntity.status(200).body(new ApiResponse("user has been updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("user id not found!"));
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.status(200).body(new ApiResponse("user has been deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("user id not found!"));
    }

    @PutMapping("/buy-product/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {
        if (!userService.checkId(userId))
            return ResponseEntity.status(400).body(new ApiResponse("User id is not found"));
        if (!productService.checkId(productId))
            return ResponseEntity.status(400).body(new ApiResponse("Product id is not found"));
        if (!merchantService.checkId(merchantId))
            return ResponseEntity.status(400).body(new ApiResponse("Merchant id is not found"));
        double price = productService.getById(productId).getPrice();
        double balance = userService.getById(userId).getBalance();
        boolean haveBalance = balance >= price;
        if (!merchantStockService.hasEnoughStock(productId, merchantId, 1))
            return ResponseEntity.status(400).body(new ApiResponse("product is out of stock"));
        if (!haveBalance) return ResponseEntity.status(400).body(new ApiResponse("User balance is insufficient "));
        merchantStockService.reduceStock(productId,merchantId, 1);
        userService.deductFromBalance(userId, price);
        purchaseService.addPurchase(new Purchase(userId, productId, merchantId, 1));
        return ResponseEntity.status(200).body(new ApiResponse("purchase has been made!!! your new balance is " + (balance - price)));
    }

    @PostMapping("/buy-quantity-of-product/{userId}/{productId}/{quantity}")
    public ResponseEntity<?> buyQuantityOfProduct(@PathVariable String userId, @PathVariable String productId, @PathVariable int quantity) {
        if (!userService.checkId(userId))
            return ResponseEntity.status(400).body(new ApiResponse("User id is not found"));
        if (!productService.checkId(productId))
            return ResponseEntity.status(400).body(new ApiResponse("Product id is not found"));
        if (quantity <= 0)
            return ResponseEntity.status(400).body(new ApiResponse("Quantity should be greater than zero"));

        String bestMerchantId = merchantStockService.findBestOfferMerchantId(productId, quantity);
        if (bestMerchantId == null)
            return ResponseEntity.status(400).body(new ApiResponse("No merchant can fulfill this quantity"));

        double price = productService.getById(productId).getPrice();
        double balance = userService.getById(userId).getBalance();
        boolean haveBalance = (balance >= (price * quantity));
        if (!haveBalance) return ResponseEntity.status(400).body(new ApiResponse("User balance is insufficient "));
        merchantStockService.reduceStock(productId, bestMerchantId,quantity);
        userService.deductFromBalance(userId, price*quantity);
        purchaseService.addPurchase(new Purchase(userId, productId, bestMerchantId, quantity));
        return ResponseEntity.status(200).body(new ApiResponse("purchase has been made with merchant "
                + bestMerchantId + "!!! your new balance is " + (balance - (price * quantity))));
    }

    @GetMapping("/best-offer/{productId}/{quantity}")
    public ResponseEntity<?> getBestOffer(@PathVariable String productId, @PathVariable int quantity) {
        if (!productService.checkId(productId))
            return ResponseEntity.status(400).body(new ApiResponse("Product id is not found"));
        if (quantity <= 0)
            return ResponseEntity.status(400).body(new ApiResponse("Quantity should be greater than zero"));

        String bestMerchantId = merchantStockService.findBestOfferMerchantId(productId, quantity);
        if (bestMerchantId == null)
            return ResponseEntity.status(400).body(new ApiResponse("No merchant can fulfill this quantity"));

        double totalPrice = productService.getById(productId).getPrice() * quantity;
        return ResponseEntity.status(200).body(new ApiResponse("Best offer merchant: "
                + bestMerchantId + " | Total price: " + totalPrice));
    }

    @PutMapping("/refund/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> refund(@PathVariable String userId, @PathVariable String productId,@PathVariable String merchantId) {
        if (!userService.checkId(userId))
            return ResponseEntity.status(400).body(new ApiResponse("User id is not found"));
        if (!productService.checkId(productId))
            return ResponseEntity.status(400).body(new ApiResponse("Product id is not found"));
        if (!merchantService.checkId(merchantId))
            return ResponseEntity.status(400).body(new ApiResponse("Merchant id is not found"));

        int quantity = purchaseService.getQuantityAndCancelPurchase(userId, productId, merchantId);
        if (quantity == 0)
            return ResponseEntity.status(400).body(new ApiResponse("The purchase has never been made!!"));
        merchantStockService.increaseStock(productId,merchantId, quantity);
        userService.addToBalance(userId, (productService.getById(productId).getPrice() * quantity));
        return ResponseEntity.status(200).body(new ApiResponse("Refund is done"));


    }

    @PutMapping("/transfer-from-wallet/{userFromId}/{userToId}/{amount}")
    public ResponseEntity<?> transferFromWallet(@PathVariable String userFromId, @PathVariable String userToId, @PathVariable double amount) {
        if (!userService.checkId(userFromId)) return ResponseEntity.status(400).body(new ApiResponse("the user trying to transfer is not found"));
        if (!userService.checkId(userToId)) return ResponseEntity.status(400).body(new ApiResponse("the user you trying to transfer to is not found"));
        if (amount <= 0) return ResponseEntity.status(400).body(new ApiResponse("Transfer amount should be greater than zero"));
        double balance = userService.getById(userFromId).getBalance();
        boolean haveBalance = (balance >= amount);
        if (!haveBalance) return ResponseEntity.status(400).body(new ApiResponse("User balance is insufficient "));
        userService.deductFromBalance(userFromId, amount);
        userService.addToBalance(userToId, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Transfer is Done successfully"));


    }
    @GetMapping("/get-user-purchases/{userId}")
    public ResponseEntity<?> getUserPurchases(@PathVariable String userId){
        if (!userService.checkId(userId))
            return ResponseEntity.status(400).body(new ApiResponse("User id is not found"));

        return ResponseEntity.status(200).body(purchaseService.getByUserId(userId));

    }

    @GetMapping("/purchase-summary/{userId}")
    public ResponseEntity<?> getPurchaseSummary(@PathVariable String userId) {
        if (!userService.checkId(userId))
            return ResponseEntity.status(400).body(new ApiResponse("User id is not found"));

        ArrayList<Purchase> userPurchases = purchaseService.getByUserId(userId);
        int totalOrders = userPurchases.size();
        int totalItems = 0;
        double totalSpent = 0;

        for (Purchase purchase : userPurchases) {
            totalItems += purchase.getQuantity();
            Product product = productService.getById(purchase.getProductId());
            if (product != null) {
                totalSpent += product.getPrice() * purchase.getQuantity();
            }
        }
        String summaryMessage = "Purchase summary | userId: " + userId
                + " | total orders: " + totalOrders
                + " | total items: " + totalItems
                + " | total spent: " + totalSpent;
        return ResponseEntity.status(200).body(new ApiResponse(summaryMessage));
    }
}
