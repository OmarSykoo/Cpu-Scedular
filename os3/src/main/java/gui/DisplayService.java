package gui;

import java.util.LinkedList;

import core.ProcessCpu;
import core.IntervalLists.IntervalList;
import core.IntevalCpus.IntervalCpu;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DisplayService extends Application {

    private static IntervalList intervals;
    private static LinkedList<ProcessCpu> process;
    private static String[] colors;
    private static Scene scene;

    public static void CreateDisplay(IntervalList intervals2, LinkedList<ProcessCpu> process2) {
        intervals = intervals2;
        process = process2;
        colors = new String[] {
                "red",
                "blue",
                "green",
                "yellow",
                "purple"
        };
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane root = new AnchorPane();
        root.setPrefSize(1360, 889);

        HBox mainHBox = new HBox(10);
        mainHBox.setPrefSize(1360, 889);

        AnchorPane leftPanel = new AnchorPane();

        VBox leftVBox = new VBox(10);
        leftVBox.setPadding(new Insets(10));
        leftVBox.setAlignment(Pos.TOP_CENTER);

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Time");
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Tasks");

        GanttChart<Number, String> ganttChart = new GanttChart<>(xAxis, yAxis);
        ganttChart.setBlockHeight(40);
        GanttChart.Series<Number, String> series = new GanttChart.Series<>();

        for (IntervalCpu interval : intervals) {
            series.getData().add(new GanttChart.Data<>(
                    interval.startTime, "P" + interval.Pnum,
                    new GanttChart.ExtraData(interval.executedTime(),
                            "status-" + colors[interval.Pnum % colors.length])));
        }

        ganttChart.getData().add(series);
        ganttChart.setPrefSize(1007, 849);

        HBox statsHBox = new HBox(12);
        statsHBox.setAlignment(Pos.CENTER_LEFT);
        Label statisticsLabel = new Label("Statistics");
        statisticsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
        Label aataLabel = new Label("AATA: 0");
        Label awtLabel = new Label("AWT: 0");
        statsHBox.getChildren().addAll(statisticsLabel, aataLabel, awtLabel);

        leftVBox.getChildren().addAll(ganttChart, statsHBox);

        AnchorPane.setTopAnchor(leftVBox, 0.0);
        AnchorPane.setBottomAnchor(leftVBox, 0.0);
        AnchorPane.setLeftAnchor(leftVBox, 0.0);
        AnchorPane.setRightAnchor(leftVBox, 0.0);

        leftPanel.getChildren().add(leftVBox);

        AnchorPane rightPanel = new AnchorPane();

        TableView<String> processTable = new TableView<>();
        processTable.setPrefSize(300, 858);

        TableColumn<String, String> processColumn = new TableColumn<>("Process");
        TableColumn<String, String> colorColumn = new TableColumn<>("Color");
        TableColumn<String, String> nameColumn = new TableColumn<>("Name");
        TableColumn<String, String> pidColumn = new TableColumn<>("PID");
        TableColumn<String, String> priorityColumn = new TableColumn<>("Priority");

        processTable.getColumns().addAll(
                processColumn,
                colorColumn,
                nameColumn,
                pidColumn,
                priorityColumn);

        AnchorPane.setTopAnchor(processTable, 5.0);
        AnchorPane.setBottomAnchor(processTable, 5.0);
        AnchorPane.setLeftAnchor(processTable, 5.0);
        AnchorPane.setRightAnchor(processTable, 5.0);

        rightPanel.getChildren().add(processTable);

        mainHBox.getChildren().addAll(leftPanel, rightPanel);

        AnchorPane.setTopAnchor(mainHBox, 0.0);
        AnchorPane.setBottomAnchor(mainHBox, 0.0);
        AnchorPane.setLeftAnchor(mainHBox, 0.0);
        AnchorPane.setRightAnchor(mainHBox, 0.0);

        root.getChildren().add(mainHBox);

        scene = new Scene(root);
        scene.getStylesheets()
                .add(getClass()
                        .getResource("/gantt-chart-styles.css")
                        .toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Window");
        primaryStage.show();
    }

}
