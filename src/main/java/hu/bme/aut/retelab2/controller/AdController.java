package hu.bme.aut.retelab2.controller;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ads")
public class AdController {
    @Autowired
    private AdRepository adRepository;

    @PostMapping
    public Ad create(@RequestBody Ad ad){
        ad.setId(null);
        ad.setCreatedAt(new Date());
        return adRepository.save(ad);
    }

    @GetMapping
    public List<Ad> findByPrice(@RequestParam(defaultValue = "0") int minPrice, @RequestParam(defaultValue = "10000000") int maxPrice){
        return adRepository.findByPrice(minPrice, maxPrice);
    }

}
