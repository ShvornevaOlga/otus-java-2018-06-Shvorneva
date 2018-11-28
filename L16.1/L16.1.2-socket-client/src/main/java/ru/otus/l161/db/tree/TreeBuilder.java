package ru.otus.l161.db.tree;

import ru.otus.l161.db.executor.AnnotationHandler;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

class TreeBuilder {

    void buildTree(Tree<NodeData> tree, Tree.Node node, Object o) {
        if (o!=null){
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                Object value;
                Class type = field.getType();
                value = ReflectionHelper.getFieldValue(o, field.getName());
                if (type.isPrimitive() || type.equals(String.class) || type.isArray()) {
                    addNode(tree, node, field, value, type);
                } else {
                    if (Map.class.isAssignableFrom(type)) {
                        Tree<NodeData> treeMap = new Tree<>((NodeData) node.getData());
                        Map.Entry<String, Object> entry = Map.entry(field.getName(), "");
                        NodeData data = new NodeData(entry, Object.class);
                        Tree.Node nodeMap = tree.addNode(node, data);
                        Map map = (Map) value;
                        Type t = field.getGenericType();
                        if (map != null) {
                            for (Object key : map.keySet()) {
                                Class mapGenericType;
                                if (t instanceof ParameterizedType) {
                                    mapGenericType = (Class) ((ParameterizedType) t).getActualTypeArguments()[1];
                                    Map.Entry<String, Object> entryMap = Map.entry(key.toString(), map.get(key));
                                    NodeData dataMap = new NodeData(entryMap, mapGenericType);
                                    treeMap.addNode(nodeMap, dataMap);
                                }
                            }
                        }
                    } else {
                        if (Collection.class.isAssignableFrom(type)) {
                            Collection collection = (Collection) value;
                            if (collection != null) {
                                Type t = field.getGenericType();
                                Class genericType;
                                if (t instanceof ParameterizedType) {
                                    genericType = (Class) ((ParameterizedType) t).getActualTypeArguments()[0];

                                    if (genericType.equals(String.class)) {
                                        String[] arr = (String[]) collection.toArray(new String[0]);
                                        addNode(tree, node, field, arr, arr.getClass());
                                    }
                                    if (genericType.equals(Integer.class)) {
                                        Integer[] arr = (Integer[]) collection.toArray(new Integer[0]);
                                        addNode(tree, node, field, arr, arr.getClass());
                                    }
                                    if (genericType.equals(Long.class)) {
                                        Long[] arr = (Long[]) collection.toArray(new Long[0]);
                                        addNode(tree, node, field, arr, arr.getClass());
                                    }
                                    if (genericType.equals(Boolean.class)) {
                                        Boolean[] arr = (Boolean[]) collection.toArray(new Boolean[0]);
                                        addNode(tree, node, field, arr, arr.getClass());
                                    }
                                    if (genericType.equals(Byte.class)) {
                                        Byte[] arr = (Byte[]) collection.toArray(new Byte[0]);
                                        addNode(tree, node, field, arr, arr.getClass());
                                    }
                                    if (genericType.equals(Short.class)) {
                                        Short[] arr = (Short[]) collection.toArray(new Short[0]);
                                        addNode(tree, node, field, arr, arr.getClass());
                                    }
                                    if (genericType.equals(Float.class)) {
                                        Float[] arr = (Float[]) collection.toArray(new Float[0]);
                                        addNode(tree, node, field, arr, arr.getClass());
                                    }
                                    if (genericType.equals(Double.class)) {
                                        Double[] arr = (Double[]) collection.toArray(new Double[0]);
                                        addNode(tree, node, field, arr, arr.getClass());
                                    }
                                    if (genericType.equals(Character.class)) {
                                        Character[] arr = (Character[]) collection.toArray(new Character[0]);
                                        addNode(tree, node, field, arr, arr.getClass());
                                    }
                                }
                            }
                        } else {
                            if (AnnotationHandler.isAnnotated(field, javax.persistence.OneToOne.class)) {
                                // if (value != null) {
                                Tree<NodeData> treeObject = new Tree<>((NodeData) node.getData());
                                //assert value != null;
                                Map.Entry<String, Object> entry = null;
                                if (value != null)
                                    entry = Map.entry(field.getName(), value);
                                //else entry = Map.entry(field.getName(), "");
                                NodeData data = new NodeData(entry, Object.class);
                                Tree.Node nodeObject = tree.addNode(node, data);
                                buildTree(treeObject, nodeObject, value);
                                // }
                            }
                        }
                    }
                }
            }
        }

    }


    private void addNode(Tree<NodeData> tree, Tree.Node node, Field field, Object value, Class type) {
        Map.Entry<String, Object> entry;
        NodeData data;
        entry = Map.entry(field.getName(), value);
        data = new NodeData(entry, type);
        tree.addNode(node, data);
    }
}