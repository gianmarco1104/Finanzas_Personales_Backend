package finances_practice.gmejia.service.impl;

import finances_practice.gmejia.dto.response.CatalogResponse;
import finances_practice.gmejia.dto.response.CountriesResponse;
import finances_practice.gmejia.mapper.CatalogMapper;
import finances_practice.gmejia.repository.*;
import finances_practice.gmejia.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatalogServiceImpl implements CatalogService {
    private final CountriesRepository countriesRepository;
    private final GenderRepository genderRepository;
    private final RolesRepository rolesRepository;
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final CategoriesRepository categoriesRepository;
    private final TransactionTypesRepository transactionTypesRepository;
    private final CatalogMapper catalogMapper;

    @Override
    public List<CountriesResponse> getCountries(){return catalogMapper.toCountriesList(countriesRepository.findAll());}

    @Override
    public List<CatalogResponse> getGenders(){return catalogMapper.toGendersList(genderRepository.findAll());}

    @Override
    public List<CatalogResponse> getRoles(){return catalogMapper.toRolesList(rolesRepository.findAll());}

    @Override
    public List<CatalogResponse> getPaymentMethods(){return catalogMapper.toPaymentMethodsList(paymentMethodsRepository.findAll());}

    @Override
    public List<CatalogResponse> getCategories(){return catalogMapper.toCategoriesList(categoriesRepository.findAll());}

    @Override
    public List<CatalogResponse> getTransactionTypes(){return catalogMapper.toTransactionTypesList(transactionTypesRepository.findAll());}
}
