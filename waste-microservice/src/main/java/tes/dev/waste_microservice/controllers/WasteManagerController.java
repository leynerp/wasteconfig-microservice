package tes.dev.waste_microservice.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tes.dev.waste_microservice.dto.WasteManagerDtoRequest;
import tes.dev.waste_microservice.services.WasteManagerService;

@RestController
@RequestMapping("/api/v1/wastemanager")
@Validated
public class WasteManagerController {

    @Autowired
    WasteManagerService wasteManagerService;

    @PostMapping
    public ResponseEntity insertWasteManager(@RequestBody @Valid WasteManagerDtoRequest wasteManagerDto, BindingResult bindingResult) {
        return wasteManagerService.create(wasteManagerDto, bindingResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateWasteManager(@RequestBody @Valid WasteManagerDtoRequest wasteManagerDto, @PathVariable("id") Long id, BindingResult bindingResult) {
        return wasteManagerService.update(wasteManagerDto, id, bindingResult);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteWasteManager(@PathVariable("id") Long id) {
        return wasteManagerService.deleteWaste(id);
    }

    @PatchMapping("/enabledDisabled/{id}")
    public ResponseEntity changeActiveWasteManager(@PathVariable("id") Long id) {
        return wasteManagerService.changeEnabled(id);
    }

    @PatchMapping("/address/{id}")
    public ResponseEntity clearAddressWasteManager(@PathVariable("id") Long id) {
        return wasteManagerService.deleteAddressFromWaste(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity getWasteManagerById(@PathVariable("id") Long id) {
        return wasteManagerService.findById(id);
    }

    @GetMapping()
    public ResponseEntity getAllWasteManager(@PageableDefault(size = 5, page = 0) Pageable pageRequest) {
        return wasteManagerService.findAll(pageRequest);
    }
}
