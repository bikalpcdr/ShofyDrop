package com.MSP.shopydrop.Service;

import com.MSP.shopydrop.Entity.Promocode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PromocodeService {

    Promocode addPromocode(Promocode promocode);

    Promocode updatePromocode(Promocode promocode, Long id);

    Promocode getPromocodeById(Long id);

    List<Promocode> getAllPromocode();

    boolean deletePromocode(Long id);
}
