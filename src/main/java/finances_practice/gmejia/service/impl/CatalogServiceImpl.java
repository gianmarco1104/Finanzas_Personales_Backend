package finances_practice.gmejia.service.impl;

import finances_practice.gmejia.dto.response.CountriesResponse;
import finances_practice.gmejia.dto.response.CatalogResponse;
import finances_practice.gmejia.entity.*;
import finances_practice.gmejia.repository.*;
import finances_practice.gmejia.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {
    private final CountriesRepository countriesRepository;
    private final GenderRepository genderRepository;
    private final RolesRepository rolesRepository;
    private final PaymentMethodsRepository paymentMethodsRepository;
    private final CategoriesRepository categoriesRepository;
    private final TransactionTypesRepository transactionTypesRepository;

    @Override
    public List<CountriesResponse> getCountries(){
        List<CountriesEntity> entities = countriesRepository.findAll();

        //El mapeo se usa para pasar datos internos (Base de datos) a datos externos (Responses/DTOs)
        return entities.stream()
                .map(country -> CountriesResponse
                        .builder()
                        .id(country.getId())
                        .name(country.getName())
                        .phoneCode(country.getPhoneCode())
                        .isoCode(country.getIsoCode())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public List<CatalogResponse> getGenders(){
        List<GenderEntity> entities = genderRepository.findAll();

        return entities.stream().map(
                gender -> CatalogResponse
                        .builder()
                        .id(gender.getId())
                        .name(gender.getName())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public List<CatalogResponse> getRoles(){
        List<RolesEntity> entities = rolesRepository.findAll();

        return entities.stream().map(
                gender -> CatalogResponse
                        .builder()
                        .id(gender.getId())
                        .name(gender.getName())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public List<CatalogResponse> getPaymentMethods(){
        List<PaymentMethodsEntity> entities = paymentMethodsRepository.findAll();

        return entities.stream().map(
                gender -> CatalogResponse
                        .builder()
                        .id(gender.getId())
                        .name(gender.getName())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public List<CatalogResponse> getCategories(){
        List<CategoriesEntity> entities = categoriesRepository.findAll();

        return entities.stream().map(
                gender -> CatalogResponse
                        .builder()
                        .id(gender.getId())
                        .name(gender.getName())
                        .build()).collect(Collectors.toList());
    }

    @Override
    public List<CatalogResponse> getTransactionTypes(){
        List<TransactionTypesEntity> entities = transactionTypesRepository.findAll();

        return entities.stream().map(
                gender -> CatalogResponse
                        .builder()
                        .id(gender.getId())
                        .name(gender.getName())
                        .build()).collect(Collectors.toList());
    }
}
