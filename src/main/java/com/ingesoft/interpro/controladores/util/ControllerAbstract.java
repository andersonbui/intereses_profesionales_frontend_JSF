/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.controladores.util;

import com.ingesoft.interpro.controladores.Controllers;
import com.ingesoft.interpro.facades.AbstractFacade;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author anderson
 * @param <Entidad>
 * @param <EntidadFacade>
 */
public abstract class ControllerAbstract<Entidad, EntidadFacade>  extends Controllers implements Serializable  {

    @Override
    protected AbstractFacade getFacade() {
        return (AbstractFacade) getEjbFacade();
    }
    
    private Entidad selected;
    private List<Entidad> items = null;
    
    public List<Entidad> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
        
    public boolean create() {
        Entidad res =  (Entidad) persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString(getStringCreated()), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = res;    // Invalidate list of items to trigger re-query.
        }
        return !JsfUtil.isValidationFailed();
    }

    public boolean update() {
        Entidad res =  (Entidad) persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString(getStringUpdated()), selected);
         if (!JsfUtil.isValidationFailed()) {
            selected = res;    // Invalidate list of items to trigger re-query.
        }
        return !JsfUtil.isValidationFailed();
    }

    public boolean destroy() {
        String msg = getStringDeleted();
        if(msg != null) {
            msg = ResourceBundle.getBundle("/Bundle").getString(msg);
        }
        persist(JsfUtil.PersistAction.DELETE, msg, selected, msg != null);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
        }
        return !JsfUtil.isValidationFailed();
    }
    
    public List<Entidad> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }
    
    public List<Entidad> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    /**************************************************************************
     * Abstracts methods
     **************************************************************************/
    
    /**
     * 
     * @return 
     */
    public abstract Entidad prepareCreate();
    
    /**
     * 
     * @return 
     */
    public abstract EntidadFacade getEjbFacade();

    /**************************************************************************
     * Getter and Setter methods
     **************************************************************************/
    
    /**
     * 
     * @return 
     */
    public Entidad getSelected() {
        return selected;
    }

    /**
     * 
     * @param selected 
     */
    public void setSelected(Entidad selected) {
        this.selected = selected;
    }
    
    /**
     * 
     * @return 
     */
    protected String getStringCreated(){
        return null;
    }
    
    /**
     * 
     * @return 
     */
    protected String getStringUpdated(){
        return null;
    }
    
    /**
     * 
     * @return 
     */
    protected String getStringDeleted(){
        return null;
    }
}
