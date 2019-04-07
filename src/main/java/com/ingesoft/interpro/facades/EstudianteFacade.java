/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.Estudiante;
import com.ingesoft.interpro.entidades.Persona;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author debian
 */
@Stateless
public class EstudianteFacade extends AbstractFacade<Estudiante> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstudianteFacade() {
        super(Estudiante.class);
    }

    public Estudiante findPorPersona(Persona persona) {
        Query query = em.createNamedQuery("Estudiante.findPorPersona");
        query.setParameter("persona", persona);
        if (!query.getResultList().isEmpty()) {
            Estudiante estud = (Estudiante) query.getResultList().get(0);
            if (estud != null) {
                return estud;
            }
        }
        return null;
    }
}
