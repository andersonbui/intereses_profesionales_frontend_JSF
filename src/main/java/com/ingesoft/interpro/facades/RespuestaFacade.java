/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.Respuesta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author debian
 */
@Stateless
public class RespuestaFacade extends AbstractFacade<Respuesta> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RespuestaFacade() {
        super(Respuesta.class);
    }

    public List<Respuesta> findAllByIdEncuesta(int idEncuesta) {

        Query query = em.createNamedQuery("Respuesta.findByIdEncuesta");
        query.setParameter("idEncuesta", idEncuesta);
        List<Respuesta> preguntas = query.getResultList();
        if (preguntas != null) {
            return preguntas;
        }
        return null;
    }
    
    public List<Respuesta> findAllByIdEncuestaIdTipo(int idEncuesta, int idTipo) {

        Query query = em.createNamedQuery("Respuesta.findByIdEncuesta");
        query.setParameter("idEncuesta", idEncuesta);
        List<Respuesta> preguntas = query.getResultList();
        if (preguntas != null) {
            return preguntas;
        }
        return null;
    }
}
