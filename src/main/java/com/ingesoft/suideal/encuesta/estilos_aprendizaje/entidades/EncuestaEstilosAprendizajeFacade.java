/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.facades.AbstractFacade;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.EncuestaEstilosAprendizaje;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author debian
 */
@Stateless
public class EncuestaEstilosAprendizajeFacade extends AbstractFacade<EncuestaEstilosAprendizaje> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EncuestaEstilosAprendizajeFacade() {
        super(EncuestaEstilosAprendizaje.class);
    }
    
    public EncuestaEstilosAprendizaje buscarPorIdEncuesta(Integer idEncuesta) {
        Query query = em.createNamedQuery("EncuestaEstilosAprendizaje.findByIdEncuesta");
        query.setParameter("encuesta", idEncuesta);
        EncuestaEstilosAprendizaje elem = (EncuestaEstilosAprendizaje) query.getSingleResult();
        return elem;
    }
}
