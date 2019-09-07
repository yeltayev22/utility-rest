package kz.yeltayev.utility.controller;

import kz.yeltayev.utility.model.dto.StreetDto;
import kz.yeltayev.utility.model.entity.Street;
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
}
