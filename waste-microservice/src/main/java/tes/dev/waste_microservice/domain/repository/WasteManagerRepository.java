package tes.dev.waste_microservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tes.dev.waste_microservice.domain.model.WasteManagerEntity;

@Repository
public interface WasteManagerRepository extends JpaRepository<WasteManagerEntity, Long> {


}
