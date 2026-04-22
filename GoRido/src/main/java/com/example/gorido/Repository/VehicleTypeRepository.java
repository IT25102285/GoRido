package com.example.gorido.Repository;
import com.example.gorido.Model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
}
