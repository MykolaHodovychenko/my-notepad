package ua.opu.pnit.mynotepad.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Note {
    private int id;
    private String title;
    private String contents;
    private Date dateCreation;
    private Date dateUpdate;
}
