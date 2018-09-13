package com.akin.todolist;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ListItemController {

	private final ListItemRepository repository;

	ListItemController(ListItemRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping("/items")
	Resources<Resource<ListItem>> all() {

		List<Resource<ListItem>> items = repository.findAll().stream()
			.map(item -> new Resource<>(item,
				linkTo(methodOn(ListItemController.class).one(item.getId())).withSelfRel(),
				linkTo(methodOn(ListItemController.class).all()).withRel("items")))
			.collect(Collectors.toList());
		
		return new Resources<>(items,
			linkTo(methodOn(ListItemController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]

	@PostMapping("/items")
	ListItem newItem(@RequestBody ListItem newItem) {
		return repository.save(newItem);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping("/items/{id}")
	Resource<ListItem> one(@PathVariable Long id) {
		
		ListItem item = repository.findById(id)
			.orElseThrow(() -> new ItemNotFoundException(id));
		
		return new Resource<>(item,
			linkTo(methodOn(ListItemController.class).one(id)).withSelfRel(),
			linkTo(methodOn(ListItemController.class).all()).withRel("items"));
	}
	// end::get-single-item[]

	@PutMapping("/items/{id}")
	ListItem replaceItem(@RequestBody ListItem newItem, @PathVariable Long id) {
		
		return repository.findById(id)
			.map(item -> {
				item.setName(newItem.getName());
				return repository.save(item);
			})
			.orElseGet(() -> {
				newItem.setId(id);
				return repository.save(newItem);
			});
	}

	@DeleteMapping("/items/{id}")
	void deleteItem(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
