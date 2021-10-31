package org.walletAPI.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
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

    @Column(name = "RECORD_DATE")
    private Date recordDate;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "AMOUNT")
    private Double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.EAGER)
    private TypeRecord typeRecord;
}
