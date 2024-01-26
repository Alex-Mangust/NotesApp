package Views;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Controllers.Interfaces.iView;

/** Класс, описывающий работу пользовательского интерфейса */
public class View implements iView {
    private boolean exit = false; // Поле для определения закрытия программы (Необходимо только для того, чтобы закрыть Scanner и не видеть предупреждения)

    /**
     * Переопределенный метод, необходимый для получения данных от пользователя
     * @param msg - сообзение пользователю
     */
    @Override
    public String prompt(String msg) {
        Scanner in = new Scanner(System.in, "UTF-8"); // Создаю экземпляр класса Scanner
        System.out.print(msg); // Вывожу сообщение пользователю в консоль
        if (exit) in.close(); // Если программа закрыта, прекращую работу сканнера
        String yourInput = in.nextLine(); // Создаю переменную, в которую записываю то, что ввел пользователь
        System.out.println();
        return yourInput; // Возвращаю то, что ввел пользователь
    }
    
    /** Переопределенный метод, необходимый для получения сообщения для пользователя, в котором предлагается ввести команду */
    @Override
    public String inputMessage() {
        return "Введите команду: ";
    }

    /** Переопределенный метод, необходимый для вывода в консоль списка всех команд для работы с программой */
    public void listCommand() {
        System.out.println("\nСписок команд\n\nMENU - вызвать список команд\nCREATE - Создать новую заметку\nFIND - Найти заметку\nEDIT - Внести изменения в заметку\nPRINT - Распечатать весь список заметок\nDELETE - Удалить заметку\nEXIT - Завершить работу программы\n");
    }

    /** Переопределенный метод, необходимый для вывода в консоль сообщения, если в файле нет ни одной заметки */
    @Override
    public void nullNotes() {
        System.out.println("У вас нет ни одной заметки!");
    }

    /** Переопределенный метод, необходимый для работы интерфейса создания заметки */
    @Override
    public List<String> createNote() {
        List<String> notes = new ArrayList<>();
        String title = prompt("Введите заголовок заметки: ");
        String description = prompt("Введите описание заметки: ");
        notes.add(title);
        notes.add(description);
        return notes;
    }

    /** Переопределенный метод, необходимый для получения сообщения для пользователя о успешном создании заметки */
    @Override
    public String createNoteMessangee(String date) {
        return String.format("Заметка создана успешно! Дата создания: %s", date);
    }

    /** Переопределенный метод, необходимый для получения сообщения для пользователя, в котором предлагается ввести поисковой запрос */
    @Override
    public String input() {
        return "Введите ваш запрос: ";
    }

    /** Переопределенный метод, необходимый для работы интерфейса выбора критерия поиска */
    @Override
    public int criteriaFind() {
        String criteria = prompt("По какому критерию вы хотите произвести поиск?\n1 - По Заголовку; 2 - По описанию; 3 - По дате\n");
        try {
            return Integer.parseInt(criteria) - 1;
        } catch (Exception e) {
            return -1;
        }
    }

    /** Переопределенный метод, необходимый для вывода в консоль сообщения, перед тем как выдать результаты поиска */
    @Override
    public void findResult() {
        System.out.println("Результаты по вашему запросу:");
    }

    /** Переопределенный метод, необходимый для выввода в консоль сообщения, если поиск не дал результатов */
    @Override
    public void nullFindResult() {
        System.out.println("Поиск не дал результатов!");
    }

    /** Переопределенный метод, необходимый для вывода в консоль сообщения, если пользователь ввел неверный критерий */
    @Override
    public void errorCriteriaFind() {
        System.out.println("Ошибка! Вы ввели неверный критерий!\nПожалуйста, введите другое значение!\n");
    }

    /** Переопределенный метод, необходимый для работы интерфейса редактирования заметки */
    @Override
    public int criteriaEdit() {
        String criteria = prompt("Что бы вы хотели изменить?\n1 - Заголовок заметки; 2 - Описание заметки\n");
        try {
            return Integer.parseInt(criteria);
        } catch (Exception e) {
            return -1;
        }
    }

    /** Переопределенный метод, необходимый для получения от пользователя индекса заметки, в которую он хочет внести изменения  */
    @Override
    public int editIndex() {
        String index = prompt("Введите индекс заметки, в которую хотите внести изменения: ");
        try {
            return Integer.parseInt(index);
        } catch (Exception e) {
            return -1;
        }
    }

    /** Переопределенный метод, необходимый для вывода в консоль сообщения о том, что редактирование заметки прошло успешно */
    @Override
    public void successfulEdit() {
        System.out.println("Изменения сохранены!");
    }

    /** Переопределенный метод, необходимый для вывода в консоль сообщения, перед тем как выдать список всех заметок */
    @Override
    public void printNotesTitle() {
        System.out.println("Список всех ваших заметок:");
    }

    /** Переопределенный метод, необходимый для получения от пользователя индекса заметки, которую он хочет удалить */
    @Override
    public int deleteIndex() {
        String index = prompt("Введите индекс заметки, которую хотите удалить: ");
        try {
            return Integer.parseInt(index);
        } catch (Exception e) {
            return -1;
        }
    }

    /** Переопределенный метод, необходимый для вывода в консоль сообщения, если пользователь ввел неверный индекс */
    @Override
    public void errorIndex() {
        System.out.println("Ошибка! Заметки с таким индексом не существует!!!");
    }

    /** Переопределенный метод, необходимый для работы интерфейса удаления заметки */
    @Override
    public String deleteChoice() {
        String yourChoice = prompt("Вы хотите выбрать заметку для удаления по индексу или по ее содержимому?\n1 - По индексу; 2 - По содержимому\n");
        if (!yourChoice.equals("1") && !yourChoice.equals("2")) return "-1";
        return yourChoice;
    }

    /** Переопределенный метод, необходимый для работы интерфейса удаления заметки */
    @Override
    public String deleteChoiceToDescription() {
        String yourChoice = prompt("Вы хотите выбрать заметку для удаления по заголовку, по описанию или по дате?\n1 - По заголовку; 2 - По описанию; 3 - По дате\n");
        if (!yourChoice.equals("1") && !yourChoice.equals("2") && !yourChoice.equals("3")) return "-1";
        return yourChoice;
    }

    /** Переопределенный метод, необходимый для вывода в консоль сообщения о том, что удаление заметки прошло успешно */
    @Override
    public void successfulDelete() {
        System.out.println("Удаление прошло успешно!");
    }

    /** Переопределенный метод, необходимый для вывода в консоль сообщения о том, что удаление заметки не удалось */
    @Override
    public void unsuccessfulDelete() {
        System.out.println("Ошибка! Не удалось удалить заметку по вашему запросу!");
    }

    /** Переопределенный метод, необходимый для вывода в консоль сообщения о том, что введенной пользователем команды нет в списке команд */
    @Override
    public void noCommand() {
        System.out.println("Данной команды нет в списке команд!\nВведите \"menu\", чтобы посмотреть список команд для работы с программой!");
    }
    
    /** Переопределенный метод, необходимый для вывода в консоль сообщения о том, что работа программы завершена */
    @Override
    public void exitMessage() {
        System.out.println("Выход из программы");
    }
}
