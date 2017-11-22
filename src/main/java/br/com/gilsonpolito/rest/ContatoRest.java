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

    private final Map<Integer, Contato> contatos = new HashMap<>();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Contato>> findAll() {
        return new ResponseEntity<>(new ArrayList<>(contatos.values()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Contato> findById(@PathVariable("id") Integer id) {
        Contato contato = contatos.get(id);
        if (contato == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(contato, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Contato> create(@RequestBody Contato contato) {
        contato.setId(contato.getGerador());
        contatos.put(contato.getId(), contato);
        return new ResponseEntity<>(contato, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Contato> update(@RequestBody Contato contato) {
        contatos.put(contato.getId(), contato);
        return new ResponseEntity<>(contato, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Contato contato = contatos.remove(id);

        if (contato == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
