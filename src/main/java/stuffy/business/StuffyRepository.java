package stuffy.business;

import org.springframework.data.repository.CrudRepository;



public interface StuffyRepository extends CrudRepository<Stuffy, Integer> {
	Stuffy findByColor(String color);

}
