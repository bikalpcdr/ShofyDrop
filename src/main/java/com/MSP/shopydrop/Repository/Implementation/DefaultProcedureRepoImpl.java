package com.MSP.shopydrop.Repository.Implementation;

import com.MSP.shopydrop.Repository.DefaultProcedureRepo;
import jakarta.persistence.*;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class DefaultProcedureRepoImpl implements DefaultProcedureRepo {

    @PersistenceContext
    private final EntityManager entityManager;

    @Qualifier("entityManagerFactory")
    private final EntityManagerFactory entityManagerFactory;

    public DefaultProcedureRepoImpl(EntityManager entityManager, EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManager;
        this.entityManagerFactory = entityManagerFactory;
    }

    private <T> StoredProcedureQuery defineAndCreateProcedureCall(String pname, Object[][] params, Class<T> type)
    {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(pname, type);

        storedProcedure.registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
        for (int i = 0; i < params.length; i++)
        {
            Object[] param = params[i];
            storedProcedure.registerStoredProcedureParameter(i+2, (Class) param[0], ParameterMode.IN);
            storedProcedure.setParameter(i+2, param[1]);
        }
        return storedProcedure;
    }

    private StoredProcedureQuery defineAndCreateProcedureCall(String pname, Object[][] params)
    {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(pname);

        for (int i = 0; i < params.length; i++)
        {
            Object[] param = params[i];
            storedProcedure.registerStoredProcedureParameter(i+1, (Class) param[0], ParameterMode.IN);
            storedProcedure.setParameter(i+1, param[1]);
        }
        return storedProcedure;
    }

    private<T> List<T> executeProcedureCallForList(StoredProcedureQuery storedProcedure)
    {
        try
        {
            storedProcedure.execute();
            return storedProcedure.getResultList();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                storedProcedure.unwrap(ProcedureOutputs.class).release();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    private Object[] executeProcedureCallForArray(StoredProcedureQuery storedProcedure)
    {
        try
        {
            storedProcedure.execute();
            return storedProcedure.getResultList().toArray();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                storedProcedure.unwrap(ProcedureOutputs.class).release();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public <T> List<T> getWithType(String pname, Object[][] params, Class<T> type) {
        List<T> list = executeProcedureCallForList(defineAndCreateProcedureCall(pname,params,type));
        entityManager.flush();
        entityManager.clear();
        return list;
    }

    @Override
    public Object[] executeWithType(String pname, Object[][] params) {
        Object[] objects = executeProcedureCallForArray(defineAndCreateProcedureCall(pname,params));
        entityManager.flush();
        entityManager.clear();
        return objects;
    }
}
