package Main;

import Main.Models.Characters.Office;
import Main.Models.Characters.PoolObserver;
import Main.Models.Characters.Worker;
import Main.Models.Log;
import Main.Models.MessageManager;
import Main.Models.SimpleLog;
import Main.Models.TimeManager;
import Main.Models.Work.Task;
import Main.UI.AddTaskInfo;
import Main.UI.SortPain;
import Main.UI.SimpleUI;
import Main.UI.ListCellPerson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Aleksand Smilyanskiy on 12.06.2016.
 * "The more we do, the more we can do." ©
 */

public class Main extends Application implements SimpleUI, PoolObserver, SimpleLog, AddTaskInfo {
    // Objects
    Scene mainScene;
    Stage mainStage;
    Office office;

    //UI objects
    ListView<Worker> staffView;
    TextArea textArea;
    ObservableList<Worker> currentList;
    LinkedList<TitledPane> availableTasks;
    Accordion taskView;
    CheckBox supressResult;

    public Main(){
        super();
    }

    public static void main(String[] args) {
        SimpleUI.info();
        launch(args);
    }

    private void initialize() {
        // Office
        office = new Office();
        office.setPoolObserver(this);

        staffView = (ListView<Worker>) mainScene.lookup("#staffView");
        currentList = FXCollections.observableList(office.getCharacters());
        staffView.setItems(FXCollections.observableList(office.getCharacters()));
        staffView.setCellFactory((param) -> new ListCellPerson());
        textArea = (TextArea) mainScene.lookup("#log");
        Log.setSimpleLog(this);

        taskView = (Accordion) mainScene.lookup("#taskView");
        TitledPane sort = new SortPain(this);
        taskView.getPanes().add(sort);



        Button addPerson = (Button) mainScene.lookup("#addPerson");
        addPerson.setOnAction((event) -> {
            Worker person = new Worker();
            office.addCharacter(person);
            Log.writeString("Staff added: " + person);
        });

        Button firePerson = (Button) mainScene.lookup("#firePerson");
        firePerson.setOnAction((event) -> {
            Object pre = staffView.getSelectionModel().getSelectedItem();
            if (pre == null) {
                Log.writeString("Select an item to delete.");
                return;
            }
            Worker person = (Worker) pre;
            office.fireCharacter(person.getId());
            Log.writeString("Staff fired: " + person);
        });

        supressResult = (CheckBox) mainScene.lookup("#supressResult");
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        mainStage = primaryStage;
        String k = System.getProperty("user.dir");
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Main.class.getResource("UI/mainForm.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Main.class.getResource("Main.fxml"));
//        Parent root = loader.load();
//        Parent root = loader.load();
        Parent root = FXMLLoader.load(getClass().getResource("UI/mainForm.fxml"));

        mainScene = new Scene(root, 800, 600);

        primaryStage.setTitle("StreamEra server");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(true);
        primaryStage.setOnCloseRequest((value)->{
            office.fireAll();
            try {
                super.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        primaryStage.show();

        // initialize objects
        initialize();
    }


    //Methods


    // Pool observer
    @Override
    public void taskDone(Task task) {
        Platform.runLater(() -> {
            if (task.getExecutioner() != null) {
                Log.writeString("Работник <" + task.getExecutioner() + "> завершил задачу " + task.getName());
            } else {
                Log.writeString(MessageManager.getMessage("task_done") + task.getName());
            }
            staffView.refresh();
            if (supressResult.isSelected()) {
                return;
            }

            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("UI/taskView.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Task " + task.getName());
                Scene taskScene = new Scene(root, 600, 400);
                stage.setScene(taskScene);
                stage.show();

                TextArea textArea = (TextArea) taskScene.lookup("#taskText");
                textArea.setText(task.getInfo());
                textArea.appendText("\n\n" + MessageManager.getMessage("data_ready") + "\n" + MessageManager.getMessage("work_deli") + "\n");
                textArea.appendText(task.getDataAfter().toString());

                Label executor = (Label) taskScene.lookup("#executor");
                Worker person = task.getExecutioner();
                if (person == null) {
                    executor.setText("Не назначен");
                } else {
                    executor.setText(person.getNickname());
                }

                Label taskName = (Label) taskScene.lookup("#taskName");
                taskName.setText(task.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void poolChanged(List<Worker> newPool) {
        Platform.runLater(() -> {
            currentList = FXCollections.observableList(office.getCharacters());
            staffView.setItems(currentList);
//            // TODO: 13.06.2016 delete this
//            String[] alcos = new String[]{
//                    "WINE,CANADA,198267,3.0,COPPER MOON - MALBEC,30.99",
//                    "WINE,CANADA,305375,4.0,DOMAINE D'OR - DRY,32.99",
//                    "WINE,CANADA,53017,4.0,SOMMET ROUGE,29.99",
//                    "WINE,CANADA,215525,4.0,MISSION RIDGE - PREMIUM DRY WHITE,33.99",
//                    "WINE,USA,168971,3.0,ZINFANDEL - BIG HOUSE CARDINAL ZIN,36.99",
//                    "WINE,FRANCE,234559,4.0,LE VILLAGEOIS RED - CELLIERS LA SALLE,34.99",
//                    "WINE,CANADA,492314,16.0,SAWMILL CREEK - MERLOT,119.0",
//                    "WINE,CANADA,587584,4.0,SOLA,32.99",
//                    "WINE,CANADA,100925,0.75,GANTON & LARSEN PROSPECT - PINOT BLANC BIRCH CANOE 2011,13.99",
//                    "SPIRITS,IRELAND,10157,0.75,JAMESON - IRISH,34.99",
//                    "WINE,ITALY,102764,0.75,PINOT GRIGIO DELLE VENEZIE - RUFFINO LUMINA,15.99",
//                    "SPIRITS,USA,103747,0.75,MAKER'S MARK - KENTUCKY BOURBON,44.95",
//                    "SPIRITS,CANADA,1040,0.75,GORDONS - LONDON DRY,24.49",
//                    "WINE,CANADA,104679,0.75,CALONA - ARTIST SERIES RESERVE PINOT GRIS 2011/13,12.99",
//                    "WINE,USA,106476,0.75,PINOT NOIR - SIDURI RUSSIAN RIVER 11/12,49.99",
//                    "SPIRITS,BRAZIL,107029,0.7,CACHACA 61,28.95"
//            };
//            AlcoholContainer container = new AlcoholContainer();
//            for (String alco: alcos){
//                container.addInfo(new Alcohol(alco));
//            }
//            Sort sort;
//            sort = new Sort(container, Sort.Parameter.Price);
//            sort.startWork();
//            taskDone(sort);
        });
    }

    @Override
    public void allTasksDone(Worker worker) {
        Platform.runLater(() -> {
            staffView.refresh();
        });
    }

    @Override
    public void taskStarted(Worker worker) {
        Platform.runLater(() -> {
            staffView.refresh();
        });
    }

    @Override
    public void appendText(String text) {
        Platform.runLater(() -> {
            String messageAndStamp = TimeManager.getTime() + ": " + text;
            textArea.appendText(messageAndStamp + "\n");
        });
    }

    @Override
    public void addTask(Task task) {
        Platform.runLater(() -> {
            Object pre = staffView.getSelectionModel().getSelectedItem();
            if (pre == null) {
                Log.writeString("Выберите работника для добавления ему задания.");
                return;
            }
            Worker person = (Worker) pre;
            office.addTask(task, person.getId());
            Log.writeString("Добавлено задание работнику: " + person.getNickname());
        });
    }
}
