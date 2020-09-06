package com.ahmetgeze.flightticket.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {


    @Column(name = "create_date")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate = new Date();


    @Column(name = "update_date")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;


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
