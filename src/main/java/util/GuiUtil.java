package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class GuiUtil {
    private GuiUtil() {}

    public static void switchTo(String fxmlName, Node node) {
        try {
            Scene loadScene = new Scene(FXMLLoader.load(GuiUtil.class.getResource("/" + fxmlName + ".fxml")));
            Stage currentStage = (Stage) node.getScene().getWindow();

            currentStage.setScene(loadScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCursorToHand(Node node) {
        node.getScene().setCursor(Cursor.HAND);
    }

    public static void setCursorToDefault(Node node) {
        node.getScene().setCursor(Cursor.DEFAULT);
    }
}
