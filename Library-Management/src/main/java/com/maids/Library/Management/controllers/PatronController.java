package com.maids.Library.Management.controllers;



import com.maids.Library.Management.entites.Patron;
import com.maids.Library.Management.services.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class PatronController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }
    private final PatronService patronService;

    @Autowired
    public PatronController(final PatronService patronService) {
        this.patronService = patronService;
    }

    @PostMapping(path = "api/patrons/")
    public ResponseEntity<Patron> createPatron(@RequestBody @Valid final Patron patron){


        final ResponseEntity<Patron> response = new ResponseEntity<Patron>(patronService.create(patron), HttpStatus.CREATED);
        return response;

    }

    @GetMapping(path = "api/patrons/{id}")
    public ResponseEntity<Patron> retrievePatron(@PathVariable final Long id){
        final Optional<Patron> foundPatron = patronService.findById(id);
        return foundPatron.map(patron -> new ResponseEntity<Patron>(patron, HttpStatus.OK))
                .orElse(new ResponseEntity<Patron>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "api/patrons")
    public ResponseEntity<List<Patron>> listPatrons(){
        final List<Patron> foundPatrons = patronService.getPatrons();

        return new ResponseEntity<List<Patron>>(foundPatrons,HttpStatus.OK);
    }

    @PutMapping(path = "api/patrons/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable final Long id,@RequestBody @Valid final Patron patron) {
        if (patronService.isPatronExist(id)) {

            return new ResponseEntity<Patron>(patronService.create(patron), HttpStatus.OK);
        } else {
            return new ResponseEntity<Patron>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(path = "api/patrons/{id}")
    public ResponseEntity deletePatron(@PathVariable final Long id){
        patronService.deletePatron(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }
}
