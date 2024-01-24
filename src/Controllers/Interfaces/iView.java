package Controllers.Interfaces;

import java.util.List;

public interface iView {
    String prompt(String msg);
    String inputMessage();
    void exit(boolean exit);
    void exitMessage();
    List<String> createNote();
    String createNoteMessangee(String date);
    int criteriaFind();
    void errorCriteriaFind();
    String findInput();
    void findResult();
    void nullFindResult();
    void printNotesTitle();
    void nullNotes();
    String deleteChoice();
    int deleteIndex();
    void errorIndex();
    String deleteChoiceToDescription();
    String deleteInput();


    void successfulDelete();
}
