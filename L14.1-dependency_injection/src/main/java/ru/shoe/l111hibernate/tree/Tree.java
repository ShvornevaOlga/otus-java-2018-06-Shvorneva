package ru.shoe.l111hibernate.tree;

import java.util.ArrayList;
import java.util.List;

class Tree<T> {
    private Node<T> root;

    Node<T> getRoot() {
        return root;
    }

    Tree(T rootData) {
        root = new Node<>(rootData);
    }

    Node addNode(Node node, T data) {
        Node newNode = new Node<>(data);
        node.addChildren(newNode);
        return newNode;
    }

    static class Node<T> {
        private T data;
        private List<Node> children;

        void addChildren(Node node){
            children.add(node);
        }
        T getData() {
            return data;
        }

        Node(T data) {
            this.data = data;
            children = new ArrayList<>();
        }

        void accept(NodeVisitor visitor) {
            visitor.visit(this);
        }

        List<Node> getChildren() {
            return children;
        }
    }
}
