package Main.UI;

import Main.Models.Data.AlcoholContainer;
import Main.Models.Data.AlcoholContainerLoader;
import Main.Models.Work.Tasks.FindValues;
import Main.resources.Log;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

/**
 * Created by Aleksand Smilyanskiy on 15.06.2016.
 * "The more we do, the more we can do." ©
 */
public class FindPain extends TitledPane {
    AlcoholContainer alcoholContainer = AlcoholContainerLoader.loadFromResourses();


    public FindPain(AddTaskInfo parent) {
        super();
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Выберите аргумент: "), 0, 0);
        ComboBox<FindValues.Parameter> comboArg = new ComboBox<>();
        comboArg.setItems(FXCollections.observableArrayList(FindValues.Parameter.values()));
        comboArg.getSelectionModel().select(0);
        grid.add(comboArg, 1, 0);

        grid.add(new Label("Выберите нижнюю границцу: "), 0, 1);
        TextField low = new TextField("нижняя");
        grid.add(low, 1, 1);

        grid.add(new Label("Выберите верхнюю границу: "), 0, 2);
        TextField high = new TextField("верхняя");
        grid.add(high, 1, 2);


        Button addTask = new Button();
        addTask.setOnAction(event -> {
            Platform.runLater(() -> {
                FindValues.Parameter parameter = comboArg.getValue();
                if (parameter.getParameterId() == 3 || parameter.getParameterId() == 4) {
                    try {
                        int lowLevel = Integer.valueOf(low.getText());
                        int highLevel = Integer.valueOf(high.getText());
                        parent.addTask(new FindValues(alcoholContainer, parameter, parameter.getAlcohol(lowLevel), parameter.getAlcohol(highLevel)));
                    } catch (NumberFormatException e) {
                        Log.writeString("Введите правильную нижнюю границу.");
                        return;
                    }
                } else {
                    parent.addTask(new FindValues(alcoholContainer, comboArg.getValue(), parameter.getAlcohol(low.getText()), parameter.getAlcohol(high.getText())));
                }

            });
        });
        addTask.setText("Назначить поиск");
        grid.add(addTask, 0, 3);
        this.setText("Поиск");
        this.setContent(grid);
    }
}
