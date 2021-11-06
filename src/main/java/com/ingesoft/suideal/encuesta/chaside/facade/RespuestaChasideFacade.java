/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.chaside.facade;

import com.ingesoft.interpro.facades.AbstractFacade;
import com.ingesoft.suideal.encuesta.chaside.entidades.EncuestaChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.RespuestaChaside;
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
public class RespuestaChasideFacade extends AbstractFacade<RespuestaChaside> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RespuestaChasideFacade() {
        super(RespuestaChaside.class);
    }

       
    public List<RespuestaChaside> getItemsXEncuesta(EncuestaChaside encuestaChaside) {
        Query query = em.createNamedQuery("RespuestaChaside.findByIdEncuestaChaside");
        query.setParameter("idEncuestaChaside", encuestaChaside.getIdEncuesta());
        List<RespuestaChaside> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista;
        }
        return null;
    }
}
