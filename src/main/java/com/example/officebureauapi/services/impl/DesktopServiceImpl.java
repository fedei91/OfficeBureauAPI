package com.example.officebureauapi.services.impl;

import com.example.officebureauapi.dto.DesktopDto;
import com.example.officebureauapi.entities.Department;
import com.example.officebureauapi.entities.Desktop;
import com.example.officebureauapi.entities.Reservation;
import com.example.officebureauapi.exceptions.DesktopNotFoundException;
import com.example.officebureauapi.exceptions.InvalidIdException;
import com.example.officebureauapi.mappers.DesktopMapper;
import com.example.officebureauapi.repositories.DepartmentRepository;
import com.example.officebureauapi.repositories.DesktopRepository;
import com.example.officebureauapi.repositories.ReservationRepository;
import com.example.officebureauapi.repositories.specifications.ReservationSpecifications;
import com.example.officebureauapi.services.DesktopService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class DesktopServiceImpl implements DesktopService {
    private static final String NOT_FOUND = "Desktop.NotFound";
    private static final String DESKTOP_ID_NOT_FOUND = "Desktop.Id.NotFound";
    private static final String DESKTOP_DEPARTMENT_ID_NOT_FOUND = "Desktop.DepartmentId.NotFound";
    private static final String DESKTOP_BOOKED_CHAIRS_EXCEEDS_CAPACITY = "Desktop.BookedChairs.Capacity.Exceeds";
    private static final String DESKTOP_EXCEEDS_DEPARTMENT_CAPACITY = "Desktop.Capacity.Exceeds.Department";
    private static final String INVALID_DESKTOP_ID_FORMAT = "Desktop.Id.InvalidFormat";
    private static final String DESKTOP_EXISTS = "Desktop.Exists";
    private static final String DESKTOP_NOT_AVAILABLE = "Desktop.TimeSlot.NotAvailable";

    private final DepartmentRepository departmentRepository;
    private final ReservationRepository reservationRepository;
    private final DesktopRepository desktopRepository;
    private final DesktopMapper desktopMapper;

    @Override
    public Page<DesktopDto> findAll(Pageable pageable) {
        return desktopRepository.findByIsDeletedIsFalse(pageable)
                .map(desktop -> desktopMapper.toDto(desktop, DesktopDto.builder().build()));
    }

    @Override
    public Desktop findById(String desktopId) {
       UUID id = UUID.fromString(desktopId);

       return desktopRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException(String.format(NOT_FOUND, desktopId)));
    }

    @Override
    public DesktopDto findByDesktopById(String id) {
        UUID desktopId;
        try {
            desktopId = UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new InvalidIdException(INVALID_DESKTOP_ID_FORMAT, ex);
        }
        return desktopRepository.findById(desktopId)
                .map(desktop -> desktopMapper.toDto(desktop, DesktopDto.builder().build()))
                .orElseThrow(() -> new DesktopNotFoundException(String.format(NOT_FOUND, id)));
    }

    @Override
    public void save(DesktopDto desktopDto) {
        Department department = departmentRepository.findById(desktopDto.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(DESKTOP_DEPARTMENT_ID_NOT_FOUND, desktopDto.getDepartmentId())));

        desktopDto.setTotalChairs();

        int remainingDesktopCapacity = department.decreaseAvailability();
        int remainingChairsCapacity = department.calculateMaxChairs();

        if (remainingDesktopCapacity <= 0) {
            throw new IllegalArgumentException(DESKTOP_EXCEEDS_DEPARTMENT_CAPACITY);
        }

        if (remainingChairsCapacity < desktopDto.getTotalChairs()) {
            throw new IllegalArgumentException(DESKTOP_BOOKED_CHAIRS_EXCEEDS_CAPACITY);
        }

        Desktop desktop = desktopMapper.toEntity(desktopDto, Desktop.builder().desktopType(desktopDto.getDesktopType()).build());
        boolean desktopsExists = desktopRepository.existsById(desktop.getId());

        if (desktopsExists) {
            throw new EntityExistsException(DESKTOP_EXISTS);
        }

        desktop.setDepartment(department);
        desktopRepository.save(desktop);

    }

    @Override
    public DesktopDto update(String desktopId, DesktopDto updatedDesktopDto) {
        Desktop existingDesktop = findById(desktopId);
        existingDesktop = desktopMapper.toEntity(updatedDesktopDto, existingDesktop);
        desktopRepository.save(existingDesktop);

        return desktopMapper.toDto(existingDesktop, updatedDesktopDto);
    }

    @Override
    public void delete(String desktopId) {
        Desktop existingDesktop = findById(desktopId);
        existingDesktop.setDeleted(true);

        desktopRepository.save(existingDesktop);
    }

    @Override
    public boolean checkAvailability(UUID desktopId, Reservation reservation) {
        Desktop desktop = desktopRepository.findById(desktopId)
                .orElseThrow(() -> new DesktopNotFoundException(String.format(NOT_FOUND, desktopId)));

        int totalChairs = desktop.getTotalChairs();

        LocalDateTime startTime = reservation.getStartTime();
        LocalDateTime endTime = reservation.getEndTime();

        List<Reservation> overlappingReservations = reservationRepository.findAll(
                ReservationSpecifications.conflictingReservation(desktopId, startTime, endTime));

        int totalBookedChairsForSlot = overlappingReservations.stream()
                .mapToInt(Reservation::getBookedChairs)
                .sum();

        int availableChairs = totalChairs - totalBookedChairsForSlot;

        return reservation.getBookedChairs() <= availableChairs;
    }


}
