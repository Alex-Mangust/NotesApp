package Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Controllers.Interfaces.iNotes;

public class NotesFile implements iNotes {
    String pathFile;

    public NotesFile(String pathFile) {
        this.pathFile = pathFile;
    }

    @Override
    public void addNote(Note note) {
        try (FileWriter fw = new FileWriter(pathFile, true)) {
            fw.write(note.getTitle() + ";" + note.getDescription() + ";" + note.getDateOfEditing());
            fw.append('\n');
            fw.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Note> findNote(String findText, int criteriaFind) {
        List<Note> notes = readNotes();
        List<Note> findNotes = new ArrayList<>();
        if (criteriaFind == 0) {
            for (Note note : notes) {
                if (note.getTitle().equals(findText)) {
                    findNotes.add(note);
                }
            }
        } else if (criteriaFind == 1){
            for (Note note : notes) {
                if (note.getDescription().equals(findText)) {
                    findNotes.add(note);
                }
            }
        } else {
            for (Note note : notes) {
                if (dateFormat(findText).equals(dateFormat(note.getDateOfEditing()))) {
                    findNotes.add(note);
                } else {
                    String[] dateNote = note.getDateOfEditing().split("-");
                    if (dateFormat(findText).equals(dateFormat(dateNote[0]))) {
                        findNotes.add(note);
                    } else if (dateFormat(findText).equals(dateFormat(dateNote[1]))) {
                        findNotes.add(note);
                    }
                }
            }
        }
        for (Note note : findNotes) {
            System.out.println(note);
        }
        return findNotes;
    }

    @Override
    public void printlnNotes() {
        for (Note note : readNotes()) {
            System.out.println(note);
        }
    }

    @Override
    public boolean deleteNotes(int index) {
        List<Note> notes = readNotes();
        boolean deleteNote = false;
        if (notes.size() >= index) {
            notes.remove(index - 1);
            deleteNote = true;
        }
        resetFileNotes(notes);
        return deleteNote;
    }

    @Override
    public boolean deleteNotes(String deleteText, int criteriaDelete) {
        List<Note> notes = readNotes();
        boolean deleteNote = false;
        for (int i = 0; i < notes.size(); i++) {
            if (criteriaDelete == 0) {
                if (deleteText.equals(notes.get(i).getTitle())) {
                    notes.remove(i);
                    deleteNote = true;
                    i--;
                }
            } else if (criteriaDelete == 1) {
                if (deleteText.equals(notes.get(i).getDescription())) {
                    notes.remove(i);
                    deleteNote = true;
                    i--;
                }
            } else {
                if (dateFormat(deleteText).equals(dateFormat(notes.get(i).getDateOfEditing()))) {
                    notes.remove(i);
                    deleteNote = true;
                    i--;
                } else {
                    String[] dateNote = notes.get(i).getDateOfEditing().split("-");
                    if (dateFormat(deleteText).equals(dateFormat(dateNote[0]))) {
                        notes.remove(i);
                        deleteNote = true;
                        i--;
                    } else if (dateFormat(deleteText).equals(dateFormat(dateNote[1]))) {
                        notes.remove(i);
                        deleteNote = true;
                        i--;
                    }
                }
            }
        }
        resetFileNotes(notes);
        return deleteNote;
    }

    @Override
    public void editNotes(int id, int criteriaEdit, String changes) {
        List<Note> notes = readNotes();
        if (id > 0 && id <= notes.size()) {
            if (criteriaEdit == 0) notes.set(id - 1, new Note(changes, notes.get(id-1).getDescription()));
            else notes.set(id - 1, new Note(notes.get(id-1).getTitle(), changes));
        }
        resetFileNotes(notes);
    }

    
    private void resetFileNotes(List<Note> notes) {
        try (FileWriter fw = new FileWriter(pathFile, false)) {
            for (Note note : notes) {
                fw.write(note.getTitle() + ";" + note.getDescription() + ";" + note.getDateOfEditing());
                fw.append('\n');
            }
            fw.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Note> readNotes() {
        List<Note> notes = new ArrayList<>();
        try (FileReader notesReader = new FileReader(pathFile);) {
            try (BufferedReader reader = new BufferedReader(notesReader)) {
                String line = reader.readLine();
                Note.resetGeneralId();
                while (line != null) {
                    String[] param = line.split(";");
                    Note note = new Note(param[0], param[1]);
                    note.setDateOfEditing(param[2]);
                    notes.add(note);
                    line = reader.readLine();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return notes;
    }

    private Date dateFormat(String data) {
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd:MM:yy-hh:mm");
        SimpleDateFormat daysDateFormat = new SimpleDateFormat("dd:MM:yy");
        SimpleDateFormat hoursDateFormat = new SimpleDateFormat("hh:mm");
        try {
            Date date = fullDateFormat.parse(data);
            return date;
        } catch (Exception errorFullFormat) {
            try {
                Date date = daysDateFormat.parse(data);
                return date;
            } catch (Exception errorDaysFormat) {
                try {
                    Date date = hoursDateFormat.parse(data);
                    return date;
                } catch (Exception errorHoursFormat) {
                    Date nullDate = new Date();
                    try {
                        nullDate = fullDateFormat.parse("00:00:00-00:00");
                    } catch (Exception errorDate) {
                        System.out.println(errorDate.getMessage());
                    }
                    return nullDate;
                }
            }
        }
    }

}