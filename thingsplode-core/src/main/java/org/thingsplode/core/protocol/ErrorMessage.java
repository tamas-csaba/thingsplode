/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public class ErrorMessage extends Response {

    private Throwable th;

    public ErrorMessage(Throwable th) {
        this.th = th;
    }

    public ErrorMessage(ExecutionStatus requestStatus, ResponseCode responseCode, Throwable th) {
        super(requestStatus, responseCode);
        this.th = th;
    }

    public ErrorMessage(String responseCorrelationID, ExecutionStatus requestStatus, ResponseCode responseCode, Throwable th) {
        super(responseCorrelationID, requestStatus, responseCode);
        this.th = th;
    }

    public ErrorMessage(String responseCorrelationID, ExecutionStatus requestStatus, ResponseCode responseCode, String resultMessage, Throwable th) {
        super(responseCorrelationID, requestStatus, responseCode, resultMessage);
        this.th = th;
    }

    public Throwable getTh() {
        return th;
    }

    public void setTh(Throwable th) {
        this.th = th;
    }

    @Override
    public String toString() {
        return "ErrorMessage{ " + super.toString() + " th=" + th + '}';
    }
}
