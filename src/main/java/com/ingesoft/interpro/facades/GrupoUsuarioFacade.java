/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.entidades.GrupoUsuario;
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
public class GrupoUsuarioFacade extends AbstractFacade<GrupoUsuario> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
       
    public List<GrupoUsuario> getGruposUsuario(Usuario idUsuario){
        Query query = em.createNamedQuery("GrupoUsuario.findPorUsuario");
        query.setParameter("usuario", idUsuario);
        List<GrupoUsuario> grupos = query.getResultList();
        if (grupos != null && !grupos.isEmpty()) {
            return grupos;
        }
        return null;
    }
    
    public GrupoUsuarioFacade() {
        super(GrupoUsuario.class);
    }
    
}
