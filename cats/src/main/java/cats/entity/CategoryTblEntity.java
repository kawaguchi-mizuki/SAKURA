package cats.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="category")
public class CategoryTblEntity {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** CATEGORY_ID. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;

	/** CATEGORY_NAME. */
	private String categoryName;

	/**
	 * CATEGORY_ID を設定します.
	 *
	 * @paramcategoryId
	 *            CATEGORY_ID
	 */
	public void setcategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * CATEGORY_ID を取得します.
	 *
	 * @return CATEGORY_ID
	 */
	public Integer getcategoryId() {
		return this.categoryId;
	}

	/**
	 * CATEGORY_NAME を設定します.
	 *
	 * @paramcategoryName
	 *            CATEGORY_NAME
	 */
	public void setcategoryName(String categoryName) {
		this.categoryName =categoryName;
	}

	/**
	 * CATEGORY_NAME を取得します.
	 *
	 * @return CATEGORY_NAME
	 */
	public String getcategoryName() {
		return this.categoryName;
	}


}
