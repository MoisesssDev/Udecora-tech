package com.spring.backend.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.backend.dto.ProductDTO;
import com.spring.backend.services.ProductService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@GetMapping
	@Transactional
	public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,

			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,

			@RequestParam(value = "direction", defaultValue = "DESC") String direction,
			
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy

	) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<ProductDTO> list = productService.findAllPaged(pageRequest);

		return ResponseEntity.ok(list);
	}

	@GetMapping("/{id}")
	@Transactional
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(productService.findById(id));
	}

	@PostMapping
	public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO dto) {
		dto = productService.save(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
		dto = productService.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		productService.delete(id);

		return ResponseEntity.noContent().build();
	}
}
