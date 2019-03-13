/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.interpro.facades;

import com.ingesoft.interpro.controladores.TipoUsuarioController;
import com.ingesoft.interpro.controladores.UsuarioController;
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
public class UsuarioFacade extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public boolean esEstudiante(int idUsuario) {
        return esTipoUsuario(idUsuario, UsuarioController.TIPO_ESTUDIANTE);
    }
    
    public boolean esTipoUsuario(int idUsuario, String tipo) {
        Query query = em.createNamedQuery("Usuario.esTipoUsuario");
        query.setParameter("idUsuario", idUsuario);
        query.setParameter("tipo", tipo);
        List<Usuario> usuarios = query.getResultList();
        if (usuarios != null && !usuarios.isEmpty()) {
            return true;
        }
        return false;
    }
    public Usuario buscarPorUsuario(String name) {
        Query query = em.createNamedQuery("Usuario.findByUsuario");
        query.setParameter("usuario", name);
        Usuario findUsuario = (Usuario) query.getResultList().get(0);
        if (findUsuario != null) {
            return findUsuario;
        }
        return null;

    }

    public Usuario obtUsuarioPorToken(String token) {
        Query query = em.createNamedQuery("Usuario.findByTokenAcesso");
        query.setParameter("tokenAcesso", token);
        List<Usuario> usuarios = query.getResultList();
        if (usuarios != null && !usuarios.isEmpty()) {
            Usuario findUsuario = (Usuario) usuarios.get(0);
            if (findUsuario != null) {
                return findUsuario;
            }
        }
        return null;

    }
}
