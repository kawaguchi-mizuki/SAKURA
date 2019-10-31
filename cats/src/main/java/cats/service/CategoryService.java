package cats.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.dto.CategoryDto;
import cats.entity.CategoryTblEntity;
import cats.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	/**
	 * カテゴリ一覧を取得する
	 * @return
	 */
	public List<CategoryDto> getAllList(){

		List<CategoryDto> list = new ArrayList<CategoryDto>();

		List<CategoryTblEntity> categoryList = categoryRepository.findAll();

		//entity -> DTO
				for( CategoryTblEntity entity : categoryList ) {
					CategoryDto dto = new CategoryDto();

					dto.setCategoryId(entity.getcategoryId());
					dto.setCategoryName(entity.getcategoryName());

					list.add(dto);
				}

		return list;
	}


}
