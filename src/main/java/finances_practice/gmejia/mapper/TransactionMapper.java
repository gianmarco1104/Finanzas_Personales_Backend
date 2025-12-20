package finances_practice.gmejia.mapper;

import finances_practice.gmejia.dto.request.TransactionRegisterRequest;
import finances_practice.gmejia.dto.request.TransactionUpdateRequest;
import finances_practice.gmejia.dto.response.CatalogResponse;
import finances_practice.gmejia.dto.response.TransactionPerUserDetailResponse;
import finances_practice.gmejia.entity.CategoriesEntity;
import finances_practice.gmejia.entity.PaymentMethodsEntity;
import finances_practice.gmejia.entity.TransactionEntity;
import finances_practice.gmejia.entity.TransactionTypesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class}, builder = @org.mapstruct.Builder(disableBuilder = true))
public interface TransactionMapper {

    TransactionPerUserDetailResponse toResponseTransaction(TransactionEntity transaction);
    CatalogResponse toCatalogCategories(CategoriesEntity categories);
    CatalogResponse toCatalogTransaction(TransactionTypesEntity transactions);
    CatalogResponse toCatalogPaymentMethods(PaymentMethodsEntity paymentMethods);


    @Mapping(target = "categories.id", source = "request.categoryId")
    @Mapping(target = "transaction.id", source = "request.transactionTypeId")
    @Mapping(target = "paymentMethods.id", source = "request.paymentMethodId")
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "id", ignore = true)
    TransactionEntity toEntity(TransactionRegisterRequest request);

    @Mapping(target = "categories.id", source = "request.categoryId")
    @Mapping(target = "transaction.id", source = "request.transactionTypeId")
    @Mapping(target = "paymentMethods.id", source = "request.paymentMethodId")
    @Mapping(target = "dateProcess", source = "request.dateProcess")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntity(TransactionUpdateRequest request, @MappingTarget TransactionEntity entity);
}
