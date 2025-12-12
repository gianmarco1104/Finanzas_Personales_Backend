package finances_practice.gmejia.service;

import finances_practice.gmejia.dto.response.CountriesResponse;
import finances_practice.gmejia.dto.response.CatalogResponse;
import finances_practice.gmejia.entity.RolesEntity;

import java.util.List;

public interface CatalogService {
    List<CountriesResponse> getCountries();
    List<CatalogResponse> getGenders();
    List<CatalogResponse> getRoles();
    List<CatalogResponse> getPaymentMethods();
    List<CatalogResponse> getCategories();
    List<CatalogResponse> getTransactionTypes();
}
