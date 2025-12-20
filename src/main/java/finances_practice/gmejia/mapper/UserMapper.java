package finances_practice.gmejia.mapper;

import finances_practice.gmejia.dto.request.RegisterRequest;
import finances_practice.gmejia.dto.request.UpdateRequest;
import finances_practice.gmejia.dto.response.CatalogResponse;
import finances_practice.gmejia.dto.response.CountriesResponse;
import finances_practice.gmejia.dto.response.ListUsersResponse;
import finances_practice.gmejia.dto.response.UserResponse;
import finances_practice.gmejia.entity.CountriesEntity;
import finances_practice.gmejia.entity.GenderEntity;
import finances_practice.gmejia.entity.RolesEntity;
import finances_practice.gmejia.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.List;


@Mapper(componentModel = "spring", imports = {LocalDateTime.class}, builder = @org.mapstruct.Builder(disableBuilder = true))
public interface UserMapper {

    UserResponse toResponseUser(UserEntity user);
    CatalogResponse toCatalogGender(GenderEntity gender);
    CatalogResponse toCatalogRoles(RolesEntity roles);
    CountriesResponse toCountry(CountriesEntity countries);

    //Creacion de usuario
    @Mapping(target = "country.id", source = "request.countryId")
    @Mapping(target = "gender.id", source = "request.genderId")
    @Mapping(target = "role.id", constant = "2")
    @Mapping(target = "createdBy", expression = "java(2L)")
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(RegisterRequest request, String encodedPassword);

    //Actualizacion de Usuario
    @Mapping(target = "country.id", source = "request.countryId")
    @Mapping(target = "gender.id", source = "request.genderId")
    @Mapping(target = "role.id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedBy", expression = "java(entity.getId())")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateUser(UpdateRequest request, @MappingTarget UserEntity entity);

    //Admin
    @Mapping(target = "fullName", expression = "java(entity.getFirstName() + ' ' + entity.getLastName())")
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "email", source = "entity.email")
    @Mapping(target = "phone", source = "entity.phone")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    ListUsersResponse toUserResponse(UserEntity entity);
    List<ListUsersResponse> toUserResponseList(List<UserEntity> users);
}
