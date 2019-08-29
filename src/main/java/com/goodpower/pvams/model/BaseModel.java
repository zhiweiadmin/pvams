package com.goodpower.pvams.model;

import java.io.Serializable;
import java.util.Date;


public class BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 修改人
	 */
	private String updater;

	/**
     * 创建时间
     */
    private Date createDttm = new Date();

    /**
     * 更新时间
     */
    private Date updateDttm = new Date();

    /**
     * 创建人GET
     * @return
     */
    public String getCreator() {
		return creator;
	}

    /**
     * 创建人SET
     * @param creator
     */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * 更新人GET
	 * @return
	 */
	public String getUpdater() {
		return updater;
	}

	/**
	 * 更新人SET
	 * @param updater
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
	}

	/**
	 * 创建时间GET
	 * @return
	 */
	public Date getCreateDttm() {
    	if(createDttm!=null){
			return (Date) createDttm.clone();
		}else{
			return null;
		}
    }

	/**
	 * 创建时间SET
	 * @param createDttm
	 */
    public void setCreateDttm(Date createDttm) {
        if (null != createDttm) {
            this.createDttm = (Date) createDttm.clone();
        } else {
            this.createDttm = null;
        }
    }

    /**
     * 更新时间GET
     * @return
     */
    public Date getUpdateDttm() {
    	if(updateDttm!=null){
			return (Date) updateDttm.clone();
		}else{
			return null;
		}
    }

    /**
     * 更新时间SET
     * @param updateDttm
     */
    public void setUpdateDttm(Date updateDttm) {
        if (null != updateDttm) {
            this.updateDttm = (Date) updateDttm.clone();
        } else {
            this.updateDttm = null;
        }
    }

}
