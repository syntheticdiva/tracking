package com.example.tracking.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class MailMovementDTO {
    private Long id;
    private Long mailItemId;
    private Long postOfficeId;
    private Date movementDate;
    private String action;

    public MailMovementDTO() {
    }

    public MailMovementDTO(Long id, Long mailItemId, Long postOfficeId, Date movementDate, String action) {
        this.id = id;
        this.mailItemId = mailItemId;
        this.postOfficeId = postOfficeId;
        this.movementDate = movementDate;
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMailItemId() {
        return mailItemId;
    }

    public void setMailItemId(Long mailItemId) {
        this.mailItemId = mailItemId;
    }

    public Long getPostOfficeId() {
        return postOfficeId;
    }

    public void setPostOfficeId(Long postOfficeId) {
        this.postOfficeId = postOfficeId;
    }

    public Date getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Date movementDate) {
        this.movementDate = movementDate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}