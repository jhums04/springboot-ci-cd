package com.notes.notesapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "NOTES")
public class NotesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notesID;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;


}
