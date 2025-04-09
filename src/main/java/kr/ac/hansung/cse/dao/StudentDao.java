package kr.ac.hansung.cse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.ac.hansung.cse.entity.Student;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class StudentDao {
	@PersistenceContext
	private EntityManager entityManager;

	public void save(Student student ) {
		entityManager.persist(student);
	}

	public Student findById(Long id) {
		return entityManager.find(Student.class, id);
	}

	public List<Student> findAll() {
		return entityManager.createQuery("SELECT c FROM Student c", Student.class).getResultList();
	}

	public Student findByIdWithCourses(Long id) {
		Student student = entityManager.find(Student.class, id);
		// 강제로 초기화하기 위해 컬렉션에 접근
		if (student != null) {
			student.getCourses().size(); // 컬렉션 초기화
		}
		return student;
	}
}