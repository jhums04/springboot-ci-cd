package com.notes.notesapi.cache;

import com.notes.notesapi.DTO.NotesDTO;
import com.notes.notesapi.entity.NotesEntity;
import com.notes.notesapi.service.NotesService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@EnableCaching
@Configuration
@EnableScheduling
public class CacheConfig {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private NotesService notesService;

    @PostConstruct
    public void preloadCache() {
        Cache cache = cacheManager.getCache("allNotesCache");

        System.out.println("************** Initializing cache ***************");
        List<NotesDTO> allNotes = notesService.getAllNotes();
        for (NotesDTO notesDTO: allNotes) {
            cache.put(notesDTO.getNotesID(), notesDTO);
        }
    }
}
