/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.AreaEncuesta;
import com.ingesoft.interpro.entidades.Encuesta;
import java.util.List;
import javax.persistence.Query;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 *
 * @author debian
 */
@Stateless
public class AreaEncuestaFacade extends AbstractFacade<AreaEncuesta> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AreaEncuestaFacade() {
        super(AreaEncuesta.class);
    }

    public List<AreaEncuesta> obtenerResultadoPorAmbiente(Encuesta encuesta) {
        Query query = em.createNamedQuery("AreaEncuesta.findByIdEncuesta");
        query.setParameter("idEncuesta", encuesta);
        List<AreaEncuesta> listaEncuestas = query.getResultList();
        return listaEncuestas;

    }

}
