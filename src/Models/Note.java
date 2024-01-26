package Models;

import java.text.SimpleDateFormat;
import java.util.Date;

/** Класс, описывающий поведение заметки */
public class Note {
    private String title; // Заголовок заметки
    private String description; // Описание заметки
    private String dateOfEditing; // Дата создания или редактирования заметки
    private int idNote; // id заметки
    private SimpleDateFormat formatDate = new SimpleDateFormat("dd:MM:yy-hh:mm"); // Поле, необходимое для форматирование даты согласно формату - день:месяц:год час:минута
    
    private static int generalId; // Стаческое поле, отражающее количество созданных заметок

    static {
        generalId = 0;
    }

    /**
     * Конструктор класса
     * @param title - заголовок заметки
     * @param description - описание заметки
     */
    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.dateOfEditing = formatDate.format(new Date()); // Получаю текущую дату и форматирую ее, согласно формату описанному в поле formatDate
        generalId++;
        this.idNote = generalId;
    }

    /** Метод, необходимый для сброса количества созданных заметок. Необходим, чтобы при перезаписи файла количество заметок оставалось неизменным.*/
    public static void resetGeneralId() {
        generalId = 0;
    }

    /** Метод, необходимый для получения заголовка заметки */
    public String getTitle() {
        return title;
    }

    /** Метод, необходимый для получения описания заметки */
    public String getDescription() {
        return description;
    }

    /**Метод, необходимый для получения даты создания или редактирования заметки */
    public String getDateOfEditing() {
        return dateOfEditing;
    }

    /**
     * Метод, необходимый для изменения заголовка заметки
     * @param title - заголовк заметки
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Метод, необходимый для изменения описания заметки
     * @param description - описание заметки
     * */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Метод, необходимый для измения даты создания или редактирования заметки. Необходим при чтении заметок из файла
     * @param dateOfEditing - дата создания или редактирования заметки
     * */
    public void setDateOfEditing(String dateOfEditing) {
        this.dateOfEditing = dateOfEditing;
    }

    /** Переопределенный метод, сообщающий информацию об экземпляре класса */
    @Override
    public String toString() {
        return String.format("%d. Заголовок: %s; Описание: %s; Дата создания/редактирования: %s", idNote, title, description, dateOfEditing);
    }
    

}
