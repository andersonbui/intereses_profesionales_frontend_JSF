package com.ingesoft.interpro.facades;

import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.PreguntaEstilosAprendizaje;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author debian
 */
@Stateless
public class PreguntaEstilosAprendizajeFsFacade extends AbstractFacade<PreguntaEstilosAprendizaje> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PreguntaEstilosAprendizajeFsFacade() {
        super(PreguntaEstilosAprendizaje.class);
    }       
    
}
