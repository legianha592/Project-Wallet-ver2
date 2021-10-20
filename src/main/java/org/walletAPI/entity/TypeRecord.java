package org.walletAPI.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TYPE_RECORD")
@Data
@RequiredArgsConstructor
public class TypeRecord {
    @Column(name = "ID")
    private Long id;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "TYPE_RECORD_NAME")
    private String typeRecordName;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "typeRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Record> listRecord;
}
