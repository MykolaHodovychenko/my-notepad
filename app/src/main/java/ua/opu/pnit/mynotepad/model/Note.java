package ua.opu.pnit.mynotepad.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

@Data
@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String contents;
    private Date dateCreation;
    private Date dateUpdate;

    @Ignore
    public Note(String title, String contents, Date dateCreation, Date dateUpdate) {
        this.title = title;
        this.contents = contents;
        this.dateCreation = dateCreation;
        this.dateUpdate = dateUpdate;
    }

    @Ignore
    public Note() {
    }

    public Note(int id, String title, String contents, Date dateCreation, Date dateUpdate) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.dateCreation = dateCreation;
        this.dateUpdate = dateUpdate;
    }
}
