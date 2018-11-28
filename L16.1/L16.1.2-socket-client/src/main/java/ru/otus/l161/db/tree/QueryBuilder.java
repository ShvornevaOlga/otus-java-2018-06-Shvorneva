package ru.otus.l161.db.tree;



import ru.otus.l161.db.base.DataSet;
import ru.otus.l161.db.executor.AnnotationHandler;
import ru.otus.l161.db.executor.DBServiceException;
import ru.otus.l161.db.executor.TExecutor;

import javax.persistence.OneToOne;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class QueryBuilder {
    private static final TreeBuilder treeBuilder = new TreeBuilder();

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
            if (!typeField.isPrimitive() && !typeField.equals(String.class) && !Collection.class.isAssignableFrom(typeField)) {
                query.append(", ").append(field.getName()).append("_id").append(" BIGINT  REFERENCES ").
                        append(field.getType().getSimpleName()).append(" (Id)");
            }
        }
        query.append(", primary key (id))");
        return query.toString();
    }

    public static String dropTable(Class clazz) {
        return "drop table if exists " + clazz.getSimpleName();
    }

    public static String insertDataSet(Object object) {
        StringBuilder query = new StringBuilder("insert into ");
        query.append(object.getClass().getSimpleName()).append(" (");
        StringBuilder fields = new StringBuilder();
        int countFields = 0;
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                if(field.get(object)!=null)
                if (!field.getName().equals("id") && !Collection.class.isAssignableFrom(field.getType())) {
                    if(AnnotationHandler.isAnnotated(field,OneToOne.class)){
                        if (fields.length() == 0) {
                            fields.append(field.getName()).append("_id");
                        } else {
                            fields.append(", ").append(field.getName()).append("_id");
                        }
                        countFields++;
                    }else
                    if (!AnnotationHandler.isAnnotated(field, javax.persistence.ManyToOne.class)) {
                        if (fields.length() == 0) {
                            fields.append(field.getName());
                        } else {
                            fields.append(", ").append(field.getName());
                        }
                        countFields++;
                    }
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        query.append(fields).append(") values(");
        for (int i = 0; i < countFields - 1; i++) {
            query.append("?,");
        }
        query.append("?)");
        return query.toString();
    }


    public static void acceptStatment(Object object, PreparedStatement statement) throws SQLException {
        Tree<NodeData> objectTree = new Tree<>(new NodeData());
        treeBuilder.buildTree(objectTree, objectTree.getRoot(), object);
        Tree.Node node = objectTree.getRoot();
        NodeVisitor visitor = new NodeVisitor(statement);
        Connection connection = statement.getConnection();
        viewTree(node, visitor, connection);
    }


    private static void viewTree(Tree.Node node, NodeVisitor visitor, Connection connection) {
        List children = node.getChildren();
        for (Object child : children) {

            List nodeChildren = ((Tree.Node) child).getChildren();
            if (nodeChildren.size() > 0) {
                TExecutor executor = new TExecutor(connection);
                try {
                    executor.save((DataSet) ((NodeData) ((Tree.Node) child).getData()).getFieldData().getValue());
                } catch (DBServiceException e) {
                    System.err.println("Can not save " +((NodeData) ((Tree.Node) child).getData()).getFieldData().getValue());
                    e.printStackTrace();
                }
            }
            ((Tree.Node) child).accept(visitor);
        }
    }
}
