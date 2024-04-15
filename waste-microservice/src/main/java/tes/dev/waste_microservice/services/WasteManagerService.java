package tes.dev.waste_microservice.services;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import tes.dev.waste_microservice.config.MessageResponse;
import tes.dev.waste_microservice.domain.repository.WasteManagerRepository;
import tes.dev.waste_microservice.dto.WasteManagerDtoRequest;
import tes.dev.waste_microservice.dto.WasteManagerDtoResponse;
import tes.dev.waste_microservice.exception.ResourceNotFoundException;
import tes.dev.waste_microservice.mapper.WasteMapper;

import java.util.ArrayList;
import java.util.List;


@Service
public class WasteManagerService {
    @Autowired
    WasteManagerRepository wasteManagerRepository;
    @Autowired
    WasteManagerAddressService wasteManagerAddressService;
    private WasteMapper mapperWaste = Mappers.getMapper(WasteMapper.class);

    public List<String> getErrors(List<ObjectError> errors) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : errors) {
            errorMessages.add(error.getDefaultMessage());
        }
        return errorMessages;
    }

    @Transactional
    public ResponseEntity create(WasteManagerDtoRequest wasteManagerDto, BindingResult bindingResult) {
        var searchWaste = wasteManagerRepository.findByNifEquals(wasteManagerDto.getNif());
        if (searchWaste != null) {
            bindingResult.rejectValue("nif", "", "Exist a waste witch this nif ");
        }
        if (!bindingResult.hasErrors()) {
            var wasteManagerEntity = mapperWaste.wasteManagerDtoToWasteManagerEntity(wasteManagerDto);
            wasteManagerEntity.setWasteManagerAddressEntity(wasteManagerAddressService.create(wasteManagerDto.getAddress()));
            mapperWaste.setWasteManagerEntity(wasteManagerEntity);
            wasteManagerRepository.save(wasteManagerEntity);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("200", "Waste create success"));
        } else {
            List<String> errorMessages = this.getErrors(bindingResult.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

    }

    ;

    @Transactional
    public ResponseEntity update(WasteManagerDtoRequest wasteManagerDto, Long id, BindingResult bindingResult) {

        var wasteUpdate = wasteManagerRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Waste", "id", id.toString());
                });
        var searchWaste = wasteManagerRepository.findByNifEqualsAndIdIsNot(wasteManagerDto.getNif(), wasteUpdate.getId());
        if (searchWaste != null) {
            bindingResult.rejectValue("nif", "", "Exist a waste witch this nif ");
        }
        if (!bindingResult.hasErrors()) {
            mapperWaste.updateWasteManagerEntityFromWasteManagerDto(wasteManagerDto, wasteUpdate);
            var wasteManagerAddress = wasteUpdate.getWasteManagerAddressEntity();
            if (wasteManagerAddress != null) {
                var idWasteManagerAddress = wasteUpdate.getWasteManagerAddressEntity().getId();
                var address = wasteUpdate.getWasteManagerAddressEntity().getDireccion();
                if (!address.equalsIgnoreCase(wasteManagerDto.getAddress())) {
                    var wasteManagerAddressUpdated = wasteManagerAddressService.update(idWasteManagerAddress, wasteManagerDto.getAddress());
                    wasteUpdate.setWasteManagerAddressEntity(wasteManagerAddressUpdated);
                }
            } else {
                wasteUpdate.setWasteManagerAddressEntity(wasteManagerAddressService.create(wasteManagerDto.getAddress()));
            }
            mapperWaste.setWasteManagerEntity(wasteUpdate);
            wasteManagerRepository.save(wasteUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("200", "Waste update success"));

        } else {
            List<String> errorMessages = this.getErrors(bindingResult.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
        }

    }

    public ResponseEntity findById(Long wasteManagerId) {
        var wasteFound = wasteManagerRepository.findById(wasteManagerId)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Waste", "id", wasteManagerId.toString());
                });
        WasteManagerDtoResponse wasteManagerDto = mapperWaste.wasteManagerEntityToWasteManagerDtoResponse(wasteFound);
        return ResponseEntity.status(HttpStatus.OK).body(wasteManagerDto);
    }

    public ResponseEntity findAll(Pageable pageable) {
        var pagewasteManager = wasteManagerRepository.findAll(pageable);
        Page<WasteManagerDtoResponse> wasteManagerDtoPage = pagewasteManager.map(wasteManagerEntity -> mapperWaste.wasteManagerEntityToWasteManagerDtoResponse(wasteManagerEntity));
        if (wasteManagerDtoPage.getContent().size() != 0)
            return ResponseEntity.status(HttpStatus.OK).body(wasteManagerDtoPage);
        else return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("200", "there is no data to show"));


    }

    public ResponseEntity deleteWaste(Long wasteManagerId) {
        var wasteFound = wasteManagerRepository.findById(wasteManagerId)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Waste", "id", wasteManagerId.toString());
                });
        wasteManagerRepository.deleteById(wasteManagerId);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("200", "Waste delete success"));
    }

    public ResponseEntity changeEnabled(Long wasteManagerId) {
        var wasteFound = wasteManagerRepository.findById(wasteManagerId)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Waste", "id", wasteManagerId.toString());
                });
        Boolean actualEnabledValue = wasteFound.getIsEnabled();
        wasteFound.setIsEnabled(!actualEnabledValue);
        wasteManagerRepository.save(wasteFound);
        String messsage = (actualEnabledValue) ? " Waste disabled success" : " Waste enabled success";
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("200", messsage));
    }

    public ResponseEntity deleteAddressFromWaste(Long wasteManagerId) {
        var wasteFound = wasteManagerRepository.findById(wasteManagerId)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Waste", "id", wasteManagerId.toString());
                });
        wasteFound.setWasteManagerAddressEntity(null);
        wasteManagerRepository.save(wasteFound);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("200", "The address of Waste was delete success"));
    }

}
