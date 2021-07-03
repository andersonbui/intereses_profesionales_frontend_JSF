/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.EncuestaPersonalidad;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author debian
 */
@Stateless
public class EncuestaPersonalidadFacade extends AbstractFacade<EncuestaPersonalidad> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EncuestaPersonalidadFacade() {
        super(EncuestaPersonalidad.class);
    }
    
    public EncuestaPersonalidad buscarPorEncuesta(Encuesta encuesta) {
        Query query = em.createNamedQuery("EncuestaPersonalidad.findByEncuesta");
        EncuestaPersonalidad elem = (EncuestaPersonalidad) query.getSingleResult();
        return elem;
    }
}
