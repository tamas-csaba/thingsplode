/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.exceptions;

import org.thingsplode.core.protocol.ExecutionStatus;
import org.thingsplode.core.protocol.ResponseCode;

/**
 *
 * @author Csaba Tamas
 */
public class SrvExecutionException extends Exception {

    private String messageCorrelationID;
    private final ExecutionStatus executionStatus;
    private final ResponseCode responseCode;

    public SrvExecutionException(ExecutionStatus executionStatus, ResponseCode responseCode, String message) {
        super(message);
        this.executionStatus = executionStatus;
        this.responseCode = responseCode;
    }

    public SrvExecutionException(String messageCorrelationID, ExecutionStatus executionStatus, ResponseCode responseCode, String message) {
        this(executionStatus, responseCode, message);
        this.messageCorrelationID = messageCorrelationID;
    }

    public SrvExecutionException(ExecutionStatus executionStatus, ResponseCode responseCode, String message, Throwable cause) {
        super(message, cause);
        this.executionStatus = executionStatus;
        this.responseCode = responseCode;
    }

    public SrvExecutionException(String messageCorrelationID, ExecutionStatus executionStatus, ResponseCode responseCode, String message, Throwable cause) {
        this(executionStatus, responseCode, message, cause);
        this.messageCorrelationID = messageCorrelationID;
    }

    public SrvExecutionException(ExecutionStatus executionStatus, ResponseCode responseCode, Throwable cause) {
        super(cause);
        this.executionStatus = executionStatus;
        this.responseCode = responseCode;
    }

    public SrvExecutionException(ExecutionStatus executionStatus, ResponseCode responseCode) {
        super();
        this.executionStatus = executionStatus;
        this.responseCode = responseCode;
    }

    /**
     * @return the executionStatus
     */
    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    /**
     * @return the responseCode
     */
    public ResponseCode getResponseCode() {
        return responseCode;
    }

    /**
     * @return the messageCorrelationID
     */
    public String getMessageCorrelationID() {
        return messageCorrelationID;
    }

}
