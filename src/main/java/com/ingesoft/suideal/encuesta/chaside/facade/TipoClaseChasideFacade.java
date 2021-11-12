package com.ingesoft.suideal.encuesta.chaside.facade;

import com.ingesoft.interpro.facades.AbstractFacade;
import com.ingesoft.suideal.encuesta.chaside.entidades.TipoClaseChaside;
import com.ingesoft.suideal.encuesta.chaside.entidades.EncuestaChaside;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author debian
 */
@Stateless
public class TipoClaseChasideFacade extends AbstractFacade<TipoClaseChaside> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoClaseChasideFacade() {
        super(TipoClaseChaside.class);
    }       
    
    public TipoClaseChaside getItemsXEncuesta(int idTipoChaside, int idClaseChaside) {
        Query query = em.createNamedQuery("TipoClaseChaside.findByIdClaseIdTipoChaside");
        query.setParameter("idClaseChaside", idClaseChaside);
        query.setParameter("idTipoChaside", idTipoChaside);
        
        try {
            TipoClaseChaside unregistro = (TipoClaseChaside) query.getSingleResult();
            return unregistro;
        } catch (NoResultException err) {
        }
        return null;
    }
}
