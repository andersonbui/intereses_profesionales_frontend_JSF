package com.ingesoft.suideal.encuesta.estilos_aprendizaje.facade;

import com.ingesoft.interpro.facades.AbstractFacade;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.EncuestaEstilosAprendizaje;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaEstilo;
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
public class RespuestaEstiloFacade extends AbstractFacade<RespuestaEstilo> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RespuestaEstiloFacade() {
        super(RespuestaEstilo.class);
    }       
    
    public List<RespuestaEstilo> getItemsXEncuesta(EncuestaEstilosAprendizaje encuestaEstilosAprendizaje) {
        Query query = em.createNamedQuery("RespuestaEstilo.findByEncuestaEstilosAprendizaje");
        query.setParameter("encuestaEstilosAprendizaje", encuestaEstilosAprendizaje);
        List<RespuestaEstilo> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista;
        }
        return null;
    }
}
