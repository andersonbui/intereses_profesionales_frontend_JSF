package com.ingesoft.suideal.encuesta.chaside.facade;

import com.ingesoft.interpro.facades.AbstractFacade;
import com.ingesoft.suideal.encuesta.chaside.entidades.ResultadoChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.EncuestaChaside;
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
public class ResultadoChasideFacade extends AbstractFacade<ResultadoChaside> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResultadoChasideFacade() {
        super(ResultadoChaside.class);
    }       
    
    public List<ResultadoChaside> getItemsXEncuesta(EncuestaChaside encuestaChaside) {
        Query query = em.createNamedQuery("ResultadoChaside.findByIdEncuestaChaside");
        query.setParameter("idEncuestaChaside", encuestaChaside.getIdEncuesta());
        List<ResultadoChaside> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista;
        }
        return null;
    }
}
