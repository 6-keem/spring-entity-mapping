package kr.ac.hansung.cse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.ac.hansung.cse.entity.Course;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CourseDao {
	@PersistenceContext
	private EntityManager entityManager;

	public void save(Course course) {
		entityManager.persist(course);
	}

	public Course findById(Long id) {
		return entityManager.find(Course.class, id);
	}

	public List<Course> findAll() {
		return entityManager.createQuery("SELECT c FROM Course c", Course.class)
			.getResultList();
	}
}