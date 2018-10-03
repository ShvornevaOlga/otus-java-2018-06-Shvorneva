package ru.shoe.l101myorm.tree;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.List;

public class QueryBuilder {
    private static final TreeBuilder treeBuilder = new TreeBuilder();

    public static String insertDataSet(Object object) {
        StringBuilder query = new StringBuilder("insert into ");
        query.append(object.getClass().getSimpleName()).append(" (");
        StringBuilder fields = new StringBuilder();
        int countFields = 0;
        for (Field field : object.getClass().getDeclaredFields()) {
            if (!field.getName().equals("id")) {
                if (fields.length() == 0) {
                    fields.append(field.getName());
                } else {
                    fields.append(", ").append(field.getName());
                }
                countFields++;
            }
        }
        query.append(fields).append(") values(");
        for (int i = 0; i < countFields-1; i++){
            query.append("?,");
        }
        query.append("?)");
        return query.toString();
    }

    public static void acceptStatment(Object object, PreparedStatement statement) {
        Tree<NodeData> objectTree = new Tree<>(new NodeData());
        treeBuilder.buildTree(objectTree, objectTree.getRoot(), object);
        Tree.Node node = objectTree.getRoot();
        NodeVisitor visitor = new NodeVisitor(statement);
        viewTree(node, visitor);
    }

    public static String createTable(Class clazz) {
        StringBuilder query = new StringBuilder("create table if not exists ");
        query.append(clazz.getSimpleName()).append(" (id bigint auto_increment");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String nameField = field.getName();
            Class typeField = field.getType();
            if (typeField.equals(byte.class)) {
                query.append(", ").append(nameField).append(" TINYINT");
            }
            if (typeField.equals(short.class)) {
                query.append(", ").append(nameField).append(" SMALLINT");
            }
            if (typeField.equals(int.class)) {
                query.append(", ").append(nameField).append(" INT");
            }
            if (typeField.equals(long.class)) {
                query.append(", ").append(nameField).append(" BIGINT");
            }
            if (typeField.equals(float.class)) {
                query.append(", ").append(nameField).append(" FLOAT");
            }
            if (typeField.equals(double.class)) {
                query.append(", ").append(nameField).append(" DOUBLE");
            }
            if (typeField.equals(String.class)) {
                query.append(", ").append(nameField).append(" varchar(256)");
            }
        }
        query.append(", primary key (id))");
        return query.toString();
    }

    public static String dropTable(Class clazz) {
        return "drop table " + clazz.getSimpleName();
    }


    private static void viewTree(Tree.Node node, NodeVisitor visitor) {
        List children = node.getChildren();
        for (Object child : children) {
            ((Tree.Node) child).accept(visitor);
        }
    }
}
