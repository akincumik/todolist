package com.akin.todolist;

class ItemNotFoundException extends RuntimeException {

	ItemNotFoundException(Long id) {
		super("Could not find item " + id);
	}
}
