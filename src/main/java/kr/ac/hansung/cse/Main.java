package kr.ac.hansung.cse;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import kr.ac.hansung.cse.dao.CourseDao;
import kr.ac.hansung.cse.dao.InstructorDao;
import kr.ac.hansung.cse.entity.Course;
import kr.ac.hansung.cse.entity.Instructor;
import kr.ac.hansung.cse.entity.InstructorDetail;
/*

// One-to-Many Uni-directional
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        InstructorDao instructorDao = context.getBean(InstructorDao.class);
        CourseDao courseDao = context.getBean(CourseDao.class);

        // [1] Instructor 객체 생성
        Instructor instructor1 = new Instructor("Namyun Kim", "nykim@hansung.ac.kr");
        Instructor instructor2 = new Instructor("Jaemon Lee", "jmlee@hansung.ac.kr");

        // [2] Instructor 먼저 저장 (DB에 insert + id 생성)
        instructorDao.save(instructor1);
        instructorDao.save(instructor2);

        // [3] Course 객체 생성
        Course course1 = new Course("웹프레임워크");
        Course course2 = new Course("오픈소스소프트웨어");
        Course course3 = new Course("iOS 프로그래밍");
        Course course4 = new Course("안드로이드 프로그래밍");

        // [4] 각 Course에 Instructor 설정 (연관관계 주입)
        course1.setInstructor(instructor1);
        course2.setInstructor(instructor1);
        course3.setInstructor(instructor2);
        course4.setInstructor(instructor2);

        // [5] Course 저장 (instructor_id 포함된 상태로 INSERT)
        courseDao.save(course1);
        courseDao.save(course2);
        courseDao.save(course3);
        courseDao.save(course4);

        // 저장된 데이터 조회
        List<Instructor> instructors = instructorDao.findAll();
        List<Course> courses = courseDao.findAll();

        System.out.println("Instructors:");
        for (Instructor Instructor : instructors) {
            System.out.println(Instructor.getFullName());
        }

        System.out.println("\nCourses:");
        for (Course course : courses) {
            System.out.println( "Instructor: " + course.getInstructor().getFullName() +
                    ", Course: " + course.getTitle());
        }
    }
}
*/

/*
// One-to-Many Bidirectional
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        InstructorDao instructorDao = context.getBean(InstructorDao.class);
        CourseDao courseDao = context.getBean(CourseDao.class);

        Instructor instructor1 = new Instructor("Namyun Kim", "nykim@hansung.ac.kr");
        Course course1 = new Course("웹프레임워크");
        Course course2 = new Course("오픈소스소프트웨어");

        instructor1.addCourse(course1);
        instructor1.addCourse(course2);

        // cascade=CascadeType.ALL, fetch = FetchType.LAZY
        instructorDao.save(instructor1);

        // 저장된 Instructor 조회 및 결과 확인
        // Instructor retrievedInstructor = instructorDao.findById(instructor1.getId());
        Instructor retrievedInstructor = instructorDao.findByIdWithCourses(instructor1.getId());
        System.out.println("Instructor: " + retrievedInstructor.getFullName());

        for (Course Course : retrievedInstructor.getCourses()) {
            System.out.println("Course: " + Course.getTitle());
        }
    }
}*/

// One-To-One Unidirectional
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        InstructorDao instructorDao = context.getBean(InstructorDao.class);
        CourseDao courseDao = context.getBean(CourseDao.class);

        // [1] InstructorDetail 객체 생성
        InstructorDetail detail =
                new InstructorDetail("youtube.com/TheJavaChannel", "Coding");

        // [2] Instructor 객체 생성 및 연관관계 설정
        Instructor instructor =
                new Instructor("Namyun Kim", "nykim@hansung.ac.kr");
        instructor.setInstructorDetail(detail);  // 연관관계 설정

        // [3] Instructor 저장 (CascadeType.ALL 덕분에 InstructorDetail도 함께 저장됨)
        instructorDao.save(instructor);

        // [4] 저장된 Instructor 조회
        Instructor storedInstructor = instructorDao.findById(instructor.getId());
        System.out.println("Retrieved Instructor: " + storedInstructor.getFullName());

        // [5] InstructorDetail 출력 (단방향 관계에서 바로 접근 가능)
        InstructorDetail storedDetail = storedInstructor.getInstructorDetail();
        System.out.println("Instructor Detail:");
        System.out.println("YouTube Channel: " + storedDetail.getYoutubeChannel());
        System.out.println("Hobby: " + storedDetail.getHobby());

    }
}

/*
// Many-to-Many Unidirectional
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
            new ClassPathXmlApplicationContext("applicationContext.xml");

        InstructorDao instructorDao = context.getBean(InstructorDao.class);
        CourseDao courseDao = context.getBean(CourseDao.class);
        StudentDao studentDao = context.getBean(StudentDao.class);

        // [1] 수업(Course) 생성
        List<Course> courses = Arrays.asList(
            new Course("웹프레임워크"),
            new Course("오픈소스소프트웨어"),
            new Course("정보보안"),
            new Course("웹서버프로그래밍"),
            new Course("클라우드컴퓨팅")
        );

        // [2] 수업 저장
        courses.forEach(courseDao::save);

        // [3] 학생(Student) 생성 및 수강 과목 설정
        Student student1 = new Student("Alice", "alice@hansung.ac.kr");
        Student student2 = new Student("Bob", "bob@hansung.ac.kr");
        Student student3 = new Student("Charlie", "charlie@hansung.ac.kr");

        student1.setCourses(Arrays.asList(courses.get(0), courses.get(1)));
        student2.setCourses(Arrays.asList(courses.get(1), courses.get(2), courses.get(3))); student3.setCourses(Arrays.asList(courses.get(2), courses.get(3), courses.get(4)));

        // [4] 학생 저장
        List<Student> students = Arrays.asList(student1, student2, student3);
        students.forEach(studentDao::save);

        // [5] 저장된 학생 및 수강 과목 조회 및 출력
        Student storedStudent = studentDao.findByIdWithCourses(student1.getId());

        System.out.println(" Retrieved Student: " + storedStudent.getFullName());
        storedStudent.getCourses().forEach(
            course -> System.out.println("   ➤ Enrolled in: " + course.getTitle())
        );
    }
}*/
