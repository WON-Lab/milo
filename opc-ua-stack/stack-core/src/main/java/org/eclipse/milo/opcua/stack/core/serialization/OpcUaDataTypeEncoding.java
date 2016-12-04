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

package org.eclipse.milo.opcua.stack.core.serialization;

import java.io.StringWriter;
import java.nio.ByteOrder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.eclipse.milo.opcua.stack.core.StatusCodes;
import org.eclipse.milo.opcua.stack.core.UaSerializationException;
import org.eclipse.milo.opcua.stack.core.serialization.codec.OpcBinaryStreamReader;
import org.eclipse.milo.opcua.stack.core.serialization.codec.OpcBinaryStreamWriter;
import org.eclipse.milo.opcua.stack.core.serialization.codec.OpcBinaryTypeCodec;
import org.eclipse.milo.opcua.stack.core.serialization.codec.OpcXmlStreamReader;
import org.eclipse.milo.opcua.stack.core.serialization.codec.OpcXmlStreamWriter;
import org.eclipse.milo.opcua.stack.core.serialization.codec.OpcXmlTypeCodec;
import org.eclipse.milo.opcua.stack.core.serialization.codec.TypeManager;
import org.eclipse.milo.opcua.stack.core.types.builtin.ByteString;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.XmlElement;

public class OpcUaDataTypeEncoding implements DataTypeEncoding {

    private final ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;

    @Override
    public ByteString encodeToByteString(
        Object object,
        NodeId encodingTypeId,
        TypeManager typeManager) throws UaSerializationException {

        try {
            @SuppressWarnings("unchecked")
            OpcBinaryTypeCodec<Object> codec =
                (OpcBinaryTypeCodec<Object>) typeManager.getBinaryCodec(encodingTypeId);

            ByteBuf buffer = allocator.buffer().order(ByteOrder.LITTLE_ENDIAN);

            OpcBinaryStreamWriter writer = null; // TODO

            codec.encode(() -> typeManager, object, writer);

            byte[] bs = new byte[buffer.readableBytes()];
            buffer.readBytes(bs);
            buffer.release();

            return ByteString.of(bs);
        } catch (ClassCastException e) {
            throw new UaSerializationException(StatusCodes.Bad_EncodingError, e);
        }
    }

    @Override
    public XmlElement encodeToXmlElement(
        Object object,
        NodeId encodingTypeId,
        TypeManager typeManager) throws UaSerializationException {

        try {
            @SuppressWarnings("unchecked")
            OpcXmlTypeCodec<Object> codec =
                (OpcXmlTypeCodec<Object>) typeManager.getXmlCodec(encodingTypeId);

            StringWriter stringWriter = new StringWriter();

            OpcXmlStreamWriter writer = null; // TODO

            codec.encode(() -> typeManager, object, writer);

            return new XmlElement(stringWriter.toString());
        } catch (ClassCastException e) {
            throw new UaSerializationException(StatusCodes.Bad_EncodingError, e);
        }
    }

    @Override
    public Object decodeFromByteString(
        ByteString encoded,
        NodeId encodingTypeId,
        TypeManager typeManager) throws UaSerializationException {

        try {
            @SuppressWarnings("unchecked")
            OpcBinaryTypeCodec<Object> codec =
                (OpcBinaryTypeCodec<Object>) typeManager.getBinaryCodec(encodingTypeId);

            byte[] bs = encoded.bytes();
            if (bs == null) bs = new byte[0];

            ByteBuf buffer = Unpooled
                .wrappedBuffer(bs)
                .order(ByteOrder.LITTLE_ENDIAN);

            OpcBinaryStreamReader reader = new OpcBinaryStreamReader(buffer);

            return codec.decode(() -> typeManager, reader);
        } catch (ClassCastException e) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError, e);
        }
    }

    @Override
    public Object decodeFromXmlElement(
        XmlElement encoded,
        NodeId encodingTypeId,
        TypeManager typeManager) throws UaSerializationException {

        try {
            @SuppressWarnings("unchecked")
            OpcXmlTypeCodec<Object> codec =
                (OpcXmlTypeCodec<Object>) typeManager.getXmlCodec(encodingTypeId);

            OpcXmlStreamReader reader = null; // TODO

            return codec.decode(() -> typeManager, reader);
        } catch (ClassCastException e) {
            throw new UaSerializationException(StatusCodes.Bad_DecodingError, e);
        }
    }

}
