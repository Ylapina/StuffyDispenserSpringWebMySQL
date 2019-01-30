package stuffy.web;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import stuffy.business.Stuffy;
import stuffy.business.StuffyRepository;
import stuffy.util.JsonResponse;

@Controller
@RequestMapping(path = "/stuffies")

public class StuffyController {
	@Autowired
	private StuffyRepository stuffyRepo;

	@GetMapping(path = "/")
	public @ResponseBody JsonResponse getAllStuffies() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(stuffyRepo.findAll());

		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	@GetMapping("/{id}")
	public @ResponseBody JsonResponse getStuffy(@PathVariable int id) {
		JsonResponse jr=null;
		try {
			Optional<Stuffy> s = stuffyRepo.findById(id);
			if(s.isPresent()) {
				//good call to DB return a valid user
				jr=JsonResponse.getInstance(s);
			}
			else {
				jr=JsonResponse.getInstance(
						new Exception("No stuffy found for id= "+id));
			}
				
			}
			catch(Exception e) {
				jr = JsonResponse.getInstance(e);
		}
			return jr;
		}
	@PostMapping(path = "/")
	public @ResponseBody JsonResponse addNewStuffy(@RequestBody Stuffy s) {
		
		    return saveStuffy(s);
	}
	//update a Stuffy
		@PutMapping("/{id}")
		public @ResponseBody JsonResponse updateStuffy(@PathVariable int id, @RequestBody Stuffy s) {
			
			return JsonResponse.getInstance(stuffyRepo.save(s));
			
			
		}
		private @ResponseBody JsonResponse saveStuffy(Stuffy s) {
			JsonResponse jr = null;
			try {
				stuffyRepo.save(s);
				JsonResponse.getInstance(s);
				
			}
			catch (DataIntegrityViolationException dve) {
				//jr = JsonResponse.getInstance(dve);
				
				jr=JsonResponse.getInstance(dve);
				
			}
			return jr;
		}
		@DeleteMapping("/{id}")
		public @ResponseBody JsonResponse removeStuffy(@PathVariable int id) {
			JsonResponse jr = null;
			Optional<Stuffy> s = stuffyRepo.findById(id);
			
			if(s.isPresent()) {
				stuffyRepo.deleteById(id);
			jr=JsonResponse.getInstance(s);
		}
		else  {
		jr =JsonResponse.getInstance( "delete unsuccessful, no stuffy id= " + id);
		}

			return jr; 
			
		}
		
		@PostMapping("/getByColor")
		public @ResponseBody JsonResponse getStuffyByColor(@RequestBody Stuffy stuffy) {
			return JsonResponse.getInstance(stuffyRepo.findByColor(stuffy.getColor()));
			
		}

}
