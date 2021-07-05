/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.core.provisioning.api.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Base64;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.common.objects.Attribute;

class AttributeSerializer extends JsonSerializer<Attribute> {

    public static final String BYTE_ARRAY_PREFIX = "<binary>";

    public static final String BYTE_ARRAY_SUFFIX = "</binary>";

    @Override
    public void serialize(final Attribute source, final JsonGenerator jgen, final SerializerProvider sp)
            throws IOException {

        jgen.writeStartObject();

        jgen.writeStringField("name", source.getName());

        jgen.writeFieldName("value");
        if (source.getValue() == null) {
            jgen.writeNull();
        } else {
            jgen.writeStartArray();
            for (Object value : source.getValue()) {
                if (value == null) {
                    jgen.writeNull();
                } else if (value instanceof GuardedString) {
                    jgen.writeObject(value);
                } else if (value instanceof Integer) {
                    jgen.writeNumber((Integer) value);
                } else if (value instanceof Long) {
                    jgen.writeNumber((Long) value);
                } else if (value instanceof Double) {
                    jgen.writeNumber((Double) value);
                } else if (value instanceof Boolean) {
                    jgen.writeBoolean((Boolean) value);
                } else if (value instanceof byte[]) {
                    jgen.writeString(
                            BYTE_ARRAY_PREFIX
                            + Base64.getEncoder().encodeToString((byte[]) value)
                            + BYTE_ARRAY_SUFFIX);
                } else {
                    jgen.writeString(value.toString());
                }
            }
            jgen.writeEndArray();
        }

        jgen.writeEndObject();
    }

}
