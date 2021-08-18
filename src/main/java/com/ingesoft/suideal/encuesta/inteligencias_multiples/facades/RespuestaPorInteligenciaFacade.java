/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.inteligencias_multiples.facades;

import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.RespuestaPorInteligencia;
import com.ingesoft.interpro.facades.AbstractFacade;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.EncuestaEstilosAprendizaje;
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
public class RespuestaPorInteligenciaFacade extends AbstractFacade<RespuestaPorInteligencia> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RespuestaPorInteligenciaFacade() {
        super(RespuestaPorInteligencia.class);
    }
    
    public List<RespuestaPorInteligencia> getItemsXEncuesta(EncuestaInteligenciasMultiples encuestaEstilosAprendizaje) {
        Query query = em.createNamedQuery("RespuestaPorInteligencia.findByIdEncuestaInteligenciasMultiples");
        query.setParameter("idEncuestaInteligenciasMultiples", encuestaEstilosAprendizaje.getIdEncuesta());
        List<RespuestaPorInteligencia> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista;
        }
        return null;
    }
    
}
