/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.inteligencias_multiples.facades;

import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.RespuestaInteligenciasMultiples;
import com.ingesoft.interpro.facades.AbstractFacade;
import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.EncuestaInteligenciasMultiples;
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
public class RespuestaInteligenciasMultiplesFacade extends AbstractFacade<RespuestaInteligenciasMultiples> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RespuestaInteligenciasMultiplesFacade() {
        super(RespuestaInteligenciasMultiples.class);
    }
    
    public List<RespuestaInteligenciasMultiples> getItemsXEncuesta(Integer idEncuestaEstilosAprendizaje) {
        Query query = em.createNamedQuery("RespuestaInteligenciasMultiples.findByIdEncuestaInteligenciasMultiples");
        query.setParameter("idEncuestaInteligenciasMultiples", idEncuestaEstilosAprendizaje);
        List<RespuestaInteligenciasMultiples> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista;
        }
        return null;
    }
    
}