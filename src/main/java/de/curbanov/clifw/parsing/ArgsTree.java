package de.curbanov.clifw.parsing;

import java.util.ArrayList;
import java.util.List;

public class ArgsTree extends Tree<Token> {}

class Tree<T> {

    private Node<T> root;

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(T data) {
        this.root = new Node<>();
        this.root.parent = null;
        this.root.children = new ArrayList<>();
        this.root.data = data;
    }

    public List<Node<T>> getChildrenFrom(Node<T> node) {
        return node.children;
    }

    public T getDataFrom(Node<T> node) {
        return node.data;
    }

    public Node<T> getParentFrom(Node<T> node) {
        return node.parent;
    }

    public Node<T> addChildTo(Node<T> node, T childData) {
        Node<T> child = new Node<>();
        child.parent = node;
        child.data = childData;
        child.children = new ArrayList<>();
        node.children.add(child);
        return child;
    }

    public static class Node<T> {

        private T data;
        private Node<T> parent;
        private List<Node<T>> children;
    }
}