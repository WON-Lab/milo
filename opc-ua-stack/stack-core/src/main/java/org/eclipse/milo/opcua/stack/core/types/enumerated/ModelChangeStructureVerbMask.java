/*
 * Copyright (c) 2016 Kevin Herron
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *   http://www.eclipse.org/org/documents/edl-v10.html.
 */

package org.eclipse.milo.opcua.stack.core.types.enumerated;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import org.eclipse.milo.opcua.stack.core.serialization.OpcUaTypeDictionary;
import org.eclipse.milo.opcua.stack.core.serialization.UaDecoder;
import org.eclipse.milo.opcua.stack.core.serialization.UaEncoder;
import org.eclipse.milo.opcua.stack.core.serialization.UaEnumeration;

public enum ModelChangeStructureVerbMask implements UaEnumeration {

    NodeAdded(1),
    NodeDeleted(2),
    ReferenceAdded(4),
    ReferenceDeleted(8),
    DataTypeChanged(16);

    private final int value;

    ModelChangeStructureVerbMask(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    private static final ImmutableMap<Integer, ModelChangeStructureVerbMask> VALUES;

    static {
        Builder<Integer, ModelChangeStructureVerbMask> builder = ImmutableMap.builder();
        for (ModelChangeStructureVerbMask e : values()) {
            builder.put(e.getValue(), e);
        }
        VALUES = builder.build();
    }

    public static ModelChangeStructureVerbMask from(Integer value) {
        if (value == null) return null;
        return VALUES.getOrDefault(value, null);
    }

    public static void encode(ModelChangeStructureVerbMask modelChangeStructureVerbMask, UaEncoder encoder) {
        encoder.encodeInt32(null, modelChangeStructureVerbMask.getValue());
    }

    public static ModelChangeStructureVerbMask decode(UaDecoder decoder) {
        int value = decoder.decodeInt32(null);

        return VALUES.getOrDefault(value, null);
    }

    static {
        OpcUaTypeDictionary.registerEncoder(ModelChangeStructureVerbMask::encode, ModelChangeStructureVerbMask.class);
        OpcUaTypeDictionary.registerDecoder(ModelChangeStructureVerbMask::decode, ModelChangeStructureVerbMask.class);
    }

}
