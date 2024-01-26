package Controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import Controllers.Interfaces.iNotes;
import Controllers.Interfaces.iView;
import Models.Note;

/** Класс, описывающий работу модели с пользовательским интерфейсом */
public class Controllers {
    private iNotes notes; // Модель, с которой работает конроллер
    private iView view; // Пользовательский интерфейс, с которым работает контроллер

    /**
     * Конструктор класса
     * 
     * @param notes - модель
     * @param view  - пользовательский интерфейс
     */
    public Controllers(iNotes notes, iView view) {
        this.notes = notes;
        this.view = view;
    }

    /**
     * Метод, проверяющий наличие заметок в файле
     * 
     * @return возвращает имеет ли список какие-либо значения
     */
    private boolean testDate() {
        if (this.notes.readNotes().size() > 0)
            return true;
        return false;
    }

    /** Метод, описывающий запуск и работу программы */
    public void run() {
        Command cmd = (Command) Command.NONE; // Инициализируется экземпляр класса перечисления со значением NONE
        boolean nextRun = true; // Переменная, необходимая для продолжения работы программы, пока пользователь
                                // не введет команду о завершении
        view.listCommand(); // В консоль выводится список всех команд для работы с программой
        while (nextRun) {
            String comand = view.prompt(view.inputMessage()); // Происходит запрос пользователю, чтобы он ввел команду.
            try {
                cmd = Command.valueOf(comand.toUpperCase()); // Экземпляру класса перечисления записывается значение, которое ввел пользователь
                switch (cmd) { 
                    case MENU: // Вывод в консоль списка всех команд для работы с программой
                        view.listCommand();
                        break;
                    case EXIT: // Завершение работы программы
                        nextRun = false;
                        view.exitMessage();
                        break;
                    case CREATE: // Создание новой заметки
                        List<String> newNoteParam = view.createNote(); // Список с элементами String, в котором хранятся данные новой заметки в текстовом виде
                        Note newNote = new Note(newNoteParam.get(0), newNoteParam.get(1)); // Создается новый экземпляр класса Note со значениями из списка newNoteParam
                        notes.createNote(newNote); // В файл записывается новая заметка
                        System.out.println(view.createNoteMessangee(newNote.getDateOfEditing())); // Выводится сообщение об успешном создании заметки
                        break;
                    case FIND: // Поиск заметки
                        if (testDate()) { // Проверяется, имеются ли в файле заметки
                            boolean findFailure = true; // Переменная, необходимая для повторного ввода в случае, если пользователь введет неверный критерий поиска
                            while (findFailure) {
                                int criteriaFind = view.criteriaFind(); // Переменная, в которую записывается критерий поиска, введенный пользователем
                                if (criteriaFind < 0 || criteriaFind > 2) {
                                    view.errorCriteriaFind(); // Выводится сообщение об ошибке
                                    timeSleep(1); // Программа ожидает 1 секунду
                                } else {
                                    findFailure = false; // Цикл больше не будет повторяться, так как пользователь ввел верный критерий поиска
                                    String yourFind = view.prompt(view.input()); // Переменная, в которую записывается поисковой запрос, введенный пользователм
                                    view.findResult(); // Выводятся результаты поиска
                                    if (notes.findNote(yourFind, criteriaFind).size() == 0)
                                        view.nullFindResult(); // Если поиск не дал результатов, выводится сообщение об этом
                                }
                            }
                        } else {
                            view.nullNotes(); // Если в файле нет ни одной заметки, выводится сообщение об этом
                        }
                        break;
                    case EDIT: // Редактирование заметки 
                        if (testDate()) { // Проверяется, имеются ли в файле заметки
                            int editIndex = view.editIndex(); // Переменная, в которую записывается введенный пользователем индекс заметки, в которую необходимо внести изменения
                            if (editIndex <= 0 || editIndex > notes.readNotes().size()) {
                                view.errorIndex(); // Если введенный индекс меньше или равен нулю, либо превышает количество заметок, а значит не может принадлежать ни одной из них, выводится сообщение об ошибке
                            } else {
                                int criteriaEdit = view.criteriaEdit(); // Переменная, в которую записывается критерий редактирования (Что именно необходимо изменить), введенный пользователем
                                String changes = view.prompt(view.input()); // Переменная, в которую записываются изменения, введенные пользователем
                                if (criteriaEdit > 0) {
                                    notes.editNotes(editIndex, criteriaEdit - 1, changes); // Происходит редактирование заметки
                                    view.successfulEdit(); // Выводится сообзение об успешном редактировании
                                } else
                                    view.errorCriteriaFind(); // Выводится сообщение о том, что редактирование не удалось
                            }
                        } else {
                            view.nullNotes(); // Если в файле нет ни одной заметки, выводится сообщение об этом
                        }
                        break;
                    case PRINT: // Вывод списка всех заметок
                        if (testDate()) { // Проверяется, имеются ли в файле заметки
                            view.printNotesTitle(); // Выводится сообщение заголовок
                            notes.printlnNotes(); // Выводятся все заметки
                        } else {
                            view.nullNotes(); // Если в файле нет ни одной заметки, выводится сообщение об этом
                        }
                        break;
                    case DELETE: // Удаление заметки
                        if (testDate()) { // Проверяется, имеются ли в файле заметки
                            String yourChoiceDelete = view.deleteChoice(); // Переменная, в которую записывается критерий удаления заметки
                            if (yourChoiceDelete.equals("1")) { // Если критерий равняется единице, происходит удаление по индексу
                                int deleteIndex = view.deleteIndex(); // Переменная, в которую записывается, введенный пользователем, индекс заметки, которую необходимо удалить.
                                if (deleteIndex <= 0 || deleteIndex > notes.readNotes().size()) {
                                    view.errorIndex(); // Если введенный индекс меньше или равен нулю, либо превышает количество заметок, а значит не может принадлежать ни одной из них, выводится сообщение об ошибке
                                } else {
                                    notes.deleteNotes(deleteIndex); // Происходит удаление заметки
                                    view.successfulDelete(); // Выводится сообщение об успешном удалении заметки
                                }
                            } else if (yourChoiceDelete.equals("2")) { // Если критерий равняется двойке, происходит удаление по содержимому
                                String yourChoiceDeleteDescription = view.deleteChoiceToDescription(); // Переменная, в которую записывается критерий удаления заметки по содержимому
                                if (convertNumber(yourChoiceDeleteDescription)) { // Проверяется, возможно ли переменную с критерием конвертировать в число
                                    int criteria = Integer.parseInt(yourChoiceDeleteDescription) - 1; // Переменная, в которую записывается критерий удаления запитки по содержимому в числовом виде
                                    if (notes.deleteNotes(view.prompt(view.input()), criteria)) { // Происходит удаление заметки в которой, в зависимости от выбранного критерия, либо заголовок, либо описание, либо дата совпадает с тем, что ввел пользователь.
                                        view.successfulDelete(); // Выводится сообщение об успешном удалении заметки
                                    } else {
                                        view.unsuccessfulDelete(); // Выводится сообщение о том, что удаление не удалось
                                    }
                                } else
                                    view.errorCriteriaFind(); // Выводится сообщение о том, что пользователь ввел неккоректный критерий удаления по содержимомуу
                            } else
                                view.errorCriteriaFind(); // Выводится сообщение о том, что пользователь ввел неккоректный критерий удаления
                        } else {
                            view.nullNotes(); // Если в файле нет ни одной заметки, выводится сообщение об этом
                        }
                        break;
                    default:
                        view.noCommand(); // Выводится сообщение о том, что введенной пользователем команды нет в списке команд
                        break;
                }
                timeSleep(1); // Программа ожидает 1 секунду
            } catch (Exception e) {
                view.noCommand();
            }
        }
    }

    /**
     * Приватный метод, необходимый для приостановления программы в процессе работы
     * @param second - время паузы в секундах
     */
    private void timeSleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Приватный метод, необходимый для конвертирования текста в число
     * @param convert - текст, который нужно конвертировать
     */
    private boolean convertNumber(String convert) {
        try {
            Integer.parseInt(convert);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
