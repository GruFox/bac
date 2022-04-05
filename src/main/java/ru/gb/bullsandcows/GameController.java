package ru.gb.bullsandcows;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class GameController {

    private Game game;

    private int step;
    @FXML
    private TextArea messageArea;
    @FXML
    private TextField messageFild;

    public GameController() {
        this.game = new Game();
        step = 0;
    }

    @FXML
    private void checkButtonClick(ActionEvent actionEvent) {
        final String playerNumber = messageFild.getText();
        if (playerNumber.isEmpty()) {
            return;
        }
        final Game.BullCowCount count = game.calcBullsAndCows(playerNumber);
        final String text = String.format("%d. Введено число %s. Быков %d, коров %d", ++step, playerNumber, count.getBulls(), count.getCows());
        messageArea.appendText(text + "\n");
        messageFild.clear();
        messageFild.requestFocus();
        if (count.getBulls() == 4) {
            if (isWantToPlayAgain()){
                step = 0;
                messageArea.clear();
                this.game = new Game();
            }else {
                System.exit(0);
            }
        }
    }

    private boolean isWantToPlayAgain() {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Поздравляю, вы выиграли!\n" +
                        "Загаданное число: " + game.getGuessNumber() + "\n" +
                        "Вы потратили " + step + " ходов\n" +
                        "Желаете сыграть еще раз?",
                new ButtonType("Да", ButtonBar.ButtonData.YES),
                new ButtonType("Нет", ButtonBar.ButtonData.NO));
        alert.setTitle("Вопросец");
        final ButtonType answer = alert.showAndWait().get();
        return answer.getButtonData() == ButtonBar.ButtonData.YES;
    }

}