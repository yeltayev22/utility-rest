package kz.yeltayev.utility.controller;

import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.model.dto.AccessDto;
import kz.yeltayev.utility.model.request.AccessRequest;
import kz.yeltayev.utility.services.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/accesses")
    public void addAccesses(@Valid @RequestBody List<AccessRequest> accesses) throws ResourceNotFoundException {
        accessService.addAccesses(accesses);
    }

    @DeleteMapping("/accesses/{id}")
    public Map<String, Boolean> deleteAccount(@PathVariable(value = "id") Long accessId)
            throws ResourceNotFoundException {
        return accessService.deleteAccess(accessId);
    }
}
