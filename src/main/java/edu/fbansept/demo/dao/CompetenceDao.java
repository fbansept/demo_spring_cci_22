package edu.fbansept.demo.dao;

import edu.fbansept.demo.model.Competence;
import edu.fbansept.demo.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenceDao extends JpaRepository<Competence, Integer> {



}
