/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.CodigoInstitucion;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author debian
 */
@Stateless
public class CodigoInstitucionFacade extends AbstractFacade<CodigoInstitucion> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CodigoInstitucionFacade() {
        super(CodigoInstitucion.class);
    }
    
     
    public CodigoInstitucion buscarPorCodigoActivacion(String codigo) {
        Query query = em.createNamedQuery("CodigoInstitucion.findByCodigoActivacion");
        query.setParameter("codigoActivacion", codigo);
        CodigoInstitucion codigoActiv = (CodigoInstitucion) query.getResultList().get(0);
        if (codigoActiv != null) {
            return codigoActiv;
        }
        return null;

    }
}
