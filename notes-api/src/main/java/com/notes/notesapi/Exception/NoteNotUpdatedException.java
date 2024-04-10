package com.notes.notesapi.Exception;

public class NoteNotUpdatedException extends RuntimeException{
    public NoteNotUpdatedException(String note, String fieldName1, String fieldName2, String fieldValue) {
        super(String.format("%s cannot be updated with the given input data %s and %s as $s", note, fieldName1, fieldName2, fieldValue));
    }
}
