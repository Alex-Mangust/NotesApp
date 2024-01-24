package Controllers.Interfaces;

import java.util.List;

import Models.Note;

public interface iNotes {
    void addNote(Note note);
    List<Note> findNote(String findText, int criteriaFind);
    void printlnNotes();
    boolean deleteNotes(int index);
    boolean deleteNotes(String deleteText, int criteriaDelete);
    void editNotes(int id, int criteriaEdit);
    List<Note> readNotes();
}
