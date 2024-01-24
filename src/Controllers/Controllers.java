package Controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import Controllers.Interfaces.iNotes;
import Controllers.Interfaces.iView;
import Models.Note;

public class Controllers {
    private iNotes notes;
    private iView view;

    public Controllers(iNotes notes, iView view) {
        this.notes = notes;
        this.view = view;
    }

    private boolean testDate() {
        if (this.notes.readNotes().size() > 0)
            return true;
        return false;
    }

    public void run() {
        Command cmd = (Command) Command.NONE;
        boolean nextRun = true;
        while (nextRun) {
            boolean errorComand = true;
            while (errorComand) {
                String comand = view.prompt(view.inputMessage());
                try {
                    cmd = Command.valueOf(comand.toUpperCase());
                    switch (cmd) {
                        case EXIT:
                            nextRun = false;
                            view.exit(true);
                            view.exitMessage();
                            break;
                        case CREATE:
                            List<String> newNoteParam = view.createNote();
                            Note newNote = new Note(newNoteParam.get(0), newNoteParam.get(1));
                            notes.addNote(newNote);
                            System.out.println(view.createNoteMessangee(newNote.getDateOfEditing()));
                            break;
                        case FIND:
                            if (testDate()) {
                                int criteriaFind = 0;
                                boolean findFailure = true;
                                while (findFailure) {
                                    criteriaFind = view.criteriaFind();
                                    if (criteriaFind < 0 || criteriaFind > 2) {
                                        view.errorCriteriaFind();
                                        timeSleep(1);
                                    } else {
                                        findFailure = false;
                                        String yourFind = view.prompt(view.findInput());
                                        view.findResult();
                                        if (notes.findNote(yourFind, criteriaFind).size() == 0)
                                            view.nullFindResult();
                                        // notes.findNote(view.prompt(view.findInput()), criteriaFind);
                                    }
                                }
                            } else {
                                view.nullNotes();
                            }
                            break;
                        case EDIT:
                            if (testDate()) {

                            } else {
                                view.nullNotes();
                            }
                            break;
                        case PRINT:
                            if (testDate()) {
                                view.printNotesTitle();
                                notes.printlnNotes();
                            } else {
                                view.nullNotes();
                            }
                            break;
                        case DELETE:
                            if (testDate()) {
                                String yourChoiceDelete = view.deleteChoice();
                                if (yourChoiceDelete.equals("1")) {
                                    int deleteIndeeex = view.deleteIndex();
                                    if (deleteIndeeex < 0 || deleteIndeeex > notes.readNotes().size()) {
                                        view.errorIndex();
                                    } else {
                                        notes.deleteNotes(deleteIndeeex);
                                        view.successfulDelete();
                                    }
                                } else if (yourChoiceDelete.equals("2")) {
                                    String yourChoiceDeleteDescription = view.deleteChoiceToDescription();
                                    if (convertNumber(yourChoiceDeleteDescription)) {
                                        int criteria = Integer.parseInt(yourChoiceDeleteDescription) - 1;
                                        notes.deleteNotes(view.prompt(view.deleteInput()), criteria);
                                        view.successfulDelete();
                                    } else view.errorCriteriaFind();
                                } else view.errorCriteriaFind();
                            } else {
                                view.nullNotes();
                            }
                            break;
                        default:
                            break;
                    }
                    timeSleep(1);
                    errorComand = false;
                } catch (Exception e) {
                    System.out.println("Данной команды нет в списке команд!");
                }
            }
        }
    }

    private void timeSleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean convertNumber(String convert) {
        try {
            Integer.parseInt(convert);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
