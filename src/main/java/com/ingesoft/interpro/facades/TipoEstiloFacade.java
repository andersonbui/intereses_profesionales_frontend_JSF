package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.TipoEstilo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author debian
 */
@Stateless
public class TipoEstiloFacade extends AbstractFacade<TipoEstilo> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoEstiloFacade() {
        super(TipoEstilo.class);
    }       
    
}