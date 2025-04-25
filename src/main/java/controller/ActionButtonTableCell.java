package controller;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public class ActionButtonTableCell<T> extends TableCell<T, Void> {
    private final HBox buttons = new HBox();
    private final Button editButton = new Button("Modifier");
    private final Button deleteButton = new Button("Supprimer");

    public ActionButtonTableCell() {
        buttons.setSpacing(5);
        buttons.getChildren().addAll(editButton, deleteButton);
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(buttons);
        }
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
} 