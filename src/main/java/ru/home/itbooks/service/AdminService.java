package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class AdminService {
    private EntityManager em;

    @Autowired
    public AdminService(EntityManager em) {
        this.em = em;
    }

    public void checkpoint() {
        //em.createStoredProcedureQuery("CHECKPOINT").execute();
        em.createStoredProcedureQuery("SYSTEM_LOBS.MERGE_EMPTY_BLOCKS").execute();
    }
}
