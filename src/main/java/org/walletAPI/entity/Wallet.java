package org.walletAPI.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "WALLET")
@Data
@RequiredArgsConstructor
public class Wallet {
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "WALLET_NAME")
    private String walletName;

    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> listRecord;
}
