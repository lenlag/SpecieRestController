package formation.afpa.fr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import formation.afpa.fr.Exception.SpecieAlreadyExistsException;
import formation.afpa.fr.Exception.SpecieNotAvailableException;
import formation.afpa.fr.Exception.SpecieNotFoundException;
import formation.afpa.fr.Exception.SpecieNotValidException;

@RestController
@RequestMapping("/rest/species")
public class SpecieController {

	
	//restcontroller = API Rest
	
	@Autowired
	private ServiceSpecie service;

	@GetMapping("") // @RequestMapping("/rest/species")
	public List<Specie> list() throws SpecieNotAvailableException {
		List<Specie> list = service.findAll();
		return list;
	}

	@GetMapping("/{id}") // @RequestMapping("/rest/species") + id
	public Specie get(@PathVariable("id") Long id) throws SpecieNotFoundException, Exception {
		return service.findById(id);

	}

	@PostMapping("")
	public Long create(@RequestBody Specie s) throws SpecieNotValidException, SpecieAlreadyExistsException {
		service.create(s);

		return s.getId(); // return the id of the new created specie
	}

	@PutMapping("/{id}")
	public void update(@RequestBody Specie s, @PathVariable("id") Long id) throws Exception {
		try {
			service.findById(id); // we do not stock the result in variable s; use this method just to check if the object exists in the DB
		} catch (SpecieNotFoundException e) {
			e.printStackTrace();
		}
		service.update(s);

	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) throws SpecieNotFoundException {
		service.deleteById(id);
	}
}
