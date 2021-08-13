package com.ingesoft.suideal.encuesta.estilos_aprendizaje.facade;

import com.ingesoft.interpro.facades.AbstractFacade;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.EncuestaEstilosAprendizaje;
import com.ingesoft.suideal.encuesta.estilos_aprendizaje.entidades.RespuestaPorEstilo;
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
public class RespuestaPorEstiloFacade extends AbstractFacade<RespuestaPorEstilo> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RespuestaPorEstiloFacade() {
        super(RespuestaPorEstilo.class);
    }       
    
    public List<RespuestaPorEstilo> getItemsXEncuesta(EncuestaEstilosAprendizaje encuestaEstilosAprendizaje) {
        Query query = em.createNamedQuery("RespuestaPorEstilo.findByIdEncuestaEstilosAprendizaje");
        query.setParameter("idEncuestaEstilosAprendizaje", encuestaEstilosAprendizaje.getIdEncuesta());
        List<RespuestaPorEstilo> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista;
        }
        return null;
    }
}
