package com.root.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.root.entity.Notes;
import com.root.entity.User;
import com.root.repository.UserRepo;
import com.root.service.NoteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private NoteService noteService;

	@ModelAttribute
	public User getUser(Principal p, Model m) {

		String email = p.getName();
		User user = userRepo.findByEmail(email);

		m.addAttribute("user", user);

		return user;
	}

	@GetMapping("/viewNotes")
	public String viewPage(Model m, Principal p, @RequestParam(defaultValue = "0") Integer pageNo) {

		User user = getUser(p, m);

		// List<Notes> notesByUser = noteService.getNotesByUser(user);
		Page<Notes> notesByUser = noteService.getNotesByUser(user, pageNo);

		m.addAttribute("currentPage", pageNo);
		m.addAttribute("totalElements", notesByUser.getTotalElements());
		m.addAttribute("totalPages", notesByUser.getTotalPages());

		m.addAttribute("notesList", notesByUser.getContent());

		return "view_notes";
	}

	@GetMapping("/addNotes")
	public String addNotesPage() {

		return "add_notes";
	}

	@GetMapping("/editNotes/{id}")
	public String editNotes(@PathVariable int id, Model m) {

		Notes notesById = noteService.getNotesById(id);

		m.addAttribute("e", notesById);

		return "edit_notes";
	}

	@PostMapping("/saveNotes")
	public String saveNotes(@ModelAttribute Notes notes, HttpSession session, Principal p, Model m) {

		notes.setDate(LocalDate.now());
		notes.setUser(getUser(p, m));

		Notes saveNotes = noteService.saveNotes(notes);

		if (saveNotes != null) {
			session.setAttribute("msg", "Notes Successfully Save");
		} else {
			session.setAttribute("msg", "Something Wrong On Server");
		}

		return "redirect:/user/addNotes";
	}

	@PostMapping("/uadateNotes")
	public String updateNotes(@ModelAttribute Notes notes, HttpSession session, Principal p, Model m) {

		notes.setDate(LocalDate.now());
		notes.setUser(getUser(p, m));

		Notes saveNotes = noteService.saveNotes(notes);

		if (saveNotes != null) {
			session.setAttribute("msg", "Notes Successfully Updated");
		} else {
			session.setAttribute("msg", "Something Wrong On Server");
		}

		return "redirect:/user/viewNotes";
	}

	@GetMapping("/deleteNotes/{id}")
	public String deleteNotes(@PathVariable int id, HttpSession session) {

		boolean f = noteService.deleteNotes(id);

		if (f) {
			session.setAttribute("msg", "notes successfully delete");
		} else {
			session.setAttribute("msg", "Something Wrong on Server");
		}

		return "redirect:/user/viewNotes";
	}
}
