package tes.dev.waste_microservice.domain.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WasteCenterAuthorizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String authorizationNumber;

    @ManyToOne
    @JoinColumn(name = "id_waste_manager", nullable = false, referencedColumnName = "id")
    private WasteManagerEntity westManagerEntity;
}
