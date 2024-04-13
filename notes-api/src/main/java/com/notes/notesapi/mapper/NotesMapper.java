package com.notes.notesapi.mapper;


import com.notes.notesapi.DTO.NotesDTO;
import com.notes.notesapi.entity.NotesEntity;

import java.util.Objects;

public class NotesMapper {

    public static NotesDTO noteDataToDTO(NotesEntity note) {
        NotesDTO notesDTO = new NotesDTO();
        notesDTO.setNotesID(note.getNotesID());
        notesDTO.setBody(Objects.nonNull(note.getBody()) ? note.getBody() : null);
        notesDTO.setTitle(Objects.nonNull(note.getTitle()) ? note.getTitle() : null);
        return notesDTO;
    }

    public static NotesEntity noteDTOToMap(NotesDTO notesDTO) {
        NotesEntity noteEntity = new NotesEntity();
        noteEntity.setNotesID(notesDTO.getNotesID());
        noteEntity.setTitle(Objects.nonNull(notesDTO.getTitle()) ? notesDTO.getTitle() : null);
        noteEntity.setBody(Objects.nonNull(notesDTO.getBody()) ? notesDTO.getBody() : null);
        return noteEntity;
    }

    public static NotesEntity createNewNote(NotesDTO notesDTO) {
        NotesEntity newNote = new NotesEntity();
        newNote.setTitle(Objects.nonNull(notesDTO.getTitle()) ? notesDTO.getTitle() : "Default title");
        newNote.setBody(Objects.nonNull(notesDTO.getBody()) ? notesDTO.getBody() : "Default body");
        return newNote;
    }

}
