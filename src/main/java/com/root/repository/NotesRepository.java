package com.root.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.root.entity.Notes;
import com.root.entity.User;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

	public Page<Notes> findByUser(User user,Pageable pageable);
}
