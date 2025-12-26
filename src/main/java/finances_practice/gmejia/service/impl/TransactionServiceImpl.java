package finances_practice.gmejia.service.impl;

import finances_practice.gmejia.dto.request.TransactionPerUserRequest;
import finances_practice.gmejia.dto.request.TransactionRegisterRequest;
import finances_practice.gmejia.dto.request.TransactionUpdateRequest;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.TransactionPerUserDetailResponse;
import finances_practice.gmejia.dto.response.TransactionPerUserResponse;
import finances_practice.gmejia.dto.response.TransactionSummary;
import finances_practice.gmejia.entity.CategoriesEntity;
import finances_practice.gmejia.entity.PaymentMethodsEntity;
import finances_practice.gmejia.entity.TransactionEntity;
import finances_practice.gmejia.entity.UserEntity;
import finances_practice.gmejia.exception.BusinessException;
import finances_practice.gmejia.mapper.TransactionMapper;
import finances_practice.gmejia.repository.CategoriesRepository;
import finances_practice.gmejia.repository.PaymentMethodsRepository;
import finances_practice.gmejia.repository.TransactionRepository;
import finances_practice.gmejia.service.TransactionService;
import finances_practice.gmejia.utils.UserContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final CategoriesRepository categoriesRepository;
    private final ObjectMapper objectMapper;
    private final UserContextUtils userContextUtils;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional(readOnly = true)
    public JsonNode getDashboard(){
        UserEntity user = userContextUtils.getCurrentUser();
        String jsonRaw = transactionRepository.getDashboardData(user.getId());

        try{
            if(jsonRaw == null){
                return objectMapper.createObjectNode();
            }
            return objectMapper.readTree(jsonRaw);
        }catch(Exception e){
            throw new BusinessException("Error interno al procesar datos para el dashboard", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionPerUserResponse> getTransactions(TransactionPerUserRequest request){
        UserEntity user = userContextUtils.getCurrentUser();

        //Lista del repositorio
        List<TransactionSummary> rawList = transactionRepository.findHistory(user.getId(), request);

        //Limpiamos la lista (se elimina el decoratedClass y targetClass
        return rawList.stream()
                .map(t -> TransactionPerUserResponse.builder()
                        .id(t.getId())
                        .amount(t.getAmount())
                        .description(t.getDescription())
                        .date(t.getDate())
                        .categoryName(t.getCategory_name())
                        .typeName(t.getType_name())
                        .paymentMethod(t.getPayment_method())
                        .isRecurring(t.getIs_recurring())
                        .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionPerUserDetailResponse getTransactionsDetails(Long id){
        UserEntity user = userContextUtils.getCurrentUser();

        TransactionEntity transaction = transactionRepository.findByIdAndStatus(id,true)
                .orElseThrow(() -> new BusinessException("Transaccion eliminada o no encontrada", HttpStatus.NOT_FOUND));

        //Validar que la transaccion solo sea del usuario autenticado
        if(!transaction.getUserId().equals(user.getId())){
            throw new BusinessException("No tiene permisos para ver esta transaccion", HttpStatus.UNAUTHORIZED);
        }

        return transactionMapper.toResponseTransaction(transaction);
    }

    @Override
    @Transactional
    public GeneralResponse createTransaction(TransactionRegisterRequest request){
        UserEntity user = userContextUtils.getCurrentUser();

        TransactionEntity entity = transactionMapper.toEntity(request);

        if (request.getCategoryId() != null) {
            CategoriesEntity category = categoriesRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new BusinessException("Categoría no encontrada", HttpStatus.BAD_REQUEST));
            entity.setCategories(category);
        } else {
            entity.setCategories(null);
        }

        if (request.getPaymentMethodId() != null) {
            PaymentMethodsEntity paymentMethod = paymentMethodsRepository.findById(request.getPaymentMethodId())
                    .orElseThrow(() -> new BusinessException("Método de pago no encontrado", HttpStatus.BAD_REQUEST));
            entity.setPaymentMethods(paymentMethod);
        } else {
            entity.setPaymentMethods(null);
        }

        entity.setUserId(user.getId());
        entity.setCreatedBy(user.getId());

        TransactionEntity saved = transactionRepository.save(entity);
        return GeneralResponse.builder()
                .id(saved.getId())
                .message("Transaccion registrada exitosamente")
                .build();
    }

    @Override
    @Transactional
    public GeneralResponse updateTransaction(TransactionUpdateRequest request){
        UserEntity user = userContextUtils.getCurrentUser();

        TransactionEntity transaction = transactionRepository.findByIdAndStatus(request.getId(),true)
                .orElseThrow(() -> new BusinessException("Transaccion eliminada o no encontrada", HttpStatus.NOT_FOUND));

        if(!transaction.getUserId().equals(user.getId())){
            throw new BusinessException("Transacción no encontrada", HttpStatus.NOT_FOUND);
        }

        transactionMapper.updateEntity(request, transaction);

        if (request.getCategoryId() != null) {
            CategoriesEntity category = categoriesRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new BusinessException("Categoría no encontrada", HttpStatus.BAD_REQUEST));
            transaction.setCategories(category);
        } else {
            transaction.setCategories(null);
        }

        if (request.getPaymentMethodId() != null) {
            PaymentMethodsEntity paymentMethod = paymentMethodsRepository.findById(request.getPaymentMethodId())
                    .orElseThrow(() -> new BusinessException("Método de pago no encontrado", HttpStatus.BAD_REQUEST));
            transaction.setPaymentMethods(paymentMethod);
        } else {
            transaction.setPaymentMethods(null);
        }
        transaction.setUpdatedBy(user.getId());
        transactionRepository.save(transaction);

        return GeneralResponse.builder()
                .id(transaction.getId())
                .message("Transaccion actualizada correctamente")
                .build();
    }

    @Override
    @Transactional
    public GeneralResponse deleteTransaction(Long id){
        UserEntity user = userContextUtils.getCurrentUser();

        TransactionEntity transaction = transactionRepository.findByIdAndStatus(id,true)
                .orElseThrow(() -> new BusinessException("Transaccion eliminada o no encontrada", HttpStatus.NOT_FOUND));

        if(!transaction.getUserId().equals(user.getId())){
            throw new BusinessException("Transaccion no encontrada", HttpStatus.NOT_FOUND);
        }

        transaction.setStatus(false);
        transaction.setUpdatedBy(user.getId());
        transactionRepository.save(transaction);

        return GeneralResponse.builder()
                .id(transaction.getId())
                .message("Transaccion eliminada correctamente")
                .build();
    }
}
