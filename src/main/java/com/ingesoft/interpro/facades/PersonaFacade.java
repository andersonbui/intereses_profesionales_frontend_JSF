/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.Persona;
import com.ingesoft.interpro.entidades.Usuario;
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
public class PersonaFacade extends AbstractFacade<Persona> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaFacade() {
        super(Persona.class);
    }
       
    public Persona findPorIdUsuario(Usuario idUsuario){
        Query query = em.createNamedQuery("Persona.findPorIdUsuario");
        query.setParameter("idUsuario", idUsuario);
        List<Persona> personas = query.getResultList();
        if (personas != null && !personas.isEmpty()) {
            return personas.get(0);
        }
        return null;
    }
    
    public List findAllByInstitucion(Integer idInstitucion){
        Query query = em.createNamedQuery("Pregunta.findPoIdUsuario");
        query.setParameter("idUsuario", idInstitucion);
        List<Persona> estud = query.getResultList();
        if (estud != null) {
            return estud;
        }
        return null;
    }
    
}
