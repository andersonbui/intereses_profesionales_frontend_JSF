/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.TipoUsuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author debian
 */
@Stateless
public class TipoUsuarioFacade extends AbstractFacade<TipoUsuario> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoUsuarioFacade() {
        super(TipoUsuario.class);
    }
    
    public TipoUsuario obtenerPorTipo(String tipo) {
        
        Query query = em.createNamedQuery("TipoUsuario.findByTipo");
        query.setParameter("tipo", tipo);
        TipoUsuario tipoUsuario = (TipoUsuario) query.getResultList().get(0);
        if (tipoUsuario != null) {
            return tipoUsuario;
        }
        return null;
    }
       
    
}
