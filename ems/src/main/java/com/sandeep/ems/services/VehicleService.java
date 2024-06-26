package com.sandeep.ems.services;

import com.sandeep.ems.dtos.VehicleDto;
import com.sandeep.ems.entities.Vehicle;
import com.sandeep.ems.exceptions.AppException;
import com.sandeep.ems.mappers.VehicleMapper;
import com.sandeep.ems.repositories.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public List<VehicleDto> allVehicles() {
        return vehicleMapper.toVehicleDtos(vehicleRepository.findAll());
    }

    public VehicleDto createVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleMapper.toVehicle(vehicleDto);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toVehicleDto(savedVehicle);
    }

    public VehicleDto updateVehicle(Long id, VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new AppException("Vehicle not found", HttpStatus.NOT_FOUND));

        vehicleMapper.updateVehicle(vehicle, vehicleMapper.toVehicle(vehicleDto));

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toVehicleDto(savedVehicle);
    }

    public VehicleDto patchVehicle(Long id, VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new AppException("Vehicle not found", HttpStatus.NOT_FOUND));

        if (vehicleDto.getBrand() != null) {
            vehicle.setBrand(vehicleDto.getBrand());
        }
        if (vehicleDto.getModel() != null) {
            vehicle.setModel(vehicleDto.getModel());
        }
        if (vehicleDto.getYear() != 0) {
            vehicle.setYear(vehicleDto.getYear());
        }
        if (vehicleDto.getColor() != null) {
            vehicle.setColor(vehicleDto.getColor());
        }

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toVehicleDto(savedVehicle);
    }

    public VehicleDto deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new AppException("Vehicle not found", HttpStatus.NOT_FOUND));
        VehicleDto vehicleDto = vehicleMapper.toVehicleDto(vehicle);

        vehicleRepository.deleteById(id);

        return vehicleDto;
    }

    public VehicleDto getVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new AppException("Vehicle not found", HttpStatus.NOT_FOUND));
        return vehicleMapper.toVehicleDto(vehicle);
    }
}
