/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.RespuestaPersonalidad;
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
public class RespuestaPersonalidadFacade extends AbstractFacade<RespuestaPersonalidad> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RespuestaPersonalidadFacade() {
        super(RespuestaPersonalidad.class);
    }
    
        public List<RespuestaPersonalidad> obtenerTodosPorEncuesta(Encuesta encuesta) {
        Query query = em.createNamedQuery("RespuestaPersonalidad.findByIdEncuesta");
        query.setParameter("idEncuesta", encuesta.getIdEncuesta());
        List lista = query.getResultList();
        if (lista != null && !lista.isEmpty()) {
                return lista;
        }
        return null;
    }
}
