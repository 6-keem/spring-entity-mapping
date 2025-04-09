package kr.ac.hansung.cse.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.ac.hansung.cse.entity.Course;
import kr.ac.hansung.cse.entity.Instructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class InstructorDao {
	@PersistenceContext
	private EntityManager entityManager;

	public void save(Instructor instructor) {
		entityManager.persist(instructor);
	}

	public Instructor findById(Long id) {
		return entityManager.find(Instructor.class, id);
	}

	public List<Instructor> findAll() {
		return entityManager.createQuery("SELECT i FROM Instructor i", Instructor.class)
			.getResultList();
	}

	public Instructor findByIdWithCourses(Long id) {
		Instructor instructor = entityManager.find(Instructor.class, id);
		// 강제로 초기화하기 위해 컬렉션에 접근
		if (instructor != null) {
			/**
			 *
			 * FetchType.EAGER로 설정하면 이러한 방식으로 조회를 하지 않아도 되지만
			 * EAGER로 생성해두면 OneToMany 관계에서 매번 Many를 조회하고 가져오기 때문에 성능 저하 야기
			 *
			 * 따라서 수동으로 조회되도록 하는 메서드를 추가하여 우회하는 듯?
			 */
			instructor.getCourses().size(); // 컬렉션 초기화
		}
		return instructor;
	}
}