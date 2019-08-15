package kz.yeltayev.utility.controller;

import kz.yeltayev.utility.dto.StreetDto;
import kz.yeltayev.utility.entity.Street;
import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.services.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class StreetController {

    private StreetService streetService;

    @Autowired
    public StreetController(StreetService streetService) {
        this.streetService = streetService;
    }

    @GetMapping("/streets")
    public List<StreetDto> getAllStreets() {
        return streetService.fetchStreets();
    }

    @GetMapping("/streets/{id}")
    public ResponseEntity<StreetDto> fetchStreetById(@PathVariable(value = "id") Long streetId)
            throws ResourceNotFoundException {
        StreetDto streetDto = streetService.fetchStreetById(streetId);
        return ResponseEntity.ok().body(streetDto);
    }

    @PostMapping("/streets")
    public StreetDto createStreet(@Valid @RequestBody Street street) {
        return streetService.addStreet(street);
    }

    @PutMapping("/streets/{id}")
    public ResponseEntity<StreetDto> updateStreet(@PathVariable(value = "id") Long streetId,
                                                 @Valid @RequestBody Street streetDetails) throws ResourceNotFoundException {
        StreetDto updatedStreet = streetService.updateStreet(streetId, streetDetails);
        return ResponseEntity.ok(updatedStreet);
    }

    @DeleteMapping("/streets/{id}")
    public Map<String, Boolean> deleteStreet(@PathVariable(value = "id") Long streetId)
            throws ResourceNotFoundException {
        return streetService.deleteStreet(streetId);
    }
}
