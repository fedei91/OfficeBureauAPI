package com.example.officebureauapi.services;

import com.example.officebureauapi.dto.DesktopDto;
import com.example.officebureauapi.entities.Desktop;
import com.example.officebureauapi.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DesktopService {

    Page<DesktopDto> findAll(Pageable pageable);
    Desktop findById(String id);
    DesktopDto findByDesktopById(String id);
    void save(DesktopDto desktopDto);
    DesktopDto update(String id, DesktopDto dto);
    void delete(String id);
    boolean checkAvailability(UUID desktopId, Reservation reservation);
}
