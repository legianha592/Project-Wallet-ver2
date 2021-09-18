package org.walletAPI.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

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
}
