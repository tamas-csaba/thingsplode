/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author tamas.csaba@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ARsp")
public abstract class AbstractResponse extends AbstractCmdReq {

    @XmlElement(required = true, name = "CorrId")
    private String responseCorrelationID;

    @XmlElement(required = true, name = "ExStat")
    private ExecutionStatus requestStatus;
    @XmlElement(name = "RspCde")
    private ResponseCode responseCode;
    @XmlElement(name = "ResMsg")
    private String resultMessage;

    /**
     * @return the responseCorrelationID
     */
    public String getResponseCorrelationID() {
        return responseCorrelationID;
    }

    /**
     * @param responseCorrelationID the responseCorrelationID to set
     */
    public void setResponseCorrelationID(String responseCorrelationID) {
        this.responseCorrelationID = responseCorrelationID;
    }

    /**
     * @return the requestStatus
     */
    public ExecutionStatus getRequestStatus() {
        return requestStatus;
    }

    /**
     * @param requestStatus the requestStatus to set
     */
    public void setRequestStatus(ExecutionStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    /**
     * @return the responseCode
     */
    public ResponseCode getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return the resultMessage
     */
    public String getResultMessage() {
        return resultMessage;
    }

    /**
     * @param resultMessage the resultMessage to set
     */
    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

}
