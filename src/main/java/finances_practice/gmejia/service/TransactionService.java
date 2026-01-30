package finances_practice.gmejia.service;

import finances_practice.gmejia.dto.request.TransactionPerUserRequest;
import finances_practice.gmejia.dto.request.TransactionRegisterRequest;
import finances_practice.gmejia.dto.request.TransactionUpdateRequest;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.TransactionPerUserDetailResponse;
import finances_practice.gmejia.dto.response.TransactionPerUserResponse;
import tools.jackson.databind.JsonNode;


import java.util.List;

public interface TransactionService {
    JsonNode getDashboard();
    List<TransactionPerUserResponse> getTransactions(TransactionPerUserRequest request);
    TransactionPerUserDetailResponse getTransactionsDetails(Long id);
    GeneralResponse createTransaction(TransactionRegisterRequest request);
    GeneralResponse updateTransaction(TransactionUpdateRequest request);
    GeneralResponse deleteTransaction(Long id);
}
