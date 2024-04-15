package tes.dev.waste_microservice.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class WasteManagerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String nif;

    @OneToOne(mappedBy = "westManagerEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private WasteManagerAddressEntity wasteManagerAddressEntity;

    @OneToMany(mappedBy = "westManagerEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WasteCenterAuthorizationEntity> listOfWasteCenterAuthorization = new ArrayList<>();
    @Column(columnDefinition = "boolean default true")
    private Boolean isEnabled;
    @Version
    private Long version = 0L;
    private Date createdDate;
    private Date lastModifiedDate;

    @PrePersist
    public void setCreateDate() {
        this.createdDate = new Date();
    }

    @PreUpdate
    public void updLastModifiedDate() {
        this.lastModifiedDate = new Date();
    }
}
