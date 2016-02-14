/*
 * Copyright 2016 tamas.csaba@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsplode.connect.core;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author tamas.csaba@gmail.com
 */
public abstract class AbstractMessage {

    private HashMap<String, Serializable> header = new HashMap<>();
    private Serializable body;

    public void setHeaderValue(String key, Serializable value) {
        header.put(key, value);
    }

    public Serializable getHeaderValue(String key) {
        return header.get(key);
    }

    public HashMap<String, Serializable> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, Serializable> header) {
        this.header = header;
    }

    public Serializable getBody() {
        return body;
    }

    public void setBody(Serializable body) {
        this.body = body;
    }

}
