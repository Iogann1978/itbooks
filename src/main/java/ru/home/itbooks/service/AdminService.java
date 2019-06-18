package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;

@Service
public class AdminService {
    private EntityManager em;

    @Autowired
    public AdminService(EntityManager em) {
        this.em = em;
    }

    public void vacuumLob() {
        em.createStoredProcedureQuery("SYSTEM_LOBS.DELETE_UNUSED_LOBS")
                .registerStoredProcedureParameter(0, Long.class, ParameterMode.IN)
                .setParameter(0, 9223372036854775807L)
                .execute();
        em.createStoredProcedureQuery("SYSTEM_LOBS.MERGE_EMPTY_BLOCKS")
                .execute();
    }
}
