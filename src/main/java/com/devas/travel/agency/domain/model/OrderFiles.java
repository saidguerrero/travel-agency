package com.devas.travel.agency.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class OrderFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_files_id")
    private Long id;

    @Column(name = "type_file_id")
    private int typeFileId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "order_id", nullable = false)
    private Orders ordersByOrderId;

}
