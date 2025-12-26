package finances_practice.gmejia.controller;

import finances_practice.gmejia.dto.request.TransactionPerUserRequest;
import finances_practice.gmejia.dto.request.TransactionRegisterRequest;
import finances_practice.gmejia.dto.request.TransactionUpdateRequest;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.TransactionPerUserDetailResponse;
import finances_practice.gmejia.dto.response.TransactionPerUserResponse;
import finances_practice.gmejia.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/dashboard")
    public ResponseEntity<JsonNode> getDashboard(){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getDashboard());
    }

    //@ModelAttribute para varios valores en las consultas por medio de la URL
    //@RequestParam para 1 o 2 consultas por medio de la URL
    //@PathVariable consultas especificas para IDs unicos
    @GetMapping("/perUser")
    public ResponseEntity<List<TransactionPerUserResponse>> getTransactions(@ModelAttribute TransactionPerUserRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactions(request));
    }

    @GetMapping("/perUser/detail/{id}")
    public ResponseEntity<TransactionPerUserDetailResponse> getTransactionsDetails(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactionsDetails(id));
    }

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> register(@RequestBody @Valid TransactionRegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(request));
    }

    @PutMapping("/update")
    public ResponseEntity<GeneralResponse> update(@RequestBody @Valid TransactionUpdateRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.updateTransaction(request));
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<GeneralResponse> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.deleteTransaction(id));
    }
}
