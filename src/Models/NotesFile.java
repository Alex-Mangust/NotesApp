package Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NotesFile {
    String pathFile;

    public NotesFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public void addNote(Note note) {
        try (FileWriter fw = new FileWriter(pathFile, true)) {
            fw.write(note.getTitle() + ";" + note.getDescription() + ";" + note.getDateOfEditing());
            fw.append('\n');
            fw.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void findNote(String findText, int criteriaFind) {
        List<Note> notes = readNotes();
        if (criteriaFind == 0) {
            for (Note note : notes) {
                if (dateFormat(findText).equals(dateFormat(note.getDateOfEditing()))) {
                    System.out.println(note);
                } else {
                    String[] dateNote = note.getDateOfEditing().split("-");
                    if (dateFormat(findText).equals(dateFormat(dateNote[0]))) {
                        System.out.println(note);
                    } else if (dateFormat(findText).equals(dateFormat(dateNote[1]))) {
                        System.out.println(note);
                    }
                }
            }
        } else if (criteriaFind == 1) {
            for (Note note : notes) {
                if (note.getTitle().equals(findText)) {
                    System.out.println(note);
                }
            }
        } else {
            for (Note note : notes) {
                if (note.getDescription().equals(findText)) {
                    System.out.println(note);
                }
            }
        }

    }

    public void printlnNotes() {
        for (Note note : readNotes()) {
            System.out.println(note);
        }
    }

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

    public boolean deleteNotes(String deleteText, int criteriaDelete) {
        List<Note> notes = readNotes();
        boolean deleteNote = false;
        for (int i = 0; i < notes.size(); i++) {
            if (criteriaDelete == 0) {
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
            } else if (criteriaDelete == 1) {
                if (deleteText.equals(notes.get(i).getTitle())) {
                    notes.remove(i);
                    deleteNote = true;
                    i--;
                }
            } else {
                if (deleteText.equals(notes.get(i).getDescription())) {
                    notes.remove(i);
                    deleteNote = true;
                    i--;
                }
            }
        }
        resetFileNotes(notes);
        return deleteNote;
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

    private List<Note> readNotes() {
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