package Main.UI;

import Main.Models.Characters.Worker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

/**
 * Created by Aleksand Smilyanskiy on 13.06.2016.
 * "The more we do, the more we can do." ©
 */
public class ListCellPerson  extends ListCell<Worker>{
    private Label nickname = new Label();
    private Label running = new Label();
    private HBox hBox = new HBox();

    public ListCellPerson(){
        super();
        nickname.setPrefSize(160,20);
        hBox.getChildren().addAll(nickname, running);
    }

    @Override
    protected void updateItem(Worker item, boolean empty) {
        super.updateItem(item,empty);
        setText(null);

        if (item == null || empty){
            setGraphic(null);
            return;
        }

        nickname.setText(item.getNickname());
        setRunning(item);
        setGraphic(hBox);
    }

    public void setRunning(Worker item){
        item.queueSize();
        running.setText(item.queueSize() < 1? "done" : item.queueSize() + " are running");
    }
}
