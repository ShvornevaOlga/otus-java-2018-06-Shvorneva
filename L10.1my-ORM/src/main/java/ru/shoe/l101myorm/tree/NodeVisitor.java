package ru.shoe.l101myorm.tree;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

class NodeVisitor {
    private PreparedStatement statement;
    private int count = 0;

    NodeVisitor(PreparedStatement statement) {
        this.statement = statement;
    }

    void visit(Tree.Node element) {
        if (element.getData().getClass().equals(NodeData.class)) {
            NodeData nodeData = (NodeData) element.getData();
            Class type = nodeData.getFieldType();
            Map.Entry data = nodeData.getFieldData();
            if (!data.getKey().toString().equals("id")) {
                try {
                    count++;
                    if (type.equals(int.class) || type.equals(Integer.class)) {
                        statement.setInt(count, (int) data.getValue());
                    }
                    if (type.equals(boolean.class) || type.equals(Boolean.class)) {
                        statement.setBoolean(count, (boolean) data.getValue());
                    }
                    if (type.equals(byte.class) || type.equals(Byte.class)) {
                        statement.setByte(count, (byte) data.getValue());
                    }
                    if (type.equals(char.class) || type.equals(Character.class)) {
                        statement.setString(count, String.valueOf((char) (data.getValue())));
                    }
                    if (type.equals(short.class) || type.equals(Short.class)) {
                        statement.setShort(count, (short) data.getValue());
                    }
                    if (type.equals(long.class) || type.equals(Long.class)) {
                        statement.setLong(count, (long) data.getValue());
                    }
                    if (type.equals(float.class) || type.equals(Float.class)) {
                        statement.setFloat(count, (float) data.getValue());
                    }
                    if (type.equals(double.class) || type.equals(Double.class)) {
                        statement.setDouble(count, (double) data.getValue());
                    }
                    if (type.equals(String.class)) {
                        statement.setString(count, (String) data.getValue());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}
