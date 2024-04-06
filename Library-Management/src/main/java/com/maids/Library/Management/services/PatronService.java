package com.maids.Library.Management.services;

import com.maids.Library.Management.entites.Patron;

import java.util.List;
import java.util.Optional;

public interface PatronService {

    Patron create(Patron patron);

    Optional<Patron> findById(Long id);

    List<Patron> getPatrons();

    void deletePatron(Long id);

    boolean isPatronExist(Long id);



}
