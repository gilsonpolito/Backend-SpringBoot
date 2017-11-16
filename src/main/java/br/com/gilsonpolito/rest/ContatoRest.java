package br.com.gilsonpolito.rest;

import br.com.gilsonpolito.bean.Contato;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/contatos")
public class ContatoRest {

    private final Map<Integer, Contato> cursos = new HashMap<>();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Contato>> findAll() {
        return new ResponseEntity<>(new ArrayList<>(cursos.values()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Contato> findById(@PathVariable("id") Integer id) {
        Contato curso = cursos.get(id);
        if (curso == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(curso, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Contato> create(@RequestBody Contato curso) {
        curso.setId(curso.getGerador());
        cursos.put(curso.getId(), curso);
        return new ResponseEntity<>(curso, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Contato> update(@RequestBody Contato curso) {
        cursos.put(curso.getId(), curso);
        return new ResponseEntity<>(curso, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Contato curso = cursos.remove(id);

        if (curso == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
