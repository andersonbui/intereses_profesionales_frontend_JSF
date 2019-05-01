/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.Encuesta;
import com.ingesoft.interpro.entidades.Estudiante;
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
public class EncuestaFacade extends AbstractFacade<Encuesta> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EncuestaFacade() {
        super(Encuesta.class);
    }

    public Integer autogenerarIdEncuesta() {
        Query query = em.createNamedQuery("Encuesta.maxIdEncuesta");
        Integer estud = (Integer) query.getSingleResult();
        System.out.println("maxIdEncuesta: " + estud);
        if (estud != null) {
            return estud + 1;
        }
        return null;
    }

    public List<Encuesta> buscarPorEstudiante(Estudiante estudiante) {
        Query query = em.createNamedQuery("Encuesta.findByEstudiante");
        query.setParameter("estudiante", estudiante);
        List<Encuesta> lista = query.getResultList();
        if (!lista.isEmpty()) {
            return lista;
        }
        return null;
    }

    public Encuesta encuestaSinTerminar(Estudiante estudiante) {

        List<Encuesta> lista = buscarPorEstudiante(estudiante);
        if (!lista.isEmpty()) {
            for (Encuesta encuesta : lista) {
               //encuesta.getEstado() == null || 
                if ("SINTERMINAR".equals(encuesta.getEstado())) {
                    return encuesta;
                }
            }
        }
        return null;
    }
}
