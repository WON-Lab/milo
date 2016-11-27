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

package org.eclipse.milo.opcua.stack.core.types.structured;

import com.google.common.base.MoreObjects;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.serialization.OpcUaTypeDictionary;
import org.eclipse.milo.opcua.stack.core.serialization.UaDecoder;
import org.eclipse.milo.opcua.stack.core.serialization.UaEncoder;
import org.eclipse.milo.opcua.stack.core.serialization.UaStructure;
import org.eclipse.milo.opcua.stack.core.types.UaDataType;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

@UaDataType("DeleteNodesItem")
public class DeleteNodesItem implements UaStructure {

    public static final NodeId TypeId = Identifiers.DeleteNodesItem;
    public static final NodeId BinaryEncodingId = Identifiers.DeleteNodesItem_Encoding_DefaultBinary;
    public static final NodeId XmlEncodingId = Identifiers.DeleteNodesItem_Encoding_DefaultXml;

    protected final NodeId _nodeId;
    protected final Boolean _deleteTargetReferences;

    public DeleteNodesItem() {
        this._nodeId = null;
        this._deleteTargetReferences = null;
    }

    public DeleteNodesItem(NodeId _nodeId, Boolean _deleteTargetReferences) {
        this._nodeId = _nodeId;
        this._deleteTargetReferences = _deleteTargetReferences;
    }

    public NodeId getNodeId() { return _nodeId; }

    public Boolean getDeleteTargetReferences() { return _deleteTargetReferences; }

    @Override
    public NodeId getTypeId() { return TypeId; }

    @Override
    public NodeId getBinaryEncodingId() { return BinaryEncodingId; }

    @Override
    public NodeId getXmlEncodingId() { return XmlEncodingId; }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("NodeId", _nodeId)
            .add("DeleteTargetReferences", _deleteTargetReferences)
            .toString();
    }

    public static void encode(DeleteNodesItem deleteNodesItem, UaEncoder encoder) {
        encoder.encodeNodeId("NodeId", deleteNodesItem._nodeId);
        encoder.encodeBoolean("DeleteTargetReferences", deleteNodesItem._deleteTargetReferences);
    }

    public static DeleteNodesItem decode(UaDecoder decoder) {
        NodeId _nodeId = decoder.decodeNodeId("NodeId");
        Boolean _deleteTargetReferences = decoder.decodeBoolean("DeleteTargetReferences");

        return new DeleteNodesItem(_nodeId, _deleteTargetReferences);
    }

    static {
        OpcUaTypeDictionary.registerEncoder(DeleteNodesItem::encode, DeleteNodesItem.class, BinaryEncodingId, XmlEncodingId);
        OpcUaTypeDictionary.registerDecoder(DeleteNodesItem::decode, DeleteNodesItem.class, BinaryEncodingId, XmlEncodingId);
    }

}
