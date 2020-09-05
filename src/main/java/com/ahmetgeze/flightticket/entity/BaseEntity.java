package com.ahmetgeze.flightticket.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;


    @Column(name = "create_date")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();


    @Column(name = "update_date")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
