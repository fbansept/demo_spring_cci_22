package edu.fbansept.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.demo.dao.UtilisateurDao;
import edu.fbansept.demo.model.Utilisateur;
import edu.fbansept.demo.view.VueUtilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UtilisateurController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @GetMapping("/utilisateur/{id}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable int id) {

        Optional<Utilisateur> utilisateurExistant = utilisateurDao.findById(id);

        if(utilisateurExistant.isPresent()) {
            return new ResponseEntity<>(utilisateurExistant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/liste-utilisateur")
    @JsonView(VueUtilisateur.class)
    public List<Utilisateur> getUtilisateurs() {

        return utilisateurDao.findAll();
    }

    @PostMapping("/utilisateur")
    public ResponseEntity<Utilisateur> ajoutUtilisateur(@RequestBody Utilisateur utilisateur){

        //si l'utilisateur à un identifiant
        if(utilisateur.getId() != null) {
            Optional<Utilisateur> utilisateurExistant =
                    utilisateurDao.findById(utilisateur.getId());

            //l'utilisateur à fournit un id existant dans la BDD (c'est un update)
            if(utilisateurExistant.isPresent()) {
                utilisateurDao.save(utilisateur);
                return new ResponseEntity<>(utilisateur,HttpStatus.OK);
            } else {
                //l'utilisateur à fournit un id qui n'existe pas dans la BDD (c'est une erreur)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            utilisateurDao.save(utilisateur);
            return new ResponseEntity<>(utilisateur,HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/utilisateur/{id}")
    public ResponseEntity<Utilisateur> supprimeUtilisateur(@PathVariable int id) {

        Optional<Utilisateur> utilisateurExistant = utilisateurDao.findById(id);

        if(utilisateurExistant.isPresent()) {
            utilisateurDao.deleteById(id);
            return new ResponseEntity<>(utilisateurExistant.get(),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}