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

package org.eclipse.milo.opcua.stack.core.serialization.codec;

import org.eclipse.milo.opcua.stack.core.StatusCodes;
import org.eclipse.milo.opcua.stack.core.UaSerializationException;

public interface SerializationContext {

    SerializationContext INTERNAL = () -> TypeManager.BUILTIN;

    TypeManager getTypeManager();

    default Object decode(
        String namespaceUri,
        String typeName,
        OpcBinaryStreamReader reader) throws UaSerializationException {

        OpcBinaryTypeCodec<?> codec = getTypeManager().getBinaryCodec(namespaceUri, typeName);

        if (codec == null) {
            throw new UaSerializationException(
                StatusCodes.Bad_DecodingError,
                String.format(
                    "no OpcBinaryTypeCodec registered for typeName=%s under namespaceUri=%s",
                    typeName, namespaceUri)
            );
        }

        return codec.decode(this, reader);
    }

    default Object decode(
        String namespaceUri,
        String typeName,
        OpcXmlStreamReader reader) throws UaSerializationException {

        OpcXmlTypeCodec<?> codec = getTypeManager().getXmlCodec(namespaceUri, typeName);

        if (codec == null) {
            throw new UaSerializationException(
                StatusCodes.Bad_DecodingError,
                String.format(
                    "no OpcXmlTypeCodec registered for typeName=%s under namespaceUri=%s",
                    typeName, namespaceUri)
            );
        }

        return codec.decode(this, reader);
    }

    default void encode(
        String namespaceUri,
        String typeName,
        Object encodable,
        OpcBinaryStreamWriter writer) throws UaSerializationException {

        @SuppressWarnings("unchecked")
        OpcBinaryTypeCodec<Object> codec =
            (OpcBinaryTypeCodec<Object>) getTypeManager().getBinaryCodec(namespaceUri, typeName);

        if (codec == null) {
            throw new UaSerializationException(
                StatusCodes.Bad_EncodingError,
                String.format(
                    "no OpcBinaryTypeCodec registered for typeName=%s under namespaceUri=%s",
                    typeName, namespaceUri)
            );
        }

        codec.encode(this, encodable, writer);
    }

    default void encode(
        String namespaceUri,
        String typeName,
        Object encodable,
        OpcXmlStreamWriter writer) throws UaSerializationException {

        @SuppressWarnings("unchecked")
        OpcXmlTypeCodec<Object> codec =
            (OpcXmlTypeCodec<Object>) getTypeManager().getXmlCodec(namespaceUri, typeName);

        if (codec == null) {
            throw new UaSerializationException(
                StatusCodes.Bad_EncodingError,
                String.format(
                    "no OpcXmlTypeCodec registered for typeName=%s under namespaceUri=%s",
                    typeName, namespaceUri)
            );
        }

        codec.encode(this, encodable, writer);
    }

}
