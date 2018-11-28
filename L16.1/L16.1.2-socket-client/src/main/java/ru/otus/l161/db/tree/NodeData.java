package ru.otus.l161.db.tree;

import java.util.Map;

class NodeData {
    private Map.Entry fieldData;
    private Class fieldType;

    Map.Entry getFieldData() {
        return fieldData;
    }

    Class getFieldType() {
        return fieldType;
    }

    NodeData(Map.Entry fieldData, Class fieldType) {
        this.fieldData = fieldData;
        this.fieldType = fieldType;
    }

    NodeData() {
    }
}
