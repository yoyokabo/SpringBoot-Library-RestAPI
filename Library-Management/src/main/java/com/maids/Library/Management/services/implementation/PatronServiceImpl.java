package com.maids.Library.Management.services.implementation;

import com.maids.Library.Management.entites.Patron;
import com.maids.Library.Management.entites.PatronEntity;
import com.maids.Library.Management.repositories.PatronRepo;
import com.maids.Library.Management.services.PatronService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class PatronServiceImpl implements PatronService {

    private Patron patronEntityToPatron(PatronEntity patronEntity){
        return Patron.builder()
                .id(patronEntity.getId())
                .name(patronEntity.getName())
                .address(patronEntity.getAddress())
                .phonenumber(patronEntity.getPhonenumber())
                .build();


    }

    private PatronEntity patronToPatronEntity(Patron patron){
        return PatronEntity.builder()
                .id(patron.getId())
                .name(patron.getName())
                .address(patron.getAddress())
                .phonenumber(patron.getPhonenumber())
                .build();


    }

    private final PatronRepo patronRepo;

    @Autowired
    public PatronServiceImpl(final PatronRepo patronRepo) {
        this.patronRepo = patronRepo;
    }


    @Override
    public boolean isPatronExist(Long id) {
        return patronRepo.existsById(id);
    }


    @Override
    public Patron create(Patron patron) {
        final PatronEntity patronEntity = patronToPatronEntity(patron);
        final PatronEntity savedPatron = patronRepo.save(patronEntity);
        return patronEntityToPatron(savedPatron);
    }


    @Override
    public Optional<Patron> findById(Long id) {
        Optional<PatronEntity> foundPatrons = patronRepo.findById(id);
        return foundPatrons.map(patron -> patronEntityToPatron(patron));
    }


    @Override
    public List<Patron> getPatrons() {
        List<PatronEntity> foundPatrons = patronRepo.findAll();
        return foundPatrons.stream().map(patron -> patronEntityToPatron(patron)).collect(Collectors.toList());
    }


    @Override
    public void deletePatron(Long id) {
        try {
            patronRepo.deleteById(id);
        }catch (final EmptyResultDataAccessException ex){
            log.debug("Deletion in patrons where patron doesn't exist");
        }


    }


}
