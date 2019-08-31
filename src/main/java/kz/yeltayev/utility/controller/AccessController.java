package kz.yeltayev.utility.controller;

import kz.yeltayev.utility.dto.AccessDto;
import kz.yeltayev.utility.entity.Access;
import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.services.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AccessController {
    private AccessService accessService;

    @Autowired
    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @GetMapping("/accesses")
    public List<AccessDto> fetchAccesses() {
        return accessService.fetchAccesses();
    }

    @GetMapping("/accesses/{id}")
    public ResponseEntity<AccessDto> fetchAccessById(@PathVariable(value = "id") Long accessId) throws ResourceNotFoundException {
        AccessDto accessDto = accessService.fetchAccessById(accessId);
        return ResponseEntity.ok().body(accessDto);
    }

    @PostMapping("/accesses")
    public void addAccesses(@Valid @RequestBody List<Access> accesses) {
        accessService.addAccesses(accesses);
    }

    @PutMapping("/accesses/{id}")
    public ResponseEntity<AccessDto> updateAccess(@PathVariable(value = "id") Long accessId,
                                                    @Valid @RequestBody Access accessDetails) throws ResourceNotFoundException {
        AccessDto updatedAccess = accessService.updateAccess(accessId, accessDetails);
        return ResponseEntity.ok(updatedAccess);
    }

    @DeleteMapping("/accesses/{id}")
    public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long accessId)
            throws ResourceNotFoundException {
        return accessService.deleteAccess(accessId);
    }
}
