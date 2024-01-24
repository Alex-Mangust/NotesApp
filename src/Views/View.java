package Views;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Controllers.Interfaces.iView;

public class View implements iView {
    private boolean exit = false;

    @Override
    public String prompt(String msg) {
        Scanner in = new Scanner(System.in, "UTF-8");
        System.out.print(msg);
        if (exit) in.close();
        String yourInput = in.nextLine();
        System.out.println();
        return yourInput;
    }

    @Override
    public String inputMessage() {
        return "Введите команду: ";
    }

    @Override
    public void exit(boolean exit) {
        this.exit = exit;
    }

    @Override
    public void exitMessage() {
        System.out.println("Выход из программы");
    }

    @Override
    public List<String> createNote() {
        List<String> notes = new ArrayList<>();
        String title = prompt("Введите заголовок заметки: ");
        String description = prompt("Введите описание заметки: ");
        notes.add(title);
        notes.add(description);
        return notes;
    }

    @Override
    public String createNoteMessangee(String date) {
        return String.format("Заметка создана успешно! Дата создания: %s", date);
    }

    @Override
    public int criteriaFind() {
        String criteria = prompt("По какому критерию вы хотите произвести поиск?\n1 - По Заголовку; 2 - По описанию; 3 - По дате\n");
        try {
            return Integer.parseInt(criteria) - 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void errorCriteriaFind() {
        System.out.println("Ошибка! Вы ввели неверный критерий!\nПожалуйста, введите другое значение!\n");
    }

    @Override
    public String findInput() {
        return "Введите свой запрос: ";
    }

    @Override
    public void findResult() {
        System.out.println("Результаты по вашему запросу:");
    }

    @Override
    public void nullFindResult() {
        System.out.println("Поиск не дал результатов!");
    }
    
    @Override
    public void nullNotes() {
        System.out.println("У вас нет ни одной заметки!");
    }
    @Override
    public void printNotesTitle() {
        System.out.println("Список всех ваших заметок:");
    }

    @Override
    public String deleteChoice() {
        String yourChoice = prompt("Вы хотите выбрать заметку для удаления по индексу или по ее содержимому?\n1 - По индексу; 2 - По содержимому\n");
        if (!yourChoice.equals("1") && !yourChoice.equals("2")) return "-1";
        return yourChoice;
    }

    @Override
    public int deleteIndex() {
        String index = prompt("Введите индекс заметки, которую хотите удалить: ");
        try {
            return Integer.parseInt(index);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public void errorIndex() {
        System.out.println("Ошибка! Заметки с таким индексом не существует!!!");
    }

    @Override
    public String deleteChoiceToDescription() {
        String yourChoice = prompt("Вы хотите выбрать заметку для удаления по заголовку, по описанию или по дате?\n1 - По заголовку; 2 - По описанию; 3 - По дате\n");
        if (!yourChoice.equals("1") && !yourChoice.equals("2") && !yourChoice.equals("3")) return "-1";
        return yourChoice;
    }

    @Override
    public void successfulDelete() {
        System.out.println("Удаление прошло успешно!");
    }

    @Override
    public String deleteInput() {
        return "Введите ваш запрос: ";
    }

    
    

}
