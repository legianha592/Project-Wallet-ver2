package org.walletAPI.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USER")
@Data
@RequiredArgsConstructor
public class User {
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wallet> listWallet;

    public void addWallet(Wallet wallet) {
        listWallet.add(wallet);
    }
}
