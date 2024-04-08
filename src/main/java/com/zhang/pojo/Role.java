package com.zhang.pojo;

import javax.xml.crypto.Data;

public class Role {
    private Integer id;//id
    private String roleCode;
    private String roleName;
    private Integer createdBy;//创建者
    private Data creationDate;//创建时间
    private Integer modifyBy;//更新者
    private Data modifyDate;//更新时间
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Data getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Data creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Data getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Data modifyDate) {
        this.modifyDate = modifyDate;
    }


}
