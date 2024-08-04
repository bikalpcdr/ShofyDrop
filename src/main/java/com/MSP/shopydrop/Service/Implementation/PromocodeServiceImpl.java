package com.MSP.shopydrop.Service.Implementation;

import com.MSP.shopydrop.Entity.Promocode;
import com.MSP.shopydrop.Exception.ResourceNotFoundException;
import com.MSP.shopydrop.Service.PromocodeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PromocodeServiceImpl implements PromocodeService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Promocode addPromocode(Promocode promocode) {
        entityManager.persist(promocode);
        return promocode;
    }

    @Override
    @Transactional
    public Promocode updatePromocode(Promocode promocode, Long id) {
        Promocode existingPromocode = entityManager.find(Promocode.class, id);

        if (existingPromocode == null) {
            throw new ResourceNotFoundException("Promocode", "Id", id);
        }

        existingPromocode.setCode(promocode.getCode());
        existingPromocode.setDiscount(promocode.getDiscount());
        existingPromocode.setStartDate(promocode.getStartDate());
        existingPromocode.setCreatedAt(promocode.getCreatedAt());
        existingPromocode.setUpdatedAt(promocode.getUpdatedAt());
        existingPromocode.setReward(promocode.getReward());

        return entityManager.merge(existingPromocode);
    }

    @Override
    public Promocode getPromocodeById(Long id) {
        Promocode promocode = entityManager.find(Promocode.class, id);
        if (promocode == null) {
            throw new ResourceNotFoundException("Promocode", "Id", id);
        }
        return promocode;
    }

    @Override
    public List<Promocode> getAllPromocode() {
        TypedQuery<Promocode> query = entityManager.createQuery("SELECT p FROM Promocode p", Promocode.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public boolean deletePromocode(Long id) {
        Promocode promocode = entityManager.find(Promocode.class, id);
        if (promocode != null) {
            entityManager.remove(promocode);
            return true;
        } else {
            return false;
        }
    }
}
