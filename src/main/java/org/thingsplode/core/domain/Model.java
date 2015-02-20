/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingsplode.core.domain;

import javax.persistence.Embeddable;

/**
 *
 * @author tam
 */
@Embeddable
public class Model {

    private String manufacturer;
    private String type;
    private String serialNumber;
    private String partNumber;
    private String version;
}
