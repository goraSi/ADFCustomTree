package view;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Set;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;


import oracle.adf.view.rich.component.rich.data.RichTreeTable;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetImpl;

public class TreeModelBean {
    private TreeModel treeModel;
    private RowKeySet rks = new RowKeySetImpl();

    public TreeModelBean() {
        initTreeModel();
    }
    
    private void initTreeModel() {
        DCIteratorBinding iteratorBinding = ADFUtils.findIterator("ManagersIterator");
        RowSetIterator managersIter = iteratorBinding.getRowSetIterator();
        treeModel = TreeModel.createInstance(managersIter);
    }

    public void expandTree(ActionEvent actionEvent) {
        rks = new RowKeySetImpl();
        RichTreeTable treeTable = (RichTreeTable) JSFUtils.findComponentInRoot("tt1");
        TreeModel treeModel = (TreeModel) treeTable.getValue();
        List<TreeNode> nodes = (List<TreeNode>) treeModel.getWrappedData();
        
        int index = 0;
        for (TreeNode node : nodes) {
            ArrayList list = new ArrayList();
            list.add(index++);
            rks.add(list);
            expandTreeChildren(node, list);            
        }
        treeTable.setDisclosedRowKeys(rks);
    }
    
    private void expandTreeChildren(TreeNode node, List parentRowKey) {
        List<TreeNode> children = (List<TreeNode>) node.getChildren();
        
        int index = 0;
        for (TreeNode child : children) {
            ArrayList list = new ArrayList();
            list.addAll(parentRowKey);
            list.add(index++);
            rks.add(list);
            if (child.getChildCount() > 0) {
                expandTreeChildren(child, list);
            }
        }
    }

    public void collapseTree(ActionEvent actionEvent) {
        RichTreeTable treeTable = (RichTreeTable) JSFUtils.findComponentInRoot("tt1");
        treeTable.getDisclosedRowKeys().clear();
    }

    public void treeRowDisclose(RowDisclosureEvent rowDisclosureEvent) {
        System.out.println(rowDisclosureEvent.getAddedSet());
    }

    public void setTreeModel(TreeModel treeModel) {
        this.treeModel = treeModel;
    }

    public TreeModel getTreeModel() {
        return treeModel;
    }

    public void setRks(RowKeySet rks) {
        this.rks = rks;
    }

    public RowKeySet getRks() {
        return rks;
    }
}
