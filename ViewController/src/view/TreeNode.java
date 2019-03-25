package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import oracle.jbo.Key;
import oracle.jbo.Row;

public class TreeNode {

    private String description ;
    private Collection children;
    private String nodeType;
    private boolean nodeSelected;
    private int level;
    private Map attributes;
    private Key rowKey;

    public TreeNode() {
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setChildren(Collection children) {
        this.children = children;
    }

    public Collection getChildren() {
        return children;
    }
    
    public void addChildren(Object child) {
        children = children != null ? children : new ArrayList();
        children.add(child);
    }

    public int getChildCount() {
      if (children == null)
        return 0;
      else
        return children.size();
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeSelected(boolean nodeSelected) {
        this.nodeSelected = nodeSelected;
    }

    public boolean isNodeSelected() {
        return nodeSelected;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setAttributes(Map attributes) {
        this.attributes = attributes;
    }

    public Map getAttributes() {
        return attributes;
    }

    public void setRowKey(Key rowKey) {
        this.rowKey = rowKey;
    }

    public Key getRowKey() {
        return rowKey;
    }

    @Override
    public String toString() {
        if (attributes != null && !attributes.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            for (Object value : attributes.values()) {
                sb.append(value).append(" ");
            }
            return sb.toString();
        } else {
            return description;
        }
    }
}

