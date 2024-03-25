package com.root.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.root.entity.Notes;
import com.root.entity.User;

public interface NoteService {

	public Notes saveNotes(Notes notes);

	public Notes getNotesById(int id);

	// public List<Notes> getNotesByUser(User user, int pageNo);
	public Page<Notes> getNotesByUser(User user, int pageNo);

	public Notes updateNotes(Notes notes);

	public boolean deleteNotes(int id);
}
