package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PersonService;
import com.mycompany.myapp.domain.Person;
import com.mycompany.myapp.repository.PersonRepository;
import com.mycompany.myapp.service.dto.PersonDTO;
import com.mycompany.myapp.service.mapper.PersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Person.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService{

    private final Logger log = LoggerFactory.getLogger(PersonServiceImpl.class);
    
    @Inject
    private PersonRepository personRepository;

    @Inject
    private PersonMapper personMapper;

    /**
     * Save a person.
     *
     * @param personDTO the entity to save
     * @return the persisted entity
     */
    public PersonDTO save(PersonDTO personDTO) {
        log.debug("Request to save Person : {}", personDTO);
        Person person = personMapper.personDTOToPerson(personDTO);
        person = personRepository.save(person);
        PersonDTO result = personMapper.personToPersonDTO(person);
        return result;
    }

    /**
     *  Get all the people.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PersonDTO> findAll() {
        log.debug("Request to get all People");
        List<PersonDTO> result = personRepository.findAllWithEagerRelationships().stream()
            .map(personMapper::personToPersonDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one person by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public PersonDTO findOne(Long id) {
        log.debug("Request to get Person : {}", id);
        Person person = personRepository.findOneWithEagerRelationships(id);
        PersonDTO personDTO = personMapper.personToPersonDTO(person);
        return personDTO;
    }

    /**
     *  Delete the  person by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Person : {}", id);
        personRepository.delete(id);
    }
}
