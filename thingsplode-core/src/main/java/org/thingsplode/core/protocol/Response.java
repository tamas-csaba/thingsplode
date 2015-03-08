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
 * @param <T>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ARsp")
public class Response<T extends AbstractCommandRequest> extends AbstractCommandRequest<T> {

    @XmlElement(required = true, name = "CorrId")
    private String responseCorrelationID;
    @XmlElement(required = true, name = "ExStat")
    private ExecutionStatus requestStatus;
    @XmlElement(name = "RspCde")
    private ResponseCode responseCode;
    @XmlElement(name = "ResMsg")
    private String resultMessage;

    public Response() {
        super();
    }

    public Response(ExecutionStatus requestStatus, ResponseCode responseCode) {
        super();
        this.requestStatus = requestStatus;
        this.responseCode = responseCode;
    }

    public Response(String responseCorrelationID, ExecutionStatus requestStatus, ResponseCode responseCode) {
        this(requestStatus, responseCode);
        this.responseCorrelationID = responseCorrelationID;
    }

    public Response(String responseCorrelationID, ExecutionStatus requestStatus, ResponseCode responseCode, String resultMessage) {
        this(responseCorrelationID, requestStatus, responseCode);
        this.resultMessage = resultMessage;
    }

    public boolean isAcknowledged() {
        return requestStatus.isAcknowledged();
    }

    public T putResponseCorrelationId(String corrId) {
        this.setResponseCorrelationID(corrId);
        return (T) this;
    }

    public T putRequestStatus(ExecutionStatus status) {
        this.setRequestStatus(status);
        return (T) this;
    }

    public T putResponseCode(ResponseCode rspCode) {
        this.setResponseCode(rspCode);
        return (T) this;
    }

    public boolean isErrorType() {
        return this instanceof ErrorMessage;
    }

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

    @Override
    public String toString() {
        return "Response{ " + super.toString() + " responseCorrelationID=" + responseCorrelationID + ", requestStatus=" + requestStatus + ", responseCode=" + responseCode + ", resultMessage=" + resultMessage + '}';
    }

}
