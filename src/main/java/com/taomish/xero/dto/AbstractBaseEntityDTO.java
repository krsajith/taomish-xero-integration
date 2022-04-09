package com.taomish.xero.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
public abstract class AbstractBaseEntityDTO implements Serializable {
    private UUID uuid;
    private String createdBy;
    private String updatedBy;
    private Date createdTimestamp;
    private Date updatedTimestamp;
    private String tenantId;
    private String token;
    private String code;
}
