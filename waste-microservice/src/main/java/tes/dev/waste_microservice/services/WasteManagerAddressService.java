package tes.dev.waste_microservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tes.dev.waste_microservice.domain.model.WasteManagerAddressEntity;
import tes.dev.waste_microservice.domain.repository.WasteManagerAddressRepository;

@Service
public class WasteManagerAddressService {
    @Autowired
    WasteManagerAddressRepository wasteManagerAddressRepository;

    public WasteManagerAddressEntity create(String address) {
        return wasteManagerAddressRepository.save(new WasteManagerAddressEntity(address, true));
    }

    public WasteManagerAddressEntity update(Long id, String address) {
        WasteManagerAddressEntity updateWasteMangAdd = this.find(id);
        updateWasteMangAdd.setDireccion(address);
        return wasteManagerAddressRepository.save(updateWasteMangAdd);
    }

    public void changeStatus(Long id, Boolean status) {
        WasteManagerAddressEntity updateWasteMangAdd = this.find(id);
        updateWasteMangAdd.setIsEnabled(status);
        wasteManagerAddressRepository.save(updateWasteMangAdd);
    }

    public void delete(Long id) {
        wasteManagerAddressRepository.deleteById(id);
    }

    public WasteManagerAddressEntity find(Long id) {
        return wasteManagerAddressRepository.findById(id).orElseThrow();
    }

}
