/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.Pregunta;
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
public class PreguntaFacade extends AbstractFacade<Pregunta> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PreguntaFacade() {
        super(Pregunta.class);
    }

    public List<Pregunta> findAllAmbiente() {
        return buscarPorTipo("AMBIENTE");
    }

    public List<Pregunta> findAllPersonalidad() {
        return buscarPorTipo("PERSONALIDAD");
    }
    
    private List<Pregunta> findAllIdTipo(int tipo) {
        
         Query query = em.createNamedQuery("Pregunta.findByIdTipo");
        query.setParameter("idTipo", tipo);
        List<Pregunta> preguntas = query.getResultList();
        if (preguntas != null) {
            return preguntas;
        }
        return null;
    }
    private List<Pregunta> buscarPorTipo(String tipo) {
         Query query = em.createNamedQuery("Pregunta.findByTipo");
        query.setParameter("tipo", tipo);
        List<Pregunta> preguntas = query.getResultList();
        if (preguntas != null) {
            return preguntas;
        }
        return null;
    }
    
}
