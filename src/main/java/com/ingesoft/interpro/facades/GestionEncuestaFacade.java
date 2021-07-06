/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.GestionEncuestas;
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
public class GestionEncuestaFacade extends AbstractFacade<GestionEncuestas> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GestionEncuestaFacade() {
        super(GestionEncuestas.class);
    }

    public GestionEncuestas getGestionEncuestaById(int idGestionEncuesta) {
        Query query = em.createNamedQuery("GestionEncuesta.getGestionEncuestaById");
        query.setParameter("id", idGestionEncuesta);
        List<GestionEncuestas> GestionEncuesta = query.getResultList();
        if (!GestionEncuesta.isEmpty()) {
            return GestionEncuesta.get(0);
        }
        return null;
    }

}