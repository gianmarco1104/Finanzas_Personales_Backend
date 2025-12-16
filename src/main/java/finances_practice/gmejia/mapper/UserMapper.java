package finances_practice.gmejia.mapper;

import finances_practice.gmejia.dto.request.RegisterRequest;
import finances_practice.gmejia.dto.response.CatalogResponse;
import finances_practice.gmejia.dto.response.CountriesResponse;
import finances_practice.gmejia.dto.response.UserResponse;
import finances_practice.gmejia.entity.CountriesEntity;
import finances_practice.gmejia.entity.GenderEntity;
import finances_practice.gmejia.entity.RolesEntity;
import finances_practice.gmejia.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;


@Mapper(componentModel = "spring", imports = {LocalDateTime.class}, builder = @org.mapstruct.Builder(disableBuilder = true))
public interface UserMapper {

    UserResponse toResponse(UserEntity user);
    CatalogResponse toCatalog(GenderEntity gender);
    CatalogResponse toCatalog(RolesEntity roles);
    CountriesResponse toCountry(CountriesEntity countries);

    @Mapping(target = "country.id", source = "request.countryId")
    @Mapping(target = "gender.id", source = "request.genderId")
    @Mapping(target = "role.id", constant = "2")
    @Mapping(target = "createdBy", constant = "2")
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(RegisterRequest request, String encodedPassword);
}
