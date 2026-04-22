package com.example.gorido.Model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String model;
    private String number;
    private String insurance_number;
    private Date insurance_exp_date;
    private String vehicle_photo;
    private String insurance_photo;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driverId;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status statusId;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id")
    private VehicleType vehicleType;

    @ManyToOne
    @JoinColumn(name = "vehicle_color_id")
    private VehicleColor vehicleColor;

    public Vehicle () {}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setInsurance_number(String insurance_number) {
        this.insurance_number = insurance_number;
    }

    public String getInsurance_number() {
        return insurance_number;
    }

    public void setInsurance_exp_date(Date insurance_exp_date) {
        this.insurance_exp_date = insurance_exp_date;
    }

    public Date getInsurance_exp_date() {
        return insurance_exp_date;
    }

    public void setVehicle_photo(String vehicle_photo) {
        this.vehicle_photo = vehicle_photo;
    }

    public String getVehicle_photo() {
        return vehicle_photo;
    }

    public void setInsurance_photo(String insurance_photo) {
        this.insurance_photo = insurance_photo;
    }

    public String getInsurance_photo() {
        return insurance_photo;
    }

    public void setDriverId(Driver driverId) {
        this.driverId = driverId;
    }

    public Driver getDriverId() {
        return driverId;
    }

    public void setStatusId(Status statusId) {
        this.statusId = statusId;
    }

    public Status getStatusId() {
        return statusId;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleColor(VehicleColor vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public VehicleColor getVehicleColor() {
        return vehicleColor;
    }
}
