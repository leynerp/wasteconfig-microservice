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
public class WasteManagerDtoResponse {
    Long id;
    String name;
    String nif;
    Boolean active;
    Long idAddress;
    String address;
    List<WasteCenterAuthorizationDtoResponse> listWasteCenterAuthorizationResponse;

}
