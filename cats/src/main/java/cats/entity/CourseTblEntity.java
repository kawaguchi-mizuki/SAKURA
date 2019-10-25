package cats.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="course")
public class CourseTblEntity {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** COURSE_ID. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer courseId;

	/** SCHOOL_ID. */
	private Integer schoolId;

	/** COURSE_NAME. */
	private String courseName;




	/**COURSE_IDを取得します
	 * @return
	 */
	public Integer getcourseId() {
		return courseId;
	}

	/**COURSE_IDを設定します
	 * @param courseId
	 */
	public void setcourseId(Integer courseId) {
		this.courseId = courseId;
	}

	/**SCHOOL_IDを取得します
	 * @return
	 */
	public Integer getschoolId() {
		return schoolId;
	}

	/**SCHOOL_IDを設定します
	 * @param schoolId
	 */
	public void setschoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	/**COURSE_NAMEを取得します
	 * @return
	 */
	public String getcourseName() {
		return courseName;
	}

	/**COURSE_NAMEを設定します
	 * @param courseName
	 */
	public void setcourseName(String courseName) {
		this.courseName = courseName;
	}




}
