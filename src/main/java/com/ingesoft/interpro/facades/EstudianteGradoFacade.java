/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.EstudianteGrado;
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
public class EstudianteGradoFacade extends AbstractFacade<EstudianteGrado> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstudianteGradoFacade() {
        super(EstudianteGrado.class);
    }
    
    public List<EstudianteGrado> buscarPorIdEstudiante(Estudiante estudiante) {
        Query query = em.createNamedQuery("EstudianteGrado.findByEstudiante");
        query.setParameter("estudiante", estudiante);
        List<EstudianteGrado> lista = query.getResultList();
        if (lista != null) {
            return lista;
        }
        return null;
    }
}
