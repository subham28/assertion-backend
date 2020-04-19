package com.assertion.assignment.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WEBSITE_DATA")
public class WebsitePO
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME",nullable = false)
    private String websiteName;

    @Column(name = "PASSWORD",nullable = false)
    private String password;

    @Column(name = "CREATED_TIME",nullable = false)
    private Date createdTime;

    @Column(name = "MODIFIED_TIME")
    private Date modifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String toString() {
        return "WebsitePO{" +
                "id=" + id +
                ", websiteName='" + websiteName + '\'' +
                ", password='" + password + '\'' +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
