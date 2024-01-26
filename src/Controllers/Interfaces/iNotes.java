package Controllers.Interfaces;

import java.util.List;

import Models.Note;

/** Интерфейс, объявляющий работу с заметками */
public interface iNotes {
    void createNote(Note note); // Объявленный, необходимый для создания новой заметки
    List<Note> readNotes(); // Объявленный метод, необходимый для чтения всех заметок из файла и возвращения их в виде списка
    List<Note> findNote(String findText, int criteriaFind); // Объявленный метод, необходимый для поиска заметки
    void editNotes(int id, int criteriaEdit, String changes); // Объявленный метод, необходимый для редактирования заметки
    void printlnNotes(); // Объявленный метод, необходимый для вывода в консоль всех заметок
    boolean deleteNotes(int index); // Объявленный метод, необходимый для удаления заметки
    boolean deleteNotes(String deleteText, int criteriaDelete); // Объявленный метод, необходимый для удаления заметки
}
