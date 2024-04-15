package tes.dev.waste_microservice.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import tes.dev.waste_microservice.domain.model.WasteCenterAuthorizationEntity;
import tes.dev.waste_microservice.domain.model.WasteManagerEntity;
import tes.dev.waste_microservice.dto.WasteCenterAuthorizationDtoRequest;
import tes.dev.waste_microservice.dto.WasteCenterAuthorizationDtoResponse;
import tes.dev.waste_microservice.dto.WasteManagerDtoRequest;
import tes.dev.waste_microservice.dto.WasteManagerDtoResponse;

import java.util.Optional;

@Mapper
public interface WasteMapper {
    WasteMapper INSTANCE = Mappers.getMapper(WasteMapper.class);

    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "active", target = "isEnabled", defaultValue = "true")
    @Mapping(source = "address", target = "wasteManagerAddressEntity.direccion")
    @Mapping(source = "listWasteCenterAuthorization", target = "listOfWasteCenterAuthorization")
    WasteManagerEntity wasteManagerDtoToWasteManagerEntity(WasteManagerDtoRequest wasteDto);
   @Mapping(source = "wasteManagerAddressEntity.id", target = "idAddress")
   @Mapping(source = "nombre", target = "name")
   @Mapping(source = "isEnabled", target = "active")
   @Mapping(source = "wasteManagerAddressEntity.direccion", target = "address")
   @Mapping(source = "listOfWasteCenterAuthorization", target = "listWasteCenterAuthorizationResponse")
   WasteManagerDtoResponse wasteManagerEntityToWasteManagerDtoResponse(WasteManagerEntity wasteEntity);

    @Mapping(source = "number", target = "authorizationNumber")
    WasteCenterAuthorizationEntity wasteCenterAuthDtoRequestToWasteCenterAuthEntity(WasteCenterAuthorizationDtoRequest wasteCenterAuthorizationDtoRequest);
    @Mapping(source = "authorizationNumber", target = "number")
    WasteCenterAuthorizationDtoResponse wasteCenterAuthEntityToWasteCenterAuthDtoResponse(WasteCenterAuthorizationEntity wasteCenterAuthorizationEntity);
    @AfterMapping
    default void setWasteManagerEntity(@MappingTarget WasteManagerEntity wasteManagerEntity) {

        Optional.ofNullable(wasteManagerEntity.getWasteManagerAddressEntity())
                .ifPresent(it -> it.setWestManagerEntity(wasteManagerEntity));

        Optional.ofNullable(wasteManagerEntity.getListOfWasteCenterAuthorization())
                .ifPresent(it -> it.forEach(item -> item.setWestManagerEntity(wasteManagerEntity)));
    }

    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "listWasteCenterAuthorization", target = "listOfWasteCenterAuthorization")
    void updateWasteManagerEntityFromWasteManagerDto(WasteManagerDtoRequest dto, @MappingTarget WasteManagerEntity wasteEntity);


}
