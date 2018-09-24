package ru.shoe.l091json;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Map;

class NodeVisitor {
    private JsonObjectBuilder builder = Json.createObjectBuilder();

    void visit(Tree.Node element) {
        if (element.getData().getClass().equals(NodeData.class)) {
            NodeData nodeData = (NodeData) element.getData();
            Class type = nodeData.getFieldType();
            Map.Entry data = nodeData.getFieldData();
            if (type.equals(int.class) || type.equals(Integer.class))
                builder.add(String.valueOf(data.getKey()), (int) (data.getValue()));
            if (type.equals(boolean.class) || type.equals(Boolean.class))
                builder.add(String.valueOf(data.getKey()), (boolean) (data.getValue()));
            if (type.equals(byte.class) || type.equals(Byte.class))
                builder.add(String.valueOf(data.getKey()), (byte) (data.getValue()));
            if (type.equals(char.class) || type.equals(Character.class))
                builder.add(String.valueOf(data.getKey()), String.valueOf((char) (data.getValue())));
            if (type.equals(short.class) || type.equals(Short.class))
                builder.add(String.valueOf(data.getKey()), (short) (data.getValue()));
            if (type.equals(long.class) || type.equals(Long.class))
                builder.add(String.valueOf(data.getKey()), (long) (data.getValue()));
            if (type.equals(float.class) || type.equals(Float.class))
                builder.add(String.valueOf(data.getKey()), new BigDecimal(data.getValue().toString()));
            if (type.equals(double.class) || type.equals(Double.class))
                builder.add(String.valueOf(data.getKey()), (double) (data.getValue()));
            if (type.equals(String.class))
                builder.add(String.valueOf(data.getKey()), (String) (data.getValue()));
            if (type.isArray()) {
                JsonArrayBuilder jsonArray = Json.createArrayBuilder();
                for (int i = 0; i < Array.getLength(data.getValue()); i++) {
                    if (type.equals(int[].class) || type.equals(Integer[].class))
                        jsonArray.add((int) Array.get(data.getValue(), i));
                    if (type.equals(byte[].class) || type.equals(Byte[].class))
                        jsonArray.add((byte) Array.get(data.getValue(), i));
                    if (type.equals(short[].class) || type.equals(Short[].class))
                        jsonArray.add((short) Array.get(data.getValue(), i));
                    if (type.equals(char[].class) || type.equals(Character[].class))
                        jsonArray.add(String.valueOf((char) Array.get(data.getValue(), i)));
                    if (type.equals(boolean[].class) || type.equals(Boolean[].class))
                        jsonArray.add((boolean) Array.get(data.getValue(), i));
                    if (type.equals(long[].class) || type.equals(Long[].class))
                        jsonArray.add((long) Array.get(data.getValue(), i));
                    if (type.equals(float[].class) || type.equals(Float[].class))
                        jsonArray.add(new BigDecimal(Array.get(data.getValue(), i).toString()));
                    if (type.equals(double[].class) || type.equals(Double[].class))
                        jsonArray.add((double) Array.get(data.getValue(), i));
                    if (type.equals(String[].class))
                        jsonArray.add((String) Array.get(data.getValue(), i));
                }
                builder.add(String.valueOf(data.getKey()), jsonArray);
            }
        }
    }

    JsonObjectBuilder getBuilder() {
        return builder;
    }
}
