package org.walletAPI.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "RECORD")
@Data
@RequiredArgsConstructor
public class Record {
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "AMOUNT")
    private Double amount;
}
