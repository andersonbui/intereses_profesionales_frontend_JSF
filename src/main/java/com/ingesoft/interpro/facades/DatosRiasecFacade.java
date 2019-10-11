/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.DatosRiasec;
import com.ingesoft.interpro.entidades.TipoAmbiente;
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
public class DatosRiasecFacade extends AbstractFacade<DatosRiasec> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatosRiasecFacade() {
        super(DatosRiasec.class);
    }

    public List<DatosRiasec> buscarPorTiposAmbiente(TipoAmbiente amb1, TipoAmbiente amb2, TipoAmbiente amb3) {
        Query query = em.createNamedQuery("DatosRiasec.findPorTipoAmbiente123");
        query.setParameter("amb1", amb1);
        query.setParameter("amb2", amb2);
        query.setParameter("amb3", amb3);
        List<DatosRiasec> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista;
        }
        return null;
    }

    public List<DatosRiasec> buscarPorTiposAmbiente(TipoAmbiente amb1, TipoAmbiente amb2) {
        Query query = em.createNamedQuery("DatosRiasec.findPorTipoAmbiente12");
        query.setParameter("amb1", amb1);
        query.setParameter("amb2", amb2);
        List<DatosRiasec> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista;
        }
        return null;
    }
    
    public List<DatosRiasec> buscarPorTiposAmbiente(TipoAmbiente amb1) {
        Query query = em.createNamedQuery("DatosRiasec.findPorTipoAmbiente1");
        query.setParameter("amb1", amb1);
        List<DatosRiasec> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista;
        }
        return null;
    }
}
