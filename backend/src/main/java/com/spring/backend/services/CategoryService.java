package com.spring.backend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.backend.dto.CategoryDTO;
import com.spring.backend.entities.Category;
import com.spring.backend.repositories.CategoryRepository;
import com.spring.backend.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<CategoryDTO> findAll(){
		List<Category> list = categoryRepository.findAll();
		
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}
	
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Category Not Found!!!"));
		
		return new CategoryDTO(entity);
	}

	public CategoryDTO save(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = categoryRepository.save(entity);
		
		return new CategoryDTO(entity);
	}
}
