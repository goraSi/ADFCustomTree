package view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;

import oracle.jbo.server.ViewRowImpl;

import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;

public class TreeModel extends ChildPropertyTreeModel {
    public TreeModel() {
    }

    public TreeModel(java.lang.Object p1, java.lang.String p2) {
        super(p1, p2);
    }

    public boolean isContainer() {
        TreeNode node = (TreeNode)getRowData();
        return node.getChildCount() > 0;
    }
    
    public static TreeModel createInstance(RowSetIterator rsi) {
        return new TreeModel(initTreeNodes(rsi, new TreeNode(), null), "children");
    }

    private static Collection initTreeNodes(RowSetIterator iter, TreeNode rootNode, Integer level) {
        int i = level != null ? level : 1;
        do {
            ViewRowImpl row = (ViewRowImpl) iter.next();
            TreeNode treenode = new TreeNode();
            treenode.setDescription(row.toString());
            treenode.setNodeType(row.getViewDef() != null ? row.getViewDef().getName() : row.getClass().toString());
            treenode.setLevel(i);
            treenode.setAttributes(getAttributesForRow(row));
            treenode.setRowKey(row.getKey());

            for (RowSetIterator rsi : getRowSetForAtrrs(treenode.getAttributes())) {
                initTreeNodes(rsi, treenode, i+1);
            }
            
            rootNode.addChildren(treenode);
        } while (iter.hasNext());
        return rootNode.getChildren();
    }

    private static Map getAttributesForRow(Row row) {
        Map attributes = new HashMap();
        String[] attributeNames = row.getAttributeNames();
        Object[] attributeValues = row.getAttributeValues();
        
        for (int i = 0; i < attributeNames.length; i++) {
            attributes.put(attributeNames[i], attributeValues[i]);
        }
        return attributes;
    }
    
    private static List<RowSetIterator> getRowSetForAtrrs(Map attributes) {
        List<RowSetIterator> rsiList = new ArrayList<RowSetIterator>();
        for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) attributes).entrySet()) {
            if (entry.getValue() != null && entry.getValue() instanceof RowSetIterator) {
                RowSetIterator rsi = (RowSetIterator) entry.getValue();
                Row[] allRowsInRange = ((RowSetIterator) entry.getValue()).getAllRowsInRange();
                if (allRowsInRange != null && allRowsInRange.length > 0) {
                    rsiList.add(rsi);
                }
            }
        }
        return rsiList;
    }
}
