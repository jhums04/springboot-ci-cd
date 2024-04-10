package com.notes.notesapi.Exception;

public class NoteNotFoundException extends RuntimeException{

    public NoteNotFoundException(String note, String fieldName, long fieldValue) {
        super(String.format("%s not found with the given input data %s: %s", note, fieldName, fieldValue));
    }

}
