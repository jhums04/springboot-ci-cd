package com.notes.notesapi.service;

import com.notes.notesapi.DTO.NotesDTO;
import com.notes.notesapi.Exception.NoteNotFoundException;
import com.notes.notesapi.Exception.NoteNotUpdatedException;
import com.notes.notesapi.Repository.NotesRepository;
import com.notes.notesapi.entity.NotesEntity;
import com.notes.notesapi.mapper.NotesMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.notes.notesapi.constants.NoteConstants.MESSAGE_DELETED;

@Service
@AllArgsConstructor
@Slf4j
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    public List<NotesDTO> getAllNotes() {
        List<NotesEntity> notesFromDB = notesRepository.findAll();
        return notesFromDB.isEmpty() ?
                Collections.emptyList()
                : notesFromDB.stream()
                .map(notesEntity -> NotesMapper.noteDataToDTO(notesEntity))
                .collect(Collectors.toList());

    }

    public NotesDTO createNotes(NotesDTO notesDTO) {
        try {
            NotesEntity savedNotes = notesRepository.save(NotesMapper.createNewNote(notesDTO));
            return NotesMapper.noteDataToDTO(savedNotes);
        } catch (Exception e) {
            throw new RuntimeException("Error in creating notes");
        }
    }

//    public NotesDTO getNoteById(Long id) {
//        return NotesMapper.noteDataToDTO(notesRepository.findById(id).orElseThrow(() -> {
//            log.error("Failed to retrieve note with id: {}", id);
//            return new NoteNotFoundException("Note", "id", id);
//        }));
//    }

    public NotesDTO updateNoteById(NotesDTO notesDTO) {
        NotesEntity noteFromDb = notesRepository.findById(notesDTO.getNotesID()).orElseThrow(
                () -> new NoteNotFoundException("Note", "id", notesDTO.getNotesID())
        );

        if (Objects.isNull(notesDTO.getTitle()) && Objects.isNull(notesDTO.getBody())) {
            throw new NoteNotUpdatedException("Note", "Title", "Body", null);
        }

        if (!notesDTO.getTitle().isEmpty()) {
            noteFromDb.setTitle(notesDTO.getTitle());
        }

        if (!notesDTO.getBody().isEmpty()) {
            noteFromDb.setBody(notesDTO.getBody());
        }

        NotesEntity savedEntity = notesRepository.save(noteFromDb);

        return NotesMapper.noteDataToDTO(savedEntity);

    }


    public String deleteNoteById(Long id) {
        NotesEntity notesEntity = notesRepository.findById(id).orElseThrow(
                () -> new NoteNotFoundException("Note", "id", id)
        );
        notesRepository.deleteById(id);
        return MESSAGE_DELETED;
    }

//    Cache implementation for one method call x one cache storing
//    @Cacheable(value = "noteCache", key = "#id")
//    public NotesDTO getNoteById(Long id) {
//        return NotesMapper.noteDataToDTO(notesRepository.findById(id).orElseThrow(() -> {
//            log.error("Failed to retrieve note with id: {}", id);
//            return new NoteNotFoundException("Note", "id", id);
//        }));
//    }


//    Cache call CacheConfig.preloadCache()
//    Will get the specific note form allNotesCache. @Post construct call the cache call after application starts.
    @Cacheable(value = "allNotesCache", key = "#id")
    public NotesDTO getNoteById(Long id) {
        return NotesMapper.noteDataToDTO(notesRepository.findById(id).orElseThrow(() -> {
            log.error("Failed to retrieve note with id: {}", id);
            return new NoteNotFoundException("Note", "id", id);
        }));
    }

    @CacheEvict(value = "allNotesCache" , allEntries = true)
    public void clearCache() {
        System.out.println("******** Clearing Cache **********");

    }

    @CacheEvict(value = "allNotesCache" , key = "#id")
    public void clearCache(Long id) {
        System.out.println(String.format("*************Clearing cache for id: %s *****************", id.toString()));
    }
}
