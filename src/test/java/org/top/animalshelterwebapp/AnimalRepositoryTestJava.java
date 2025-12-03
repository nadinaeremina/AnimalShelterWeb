package org.top.animalshelterwebapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.bind.annotation.RestController;
import org.top.animalshelterwebapp.animal.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AnimalRepositoryTestJava {

	@Autowired
	private final WorkersRepository repo;

	public AnimalRepositoryTestJava(WorkersRepository repo) {
		this.repo = repo;
	}

//	@Test
//	public void testSelectPageWithFilterByCity() {
//		int PageSize = 5;
//		String cityTitle = "Москва";
//		CriteriaData criteriaData = new CriteriaData("city", Operation.GT, String.valueOf(cityTitle));
//		PageRequest pageRequest = PageRequest.of(0, PageSize);
//		Page<Animal> entities = repo.findAll(new WorkerSpecification(criteriaData), pageRequest);
//		assertNotNull(entities);
//		assertEquals(20, entities.stream().count()); // будет только одна запись
//		List<Animal> content = entities.getContent();
//		assertTrue(content.get(0).getCityTitle() == cityTitle);
//	}
}
