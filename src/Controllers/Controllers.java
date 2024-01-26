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
        view.listCommand();
        while (nextRun) {
            boolean errorComand = true;
            while (errorComand) {
                String comand = view.prompt(view.inputMessage());
                try {
                    cmd = Command.valueOf(comand.toUpperCase());
                    switch (cmd) {
                        case MENU:
                            view.listCommand();
                            break;
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
                                    }
                                }
                            } else {
                                view.nullNotes();
                            }
                            break;
                        case EDIT:
                            if (testDate()) {
                                int editIndex = view.editIndex();
                                    if (editIndex <= 0 || editIndex > notes.readNotes().size()) {
                                        view.errorIndex();
                                    } else {
                                        int criteriaEdit = view.criteriaEdit();
                                        String changes = view.prompt(view.input());
                                        if (criteriaEdit > 0) {
                                            notes.editNotes(editIndex, criteriaEdit - 1, changes);
                                            view.successfulEdit();
                                        } else view.errorCriteriaFind();
                                    }
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
                                    int deleteIndex = view.deleteIndex();
                                    if (deleteIndex < 0 || deleteIndex > notes.readNotes().size()) {
                                        view.errorIndex();
                                    } else {
                                        notes.deleteNotes(deleteIndex);
                                        view.successfulDelete();
                                    }
                                } else if (yourChoiceDelete.equals("2")) {
                                    String yourChoiceDeleteDescription = view.deleteChoiceToDescription();
                                    if (convertNumber(yourChoiceDeleteDescription)) {
                                        int criteria = Integer.parseInt(yourChoiceDeleteDescription) - 1;
                                        if (notes.deleteNotes(view.prompt(view.input()), criteria)) {
                                            view.successfulDelete();
                                        } else {
                                            view.unsuccessfulDelete();
                                        }
                                    } else view.errorCriteriaFind();
                                } else view.errorCriteriaFind();
                            } else {
                                view.nullNotes();
                            }
                            break;
                        default:
                            view.noCommand();
                            break;
                    }
                    timeSleep(1);
                    errorComand = false;
                } catch (Exception e) {
                    view.noCommand();
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
