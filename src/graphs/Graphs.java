/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author x
 */
public class Graphs extends Application {

    private GraphView graphview;
    private static Pane root;

    @Override
    public void start(Stage primaryStage) {
        //create the stack of visible items
        root = new Pane();
        //create the scene of the window
        Scene scene = new Scene(root, 800, 600);
        //create view of graph
        graphview = new GraphView(scene);

        //create the button which adds node to graph
        Button dodajW = new Button();
        dodajW.setText("Dodaj wierzcholek");
        dodajW.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Button newNode;
                newNode = graphview.addNode();
                root.getChildren().add(newNode);
            }
        });
        dodajW.setLayoutX(10);
        dodajW.setLayoutY(10);
        root.getChildren().add(dodajW);
        //create the button which deletes node to graph
        Button UsunW = new Button();
        UsunW.setText("Usun wierzcholek");
        UsunW.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                graphview.removeNode();
            }
        });
        UsunW.setLayoutX(10);
        UsunW.setLayoutY(60);
        root.getChildren().add(UsunW);
       
        //create the button which adds edges to graph
        Button DodajK = new Button("Dodaj krawedz");
        DodajK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graphview.addEdgeDialog();
            }
        });
        DodajK.setLayoutX(700);
        DodajK.setLayoutY(10);
        root.getChildren().add(DodajK);
        //create the button which deletes edges from graph
        Button UsunK = new Button("Usun krawedz");
        UsunK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graphview.removeEdge();
            }
        });
        UsunK.setLayoutX(700);
        UsunK.setLayoutY(60);
        root.getChildren().add(UsunK);
        
        //create checkbox digraph or not
        CheckBox digraf = new CheckBox("Digraf");
        digraf.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(digraf.isSelected()){
                    graphview.setDigraph(true);
                }else{
                    graphview.setDigraph(false);
                }
                graphview.clearAll();
            }
        });
        digraf.setLayoutX(scene.getWidth()/2);
        digraf.setLayoutY(10);
        root.getChildren().add(digraf);
        
        //create the reading graph button
        Button readGraph = new Button("Wczytaj Graf");
        readGraph.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graphview.ReadGraphDialog();
            }
        });
        readGraph.setLayoutX(scene.getWidth()/2 - 150);
        readGraph.setLayoutY(10);
        root.getChildren().add(readGraph);
        //create the algorithm bfs buton
        Button BFS = new Button("BFS");
        BFS.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graphview.BreadthFirstSearch(3, 5);
            }
        });
        BFS.setLayoutX(500);
        BFS.setLayoutY(10);
        root.getChildren().add(BFS);
        //create the button next stp
        Button NextStep = new Button("Nastepny krok");
        NextStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graphview.nextStep();
            }
        });
        NextStep.setLayoutX(600);
        NextStep.setLayoutY(10);
        root.getChildren().add(NextStep);

        primaryStage.setTitle("Graphs");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }

    public static void addNodeToScene(Node o) {
        root.getChildren().add(o);
    }
    public static void removeNodeFromScene(Node o) {
        root.getChildren().remove(o);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
