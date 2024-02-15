package com.example.officebureauapi.controllers;

import com.example.officebureauapi.dto.DesktopDto;
import com.example.officebureauapi.services.DesktopService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/desktops")
@AllArgsConstructor
public class DesktopController {

    private final DesktopService desktopService;

    @GetMapping
    @PreAuthorize("hasAuthority('desktop:read')")
    public ResponseEntity<Page<DesktopDto>> findAllDesktops(
            Pageable pageable
    ) {
        return ResponseEntity.ok(desktopService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('desktop:read')")
    public ResponseEntity<DesktopDto> getDesktopById(
            @PathVariable String id
    ) {
        DesktopDto desktop = desktopService.findByDesktopById(id);
        return ResponseEntity.ok(desktop);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('desktop:create')")
    public ResponseEntity<?> createDesktop(
            @RequestBody @Valid DesktopDto desktopDto
    ) {
        desktopService.save(desktopDto);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('desktop::update')")
    public ResponseEntity<?> updateDesktop(
            @PathVariable String id,
            @RequestBody @Valid DesktopDto updatedDesktop
    ) {
        desktopService.update(id, updatedDesktop);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('desktop::delete')")
    public ResponseEntity<?> deleteDesktop(
            @PathVariable String id
    ) {
        desktopService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
