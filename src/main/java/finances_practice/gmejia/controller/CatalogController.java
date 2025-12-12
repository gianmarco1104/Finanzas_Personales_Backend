package finances_practice.gmejia.controller;

import finances_practice.gmejia.dto.response.CountriesResponse;
import finances_practice.gmejia.dto.response.CatalogResponse;
import finances_practice.gmejia.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalogs")
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;

    @GetMapping("/countries")
    public ResponseEntity<List<CountriesResponse>> getCountries(){return ResponseEntity.ok(catalogService.getCountries());}

    @GetMapping("/genders")
    public ResponseEntity<List<CatalogResponse>> getGenders(){
        return ResponseEntity.ok(catalogService.getGenders());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<CatalogResponse>> getRoles(){
        return ResponseEntity.ok(catalogService.getRoles());
    }

    @GetMapping("/paymentMethods")
    public ResponseEntity<List<CatalogResponse>> getPaymentMethods(){return ResponseEntity.ok(catalogService.getPaymentMethods());}

    @GetMapping("/categories")
    public ResponseEntity<List<CatalogResponse>> getCategories(){return ResponseEntity.ok(catalogService.getCategories());}

    @GetMapping("/transactionType")
    public ResponseEntity<List<CatalogResponse>> getTransactionType(){return ResponseEntity.ok(catalogService.getTransactionTypes());}
}
