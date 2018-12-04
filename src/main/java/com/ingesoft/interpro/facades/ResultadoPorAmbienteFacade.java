/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.ResultadoPorAmbiente;
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
public class ResultadoPorAmbienteFacade extends AbstractFacade<ResultadoPorAmbiente> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResultadoPorAmbienteFacade() {
        super(ResultadoPorAmbiente.class);
    }

    public List<ResultadoPorAmbiente> obtenerResultadoPorAmbiente(int idEncuesta) {
        Query query = em.createNamedQuery("ResultadoPorAmbiente.findByIdEncuesta");
        query.setParameter("idEncuesta", idEncuesta);
        List<ResultadoPorAmbiente> encuesta = query.getResultList();
        if (encuesta != null) {
            return encuesta;
        }
        return null;

    }
}
