package Models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Controllers.Interfaces.iNotes;

/** Класс, описывающий работу с заметками. Имеет интерфейс iNotes */
public class NotesFile implements iNotes {
    String pathFile; // Путь к файлу

    /**
     * Конструктор касса
     * @param pathFile - путь к файлу
     */
    public NotesFile(String pathFile) {
        this.pathFile = pathFile;
    }

    /**
     * Переопределенный метод, необходимый для создания новой заметки
     * @param note - новая заметка
     * */
    @Override
    public void createNote(Note note) {
        try (FileWriter fw = new FileWriter(pathFile, true)) { // Создаю экземпляр класса FileWriter для записи в файл. Открываю файл в режиме добавления
            fw.write(note.getTitle() + ";" + note.getDescription() + ";" + note.getDateOfEditing()); // Записываю в файл строку, состоящую из заголовка новой заметки, описания новой заметки и даты создания новой заметки с точкой с запятой, в качестве разделителя.
            fw.append('\n'); // Добавляю в конец файла символ перехода на новую строку
            fw.flush(); // Сбрасываю буфер записи данных
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /** Переопределенный метод, необходимый для чтения всех заметок из файла и возвращения их в виде списка */
    @Override
    public List<Note> readNotes() {
        List<Note> notes = new ArrayList<>(); // Создаю новый список, в котором будут храниться заметки из файла
        try (FileReader notesReader = new FileReader(pathFile);) { // Создаю экземпляр класса FileReader для чтения из файла
            try (BufferedReader reader = new BufferedReader(notesReader)) { // Создаю экземпляр класса BufferedReader для более эффективного чтения данных из файла
                String line = reader.readLine(); // Создаю переменную, в которую записываю строку из файла
                Note.resetGeneralId(); // Обнуляю количество созданных заметок
                while (line != null) { // Цикл продолжается, пока не дойдет до конца файла (пока строка не будет равняться null)
                    String[] param = line.split(";"); // Создаю массив String для хранения данных из строки. Строка делится на элементы. Деление определяется точкой с запятой
                    Note note = new Note(param[0], param[1]); // Созданю новый экземпляр класса Note, в качестве заголовка задаю элемент массива param с нулевым индексом, а в качестве описания элемент с первым индексом
                    note.setDateOfEditing(param[2]); // Изменяю дату создание заметки на элемент массива param с вторым индексом.
                    // Таким образом новая заметка полностью копирует ту, которая записана в файле. Необходимо, чтобы превратить заметку, которая записана в файле в качестве простого текста, снова в экземпляр класса Note
                    notes.add(note); // Добавляю заметку в список
                    line = reader.readLine(); // В переменную line записываю новую строку файла
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return notes; // Возвращаю список заметок
    }
    
    /**
     * Переопределенный метод, необходимый для поиска заметки
     * @param findText - поисковой запрос
     * @param criteriaFind - критерий поиска
     * */
    @Override
    public List<Note> findNote(String findText, int criteriaFind) {
        List<Note> notes = readNotes(); // Создаю список, в котором будут храниться все заметки из файла. Содержимое задаю с помощью вызова функции readNotes
        List<Note> findNotes = new ArrayList<>(); // Создаю список, в котором будут храниться все заметки, подходящие под критерии поиска
        if (criteriaFind == 0) { // Если критерий поиска равняется нулю, происходит поиск по заголовку
            for (Note note : notes) { // Цикл проходит по всему списку с заметками
                if (note.getTitle().equals(findText)) { // Сравнивается поисковой запрос и заголовок заметки
                    findNotes.add(note); // Если сравниение удачно, заметка добавляется в список для найденых заметок
                }
            }
        } else if (criteriaFind == 1){ // Если критерий поиска равняется единице, происходит поиск по описанию
            for (Note note : notes) { // Цикл проходит по всему списку с заметками
                if (note.getDescription().equals(findText)) { // Сравнивается поисковой запрос и описание заметки
                    findNotes.add(note); // Если сравниение удачно, заметка добавляется в список для найденых заметок
                }
            }
        } else { // Если критерий поиска равняется другому значению, происходит поиск по дате
            for (Note note : notes) { // Цикл проходит по всему списку с заметками
                if (dateFormat(findText).equals(dateFormat(note.getDateOfEditing()))) { // Сравнивается поисковой запрос и дата создания или редактирования заметки. 
                    findNotes.add(note); // Если сравниение удачно, заметка добавляется в список для найденых заметок
                } else { // Если сравнения поискового запроса и даты создания или редактирования заметки неудачно, то, возможно, пользователь производит поиск либо только по дню, либо только по часам создания или редактирования заметки
                    String[] dateNote = note.getDateOfEditing().split("-"); // Создаю массив String для хранения даты создания или редактирования заметки. В элемент с нулевым индексом записывается день, в элемент с первым записываются часы 
                    if (dateFormat(findText).equals(dateFormat(dateNote[0]))) { // Сравнивается поисковой запрос и день создания или редактирования заметки
                        findNotes.add(note); // Если сравниение удачно, заметка добавляется в список для найденых заметок
                    } else if (dateFormat(findText).equals(dateFormat(dateNote[1]))) { // Сравнивается поисковой запрос и часы создания или редактирования заметки
                        findNotes.add(note); // Если сравниение удачно, заметка добавляется в список для найденых заметок
                    }
                }
            }
        }
        for (Note note : findNotes) { // В консоль выводятся все найденные заметки
            System.out.println(note);
        }
        return findNotes; // Возвращается список со всеми найденными заметками
    }

    /**
     * Переопределенный метод, необходимый для редактирования заметки
     * @param id - id заметки
     * @param criteriaEdit - критерий редактирования (что именно пользователь хочет изменить)
     * @param changes - изменения
     */
    @Override
    public void editNotes(int id, int criteriaEdit, String changes) {
        List<Note> notes = readNotes(); // Создаю список, в котором будут храниться все заметки из файла. Содержимое задаю с помощью вызова функции readNotes
        if (id > 0 && id <= notes.size()) { // Цикл проходит по всему списку с заметками
            if (criteriaEdit == 0) notes.set(id - 1, new Note(changes, notes.get(id-1).getDescription())); // Если нужная заметка найдена и критерий равняется нулю (Редактирование заголовка), то элемент в списке замеменяется на новый экземпляр класса Note, в котором в качестве заголовка указывается новое значение, а в качестве описания - описание найденой заметки.
            else notes.set(id - 1, new Note(notes.get(id-1).getTitle(), changes)); // Если нужная заметка найдена и критерий равняется единице (Редактирование описания), то элемент в списке замеменяется на новый экземпляр класса Note, в котором в качестве заголовка указывается заголовок найденой заметки, а в качестве описания новое значение.
        }
        resetFileNotes(notes); // Перезаписываю файл
    }

    /** Переопределенный метод, необходимый для вывода в консоль всех заметок */
    @Override
    public void printlnNotes() {
        for (Note note : readNotes()) {
            System.out.println(note);
        }
    }

    /**
     * Переопределенный метод, необходимый для удаления заметки
     * @param index - индекс заметки
     */
    @Override
    public boolean deleteNotes(int index) {
        List<Note> notes = readNotes(); // Создаю список, в котором будут храниться все заметки из файла. Содержимое задаю с помощью вызова функции readNotes
        boolean deleteNote = false; // Создаю переменную, которая будет определять удалось ли удалить заметку
        if (notes.size() >= index) { // Проверяется корректность введенного индекса.
            notes.remove(index - 1); // Происходит удаление элемента из списка
            deleteNote = true;
        }
        resetFileNotes(notes); // Перезаписываю файл
        return deleteNote; // Возвращаю переменную deleteNote, отображающую удалось ли удалить заметку
    }

    /**
     * Переопределенный метод, необходимый для удаления заметки
     * @param deleteText - поисковой запрос
     * @param criteriaDelete - критерий поиска
     * Данный метод позволяет найти удалить заметку по ее заголовку, описанию или дате создания или редактирования
     */
    @Override
    public boolean deleteNotes(String deleteText, int criteriaDelete) {
        List<Note> notes = readNotes(); // Создаю список, в котором будут храниться все заметки из файла. Содержимое задаю с помощью вызова функции readNotes
        boolean deleteNote = false; // Создаю переменную, которая будет определять удалось ли удалить заметку
        for (int i = 0; i < notes.size(); i++) { // Цикл проходит по всему списку с заметками
            if (criteriaDelete == 0) { // Если критерий равняется нулю, происходит удаление по заголовку
                if (deleteText.equals(notes.get(i).getTitle())) { // Сравнивается поисковой запрос и заголовок
                    notes.remove(i); // Заметка удаляется из списка
                    deleteNote = true;
                    i--;
                }
            } else if (criteriaDelete == 1) { // Если критерий равняется единице, происходит удаление по описанию
                if (deleteText.equals(notes.get(i).getDescription())) { // Сравнивается поисковой запрос и описание
                    notes.remove(i); // Заметка удаляется из списка
                    deleteNote = true;
                    i--;
                }
            } else { // Если критерий равняется другому значению, происходит удаление по дате создания или редактирования
                if (dateFormat(deleteText).equals(dateFormat(notes.get(i).getDateOfEditing()))) { // Сравнивается поисковой запрос и дата создания или редактирования заметки. 
                    notes.remove(i); // Заметка удаляется из списка
                    deleteNote = true;
                    i--;
                } else { // Если сравнения поискового запроса и даты создания или редактирования заметки неудачно, то, возможно, пользователь производит поиск либо только по дню, либо только по часам создания или редактирования заметки
                    String[] dateNote = notes.get(i).getDateOfEditing().split("-"); // Создаю массив String для хранения даты создания или редактирования заметки. В элемент с нулевым индексом записывается день, в элемент с первым записываются часы 
                    if (dateFormat(deleteText).equals(dateFormat(dateNote[0]))) { // Сравнивается поисковой запрос и день создания или редактирование заметки
                        notes.remove(i); // Заметка удаляется из списка
                        deleteNote = true;
                        i--;
                    } else if (dateFormat(deleteText).equals(dateFormat(dateNote[1]))) { // Сравнивается поисковой запрос и часы создания или редактирование заметки
                        notes.remove(i); // Заметка удаляется из списка
                        deleteNote = true;
                        i--;
                    }
                }
            }
        }
        resetFileNotes(notes); // Перезаписываю файл
        return deleteNote; // Возвращаю переменную deleteNote, отображающую удалось ли удалить заметку
    }

    /**
     * Приватный метод, необходимый для перезаписи файла, в котором хранятся заметки
     * @param notes - список заметок
     */
    private void resetFileNotes(List<Note> notes) {
        try (FileWriter fw = new FileWriter(pathFile, false)) { // Создаю экземпляр класса FileWriter для записи в файл. Открываю файл в режиме перезаписи.
            for (Note note : notes) { // Цикл проходит по всему списку с заметками
                fw.write(note.getTitle() + ";" + note.getDescription() + ";" + note.getDateOfEditing()); // Записываю в файл строку, состоящую из заголовка текущей заметки, описания текущей заметки и даты создания текущей заметки с точкой с запятой, в качестве разделителя.
                fw.append('\n'); // Добавляю в конец файла символ перехода на новую строку
            }
            fw.flush(); // Сбрасываю буфер записи данных
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Приватный метод, необходимый для форматирования поискового запроса в дату, если пользователь производит поиск по дате.
     * @param data - поисковой запрос
     */
    private Date dateFormat(String data) {
        // Создаю несколько экземпляров класса SimpleDateFormat, необходимые для редактирования даты, согласно шаблону
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd:MM:yy-hh:mm"); // Первый экземпляр необходим для редактирования полной даты создания или редактирования заметки
        SimpleDateFormat daysDateFormat = new SimpleDateFormat("dd:MM:yy"); // Второй экземпляр необходим для редактирования только создания или редактирования заметки
        SimpleDateFormat hoursDateFormat = new SimpleDateFormat("hh:mm"); // Третий экземпляр необходим для редактирования только часов создания или редактирования заметки
        Date date = new Date(); // Создается экземпляр класса Date, в котором будет записан отредактированный поисковой запрос
        try {
            date = fullDateFormat.parse(data); // В экземпляр класса Date записывается поисковой запрос в виде даты, согласно первому шаблону
            return date; // Возвращается поисковой запрос в виде даты
        } catch (Exception errorFullFormat) {
            try {
                date = daysDateFormat.parse(data); // Если редактирование по первому шаблону не удается, в экземпляр класса Date записывается поисковой запрос в виде даты, согласно второму шаблону
                return date; // Возвращается поисковой запрос в виде даты
            } catch (Exception errorDaysFormat) {
                try {
                    date = hoursDateFormat.parse(data); // Если редактирование по первому и второму шаблону не удается, в экземпляр класса Date записывается поисковой запрос в виде даты, согласно третьему шаблону
                    return date; // Возвращается поисковой запрос в виде даты
                } catch (Exception errorHoursFormat) {
                    Date nullDate = new Date(); // Если поисковой запрос невозможно отредактировать ни по одному из шаблоннов, создается новый экземпляр класса Date
                    try {
                        nullDate = fullDateFormat.parse("00:00:00-00:00"); // В экземпляр класса Date записывается значение, которое не может быть ни у одной из записок. Таким образом при неккоректном вводе, вместо ошибки, поиск просто не выдаст никаких результатов
                    } catch (Exception errorDate) {
                        System.out.println(errorDate.getMessage());
                    }
                    return nullDate; // Возвращается поисковой запрос в виде даты, которая не может быть ни у одной из записок
                }
            }
        }
    }

}