/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.protocol.response;

import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public class BootNotificationResponse {

    private Calendar currentTime;
    private Locale locale;
    private Long heartbeatInterval;
}
