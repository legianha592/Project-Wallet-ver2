package org.walletAPI.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

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
}
