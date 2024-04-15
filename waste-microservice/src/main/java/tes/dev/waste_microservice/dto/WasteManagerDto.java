package tes.dev.waste_microservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WasteManagerDto {
    @NotNull
    @NotBlank
    String name;
    @NotNull
    @NotBlank
    String nif;
    Boolean active;
    @NotNull
    @NotBlank
    String address;
    @NotNull
    @NotEmpty
    List<WasteCenterAuthorizationDto> listWasteCenterAuthorization;

}
