package Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
    private String title;
    private String description;
    private String dateOfEditing;
    private int idNote;
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd:MM:yy-hh:mm");
    
    private static int generalId;

    static {
        generalId = 0;
    }
    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.dateOfEditing = formatDate.format(new Date());
        generalId++;
        this.idNote = generalId;
    }

    public static void resetGeneralId() {
        generalId = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateOfEditing() {
        return dateOfEditing;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateOfEditing(String dateOfEditing) {
        this.dateOfEditing = dateOfEditing;
    }

    @Override
    public String toString() {
        return String.format("%d. Заголовок: %s; Описание: %s; Дата создания/редактирования: %s", idNote, title, description, dateOfEditing);
    }
    

}
