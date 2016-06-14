package Main.UI;

import Main.Models.Data.AlcoholContainer;
import Main.Models.Data.AlcoholContainerLoader;
import Main.Models.Work.Tasks.Sort;
import Main.UI.AddTaskInfo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

/**
 * Created by Aleksand Smilyanskiy on 13.06.2016.
 * "The more we do, the more we can do." ©
 */
public class SortPain extends TitledPane{
    AlcoholContainer alcoholContainer = AlcoholContainerLoader.loadFromResourses();

    public SortPain(AddTaskInfo parent){
        super();
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(5,5,5,5));
        grid.add(new Label("Выберите аргумент: "),0,0);
        ComboBox<Sort.Parameter> comboArg = new ComboBox<>();
        comboArg.setItems(FXCollections.observableArrayList(Sort.Parameter.values()));
        comboArg.getSelectionModel().select(0);
        grid.add(comboArg,1,0);
        Button addTask = new Button();
        addTask.setOnAction(event -> {
            Platform.runLater(()->{
                parent.addTask(new Sort(alcoholContainer,comboArg.getValue()));
            });
        });
        addTask.setText("Назначить сортировку");
        grid.add(addTask,0,1);
        this.setText("Сортировка");
        this.setContent(grid);
    }
}
