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

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;


import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;

public class TreeModelBean {
    private TreeModel treeModel;

    public TreeModelBean() {
        initTreeModel();
    }
    
    private void initTreeModel() {
        DCIteratorBinding iteratorBinding = ADFUtils.findIterator("ManagersIterator");
        RowSetIterator managersIter = iteratorBinding.getRowSetIterator();
        treeModel = TreeModel.createInstance(managersIter);
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
}
