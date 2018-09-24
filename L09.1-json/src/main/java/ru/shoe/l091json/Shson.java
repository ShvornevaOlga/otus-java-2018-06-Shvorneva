package ru.shoe.l091json;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class Shson {
    private final TreeBuilder treeBuilder = new TreeBuilder();

    public String toJson(Object object) {
        Tree<NodeData> objectTree = new Tree<>(new NodeData());
        treeBuilder.buildTree(objectTree, objectTree.getRoot(), object);
        Tree.Node node = objectTree.getRoot();
        NodeVisitor visitor = new NodeVisitor();
        viewTree(node, visitor);
        return writeToString(visitor.getBuilder().build());
    }

    private void viewTree(Tree.Node node, NodeVisitor visitor) {
        List children = node.getChildren();
        for (Object child : children) {
            ((Tree.Node)child).accept(visitor);
            List nodeChildren = ((Tree.Node)child).getChildren();
            if (nodeChildren.size() > 0) {
                NodeVisitor nodeVisitor = new NodeVisitor();
                viewTree((Tree.Node) child, nodeVisitor);
                NodeData nodeData = (NodeData) ((Tree.Node)child).getData();
                Map.Entry data = nodeData.getFieldData();
                visitor.getBuilder().add(String.valueOf(data.getKey()), nodeVisitor.getBuilder());
            }
        }
    }

    private String writeToString(JsonObject jsonst) {
        StringWriter stWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
            jsonWriter.writeObject(jsonst);
        }
        return stWriter.toString();
    }
}
