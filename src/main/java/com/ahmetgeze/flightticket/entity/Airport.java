package com.ahmetgeze.flightticket.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "airport")
public class Airport {

    @Id
    @Type(type="uuid-char")
    private UUID id;

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @Column(name = "create_date")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;


    @Column(name = "update_date")
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;


    public Airport() {
    }


    public Airport(String name) {
        super();
        this.id=UUID.randomUUID();
        this.name = name.isEmpty() ? null :name;
        this.createDate = new Date();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
