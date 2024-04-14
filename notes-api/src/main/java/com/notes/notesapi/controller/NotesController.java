package com.notes.notesapi.controller;

import com.notes.notesapi.DTO.NotesDTO;
import com.notes.notesapi.service.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class NotesController {

    private NotesService notesService;

    @PostMapping("/create-note")
    public NotesDTO createNotes(@RequestBody NotesDTO notesDTO) {
        return notesService.createNotes(notesDTO);
    }

    @GetMapping("/all-notes")
    public List<NotesDTO> getAllNotes() {
        return notesService.getAllNotes();
    }

    @GetMapping("/note")
    public NotesDTO getNoteById(@RequestParam Long id) {
        return notesService.getNoteById(id);
    }

    @PutMapping("/note")
    public NotesDTO updateNotebyId(@RequestBody NotesDTO notesDTO) {
        return notesService.updateNoteById(notesDTO);
    }

    @DeleteMapping("/note")
    public String deleteNoteById(@RequestParam Long id) {
        return notesService.deleteNoteById(id);
    }


    @GetMapping("/notes/clear-cache")
    public String clearNotesCache() {
        notesService.clearCache();
        return "notes cache clear successfully";
    }

    @GetMapping("/notes/clear-cache/{id}")
    public String clearNotesCache(@PathVariable Long id) {
        notesService.clearCache(id);
        return "notes cache clear successfully";
    }
}
