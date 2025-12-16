package finances_practice.gmejia.mapper;

import finances_practice.gmejia.dto.response.CatalogResponse;
import finances_practice.gmejia.dto.response.CountriesResponse;
import finances_practice.gmejia.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CatalogMapper {
    List<CountriesResponse> toCountriesList(List<CountriesEntity> entities);
    List<CatalogResponse> toGendersList(List<GenderEntity> entities);
    List<CatalogResponse> toRolesList(List<RolesEntity> entities);
    List<CatalogResponse> toPaymentMethodsList(List<PaymentMethodsEntity> entities);
    List<CatalogResponse> toCategoriesList(List<CategoriesEntity> entities);
    List<CatalogResponse> toTransactionTypesList(List<TransactionTypesEntity> entities);
}
