package com.MSP.shopydrop.Controller;

import com.MSP.shopydrop.Entity.Promocode;
import com.MSP.shopydrop.Service.PromocodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promocode")
public class PromocodeController {

    private final PromocodeService promocodeService;

    public PromocodeController(PromocodeService promocodeService) {
        this.promocodeService = promocodeService;
    }

    @PostMapping("/add")
    public ResponseEntity<Promocode> addPromocode(@RequestBody Promocode promocode){
        return ResponseEntity.ok().body(this.promocodeService.addPromocode(promocode));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Promocode> updatePromocode(@RequestBody Promocode promocode, @PathVariable Long id){
        promocodeService.updatePromocode(promocode,id);
        return ResponseEntity.ok().body(this.promocodeService.updatePromocode(promocode, id));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Promocode> getPromocodeById(@RequestBody Promocode promocode, @PathVariable Long id){
        return ResponseEntity.ok().body(this.promocodeService.getPromocodeById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Promocode>> getAllPromocode(){
        return ResponseEntity.ok().body(this.promocodeService.getAllPromocode());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deletePromocode(@PathVariable Long id){
        return ResponseEntity.ok().body(this.promocodeService.deletePromocode(id));
    }
}
