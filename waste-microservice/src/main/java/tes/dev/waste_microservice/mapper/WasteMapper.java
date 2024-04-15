package tes.dev.waste_microservice.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import tes.dev.waste_microservice.domain.model.WasteCenterAuthorizationEntity;
import tes.dev.waste_microservice.domain.model.WasteManagerEntity;
import tes.dev.waste_microservice.dto.WasteCenterAuthorizationDto;
import tes.dev.waste_microservice.dto.WasteManagerDto;

import java.util.Optional;

@Mapper
public interface WasteMapper {
    WasteMapper INSTANCE = Mappers.getMapper(WasteMapper.class);

    @Mapping(source = "nombre", target = "name")
    @Mapping(source = "isEnabled", target = "active", defaultValue = "true")
    @Mapping(source = "wasteManagerAddressEntity.direccion", target = "address")
    @Mapping(source = "listOfWasteCenterAuthorization", target = "listWasteCenterAuthorization")
    WasteManagerDto wasteManagerEntityToWasteManagerDto(WasteManagerEntity wasteEntity);

    @InheritInverseConfiguration
    @Mapping(source = "listWasteCenterAuthorization", target = "listOfWasteCenterAuthorization")
    @Mapping(source = "active", target = "isEnabled", defaultValue = "true")
    WasteManagerEntity wasteManagerDtoToWasteManagerEntity(WasteManagerDto wasteDto);

    @Mapping(source = "authorizationNumber", target = "number")
    WasteCenterAuthorizationDto wasteCenterAuthEntityToWasteCenterAuthDto(WasteCenterAuthorizationEntity wasteCenterAuthorizationEntity);

    @InheritInverseConfiguration
    WasteCenterAuthorizationEntity wasteCenterAuthDtoToWasteCenterAuthEntity(WasteCenterAuthorizationDto wasteCenterAuthorizationDto);

    @AfterMapping
    default void setWasteManagerEntity(@MappingTarget WasteManagerEntity wasteManagerEntity) {

        Optional.ofNullable(wasteManagerEntity.getWasteManagerAddressEntity())
                .ifPresent(it -> it.setWestManagerEntity(wasteManagerEntity));

        Optional.ofNullable(wasteManagerEntity.getListOfWasteCenterAuthorization())
                .ifPresent(it -> it.forEach(item -> item.setWestManagerEntity(wasteManagerEntity)));
    }

    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "listWasteCenterAuthorization", target = "listOfWasteCenterAuthorization")
    void updateWasteManagerEntityFromWasteManagerDto(WasteManagerDto dto, @MappingTarget WasteManagerEntity wasteEntity);


}
