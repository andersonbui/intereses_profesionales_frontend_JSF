/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingesoft.suideal.encuesta.inteligencias_multiples.facades;

import com.ingesoft.suideal.encuesta.inteligencias_multiples.entidades.RespuestaPorInteligencia;
import com.ingesoft.interpro.facades.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author debian
 */
@Stateless
public class RespuestaPorInteligenciaFacade extends AbstractFacade<RespuestaPorInteligencia> {

    @PersistenceContext(unitName = "com.ingeniosoft_intereses_profesionales_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RespuestaPorInteligenciaFacade() {
        super(RespuestaPorInteligencia.class);
    }
    
}
