package com.example.gorido.Model;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    private String nic_number;
    private String license_number;
    private Date license_exp_date;
    private int experience;
    private String license_image;
    private String nic_image;
    private Date registered_date;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status statusId;

    @ManyToOne
    @JoinColumn(name = "active_id")
    private Active activeId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
