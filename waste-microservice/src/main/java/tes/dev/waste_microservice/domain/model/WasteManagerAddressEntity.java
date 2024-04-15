package tes.dev.waste_microservice.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WasteManagerAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String direccion;
    @Column(columnDefinition = "boolean default true")
    private Boolean isEnabled;
    @Version
    private Long version = 0L;
    private Date createdDate;
    private Date lastModifiedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_waste_manager", referencedColumnName = "id")
    private WasteManagerEntity westManagerEntity;

    @PrePersist
    public void setCreateDate() {
        this.createdDate = new Date();
    }

    @PreUpdate
    public void updLastModifiedDate() {
        this.lastModifiedDate = new Date();
    }

    public WasteManagerAddressEntity(String direcc, Boolean status) {
        this.direccion = direcc;
        this.isEnabled = status;

    }
}
