package hu.bme.aut.retelab2.controller;

import hu.bme.aut.retelab2.domain.Ad;
import hu.bme.aut.retelab2.repository.AdRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
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
        List<Ad> results = adRepository.findByPrice(minPrice, maxPrice);
        for (Ad r:results) {
            r.setSecret(null);
        }
        return results;
    }

    @PutMapping
    public ResponseEntity modify(@RequestBody Ad ad){
        ResponseEntity response = null;
        try {
            adRepository.modify(ad);
            response = new ResponseEntity<>(HttpStatus.OK);
        } catch (AuthenticationException ae) {
            response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } finally {
            return response;
        }
    }

    @ResponseStatus(code = HttpStatus.OK)
    private void okResponse() {}

    @ResponseStatus(HttpStatus.FORBIDDEN)
    private void forbiddenResponse() {}

}
