package com.akin.todolist;

import org.springframework.data.jpa.repository.JpaRepository;

interface ListItemRepository extends JpaRepository<ListItem, Long> {

}
