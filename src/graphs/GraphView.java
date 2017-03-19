/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GraphView extends GraphLogic {

    //the body of the graph view
    private List<Button> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    //useful infromation
    private int selectedNode;
    boolean wagi;

    //scene of the widnow
    private Scene window;

    GraphView(Scene window) {
        super();
        this.window = window;
        n = 0;
        m = 0;
        digraph = false;
        wagi = true;
        selectedNode = -1;

    }

    public void setDigraph(boolean digraph) {
        this.digraph = digraph;
    }

    public Button addNode() {
        //create new Node
        Button newNode = new Button();
        if (n == 0) {
            newNode.setText(String.format("%d", n));
        } else {
            newNode.setText(String.format("%d", Integer.parseInt(nodes.get(n - 1).getText()) + 1));
        }
        nodes.add(newNode);

        //set properties of node
        markNode(n, 0);
        if (n == 0) {
            newNode.setLayoutX(50);
            newNode.setLayoutY(100);
        } else {
            newNode.setLayoutX((nodes.get(n - 1).getLayoutX() + 100) % 800);
            if (n % 8 != 0) {
                newNode.setLayoutY(nodes.get(n - 1).getLayoutY());
            } else {
                newNode.setLayoutY(nodes.get(n - 1).getLayoutY() + 100);
            }

        }
        //appearance of node
        newNode.setScaleX(1.5);
        newNode.setScaleY(1.5);
        newNode.setShape(new Circle(1.5));

        //action when clicked node
        newNode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (selectedNode != -1) {
                    markNode(selectedNode, 0);
                }
                if (selectedNode == Integer.parseInt(newNode.getText())) {
                    markNode(selectedNode, 0);
                    selectedNode = -1;
                } else if (selectedNode == -1) {
                    selectedNode = Integer.parseInt(newNode.getText());
                    markNode(selectedNode, 1);
                } else {
                    //adding new line to scene usign statci method from Graphs class
                    AskUserForValue(selectedNode, Integer.parseInt(newNode.getText()));
                    selectedNode = -1;
                }

            }
        });

        newNode.setOnMouseDragged(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                if (event.getSceneX() > 0 && event.getSceneX() < window.getWidth() && event.getSceneY() > 0 && event.getSceneY() < window.getHeight()) {
                    newNode.setLayoutX(event.getSceneX());
                    newNode.setLayoutY(event.getSceneY());
                }
                if (selectedNode != -1) {
                    markNode(selectedNode, 0);
                }
                selectedNode = -1;
                for (int i = 0; i < m; i++) {
                    if (edges.get(i).getSkad() == Integer.parseInt(newNode.getText())) {
                        edges.get(i).updateStart((int) ((int) event.getSceneX() + nodes.get(edges.get(i).getSkad()).getWidth() / 2), (int) ((int) event.getSceneY() + nodes.get(edges.get(i).getSkad()).getHeight() / 2), newNode.getWidth(), digraph);
                    } else if (edges.get(i).getDokad() == Integer.parseInt(newNode.getText())) {
                        edges.get(i).updateEnd((int) ((int) event.getSceneX() + nodes.get(edges.get(i).getDokad()).getWidth() / 2), (int) ((int) event.getSceneY() + nodes.get(edges.get(i).getDokad()).getHeight() / 2), newNode.getWidth(), digraph);
                    }
                }
            }
        });
        super.addW();
        return newNode;
    }

    public boolean addEdge(int from, int to, int value) {
        int x1, y1, x2, y2;
        if (from >= n || to >= n) {
            return false;
        }
        //counting start and end positions
        x1 = (int) (nodes.get(from).getLayoutX() + nodes.get(from).getWidth() / 2);
        y1 = (int) (nodes.get(from).getLayoutY() + nodes.get(from).getHeight() / 2);
        x2 = (int) (nodes.get(to).getLayoutX() + nodes.get(to).getWidth() / 2);
        y2 = (int) (nodes.get(to).getLayoutY() + nodes.get(to).getHeight() / 2);

        //create line with properties
        Line luk = new Line(x1, y1, x2, y2);
        luk.setStrokeWidth(2);

        //label with waga and setting properties
        Text waga = new Text(String.format("%d", value));
        if (digraph) {
            waga.setLayoutX((x1 + x2) / 2 + 0.25 * (x2 - x1));
            waga.setLayoutY((y1 + y2) / 2 + 0.25 * (y2 - y1));
        } else {
            waga.setLayoutX((x1 + x2) / 2);
            waga.setLayoutY((y1 + y2) / 2);
        }
        waga.setScaleX(2);
        waga.setScaleY(2);
        waga.setFill(Color.BLUE);

        //create arrow object with properties
        Image img = new Image("arrow.png");
        ImageView grot = new ImageView(img);

        grot.setScaleX(0.06);
        grot.setScaleY(0.06);
        grot.setLayoutX(x2 - img.getWidth() / 2 + 1 / nodes.get(to).getWidth() * 2 * (x1 - x2));
        grot.setLayoutY(y2 - img.getHeight() / 2 + 1 / nodes.get(to).getHeight() * 2 * (y1 - y2));

        //counting the right angle of arrow
        grot.setRotate(Math.atan2(y2 - y1, x2 - x1) * 180 / 3.14159);

        //adding new objects to scene
        Graphs.addNodeToScene(luk);
        luk.toBack();
        Graphs.addNodeToScene(waga);
        //waga.toBack();
        if (digraph) {
            Graphs.addNodeToScene(grot);
        }
        edges.add(new Edge(luk, waga, grot, from, to, value));

        //set properties of nodes
        markNode(from, 0);
        markNode(to, 0);

        super.addK(from, to, value);
        return true;
    }

    public void removeNode() {
        if (n == 0) {
            return;
        }
        Graphs.removeNodeFromScene(nodes.get(n - 1));
        nodes.remove(n - 1);
        remW();
        ArrayList<Edge> l = new ArrayList<>();
        for (Edge e : edges) {
            if (e.getDokad() == n || e.getSkad() == n) {
                Graphs.removeNodeFromScene(e.getArrow());
                Graphs.removeNodeFromScene(e.getLinia());
                Graphs.removeNodeFromScene(e.getWaga());
                l.add(e);
            }
        }
        for (Edge e : l) {
            remK(e.getSkad(), e.getDokad());
            m--;
        }

    }

    public boolean remEdge(int from, int to) {
        ArrayList<Edge> l = new ArrayList<>();  //list of edges to remove
        boolean outcome = false;
        //checking if edge exists
        if (digraph) {
            for (Edge e : edges) {
                if ((e.getSkad() == from && e.getDokad() == to)) {
                    Graphs.removeNodeFromScene(e.getArrow());
                    Graphs.removeNodeFromScene(e.getLinia());
                    Graphs.removeNodeFromScene(e.getWaga());
                    l.add(e);
                }
            }
        } else {
            for (Edge e : edges) {
                if ((e.getSkad() == from && e.getDokad() == to) || (e.getSkad() == to && e.getDokad() == from)) {
                    Graphs.removeNodeFromScene(e.getArrow());
                    Graphs.removeNodeFromScene(e.getLinia());
                    Graphs.removeNodeFromScene(e.getWaga());
                    l.add(e);
                }
            }
        }
        for (Edge e : l) {
            edges.remove(e);
            remK(e.getSkad(), e.getDokad());
            outcome = true;
        }
        return outcome;
    }

    //function thath asks which node remove and then removes it
    public void removeEdge() {
        if (m == 0) {
            return;
        }

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Usuwanie krawedzi");

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);

        Text text = new Text("Podaj krawedz, ktora chcesz usunac(skad,dokad):");
        dialogVbox.getChildren().add(text);

        TextField field = new TextField();
        field.setMinSize(50, 20);
        field.setMaxSize(100, 20);
        dialogVbox.getChildren().add(field);

        TextField field2 = new TextField();
        field2.setMinSize(50, 20);
        field2.setMaxSize(100, 20);
        dialogVbox.getChildren().add(field2);

        Button ok = new Button("Usun krawedz");
        dialogVbox.getChildren().add(ok);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int from, to;
                from = Integer.parseInt(field.getText());
                to = Integer.parseInt(field2.getText());
                try {
                    if (digraph) {
                        if (remEdge(from, to)) {
                            dialog.close();
                        } else {
                            text.setText("Nie ma takiej krawedzi!");
                        }
                    } else {
                        if (remEdge(from, to) || remEdge(to, from)) {
                            dialog.close();
                        } else {
                            text.setText("Nie ma takiej krawedzi!");
                        }
                    }
                } catch (Exception e) {
                    text.setText("Podaj dane liczbowe!");
                }
            }
        });

        Scene dialogScene = new Scene(dialogVbox, 300, 150);
        dialog.setScene(dialogScene);
        dialog.show();

    }

    //fucntion that ask user for value for edge and then creates it
    public void AskUserForValue(int selectedNode, int destination) {

        //checking if edge exists
        for (Edge e : edges) {
            if ((e.getSkad() == selectedNode && e.getDokad() == destination)) {
                return;
            }
            //condition to graph nieskierowany

            if ((e.getSkad() == destination && e.getDokad() == selectedNode && !digraph)) {
                return;
            }
        }
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Dodawanie krawedzi");

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);

        Text text = new Text("Podaj wage krawedzi:");
        dialogVbox.getChildren().add(text);

        TextField field = new TextField();
        field.setMinSize(50, 20);
        field.setMaxSize(50, 20);
        dialogVbox.getChildren().add(field);

        Button ok = new Button("Dodaj krawedz");
        dialogVbox.getChildren().add(ok);
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int waga;
                try {
                    if (field.getText().length() == 0) {
                        waga = 1;
                    } else {
                        waga = Integer.parseInt(field.getText());
                    }
                } catch (NumberFormatException e) {
                    waga = 1;
                }
                addEdge(selectedNode, destination, waga);
                if (!digraph) {
                    addEdge(destination, selectedNode, waga);
                }
                dialog.close();
            }
        });

        Scene dialogScene = new Scene(dialogVbox, 250, 150);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void addEdgeDialog() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Dodawanie krawedzi");

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);

        Text text = new Text("Podaj krawedz, ktora chcesz dodac(skad, dokad, waga):");
        dialogVbox.getChildren().add(text);

        TextField field = new TextField();
        field.setMinSize(50, 20);
        field.setMaxSize(100, 20);
        dialogVbox.getChildren().add(field);

        TextField field2 = new TextField();
        field2.setMinSize(50, 20);
        field2.setMaxSize(100, 20);
        dialogVbox.getChildren().add(field2);

        TextField field3 = new TextField();
        field3.setMinSize(50, 20);
        field3.setMaxSize(100, 20);
        dialogVbox.getChildren().add(field3);

        Button ok = new Button("Dodaj krawedz");
        dialogVbox.getChildren().add(ok);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int from, to, waga;
                try {
                    from = Integer.parseInt(field.getText());
                    to = Integer.parseInt(field2.getText());
                    waga = Integer.parseInt(field3.getText());
                    if (digraph) {
                        if (addEdge(from, to, waga)) {
                            dialog.close();
                        } else {
                            text.setText("Złe dane!");
                        }
                    } else {
                        if (addEdge(from, to, waga) && addEdge(to, from, waga)) {
                            dialog.close();
                        } else {
                            text.setText("Złe dane!");
                        }
                    }
                } catch (Exception e) {
                    text.setText("Podaj dane liczbowe!");
                }
            }
        });

        Scene dialogScene = new Scene(dialogVbox, 350, 200);
        dialog.setScene(dialogScene);
        dialog.show();

    }

    public void clearAll() {
        selectedNode = -1;
        for (Button b : nodes) {
            Graphs.removeNodeFromScene(b);
        }
        nodes.clear();
        n = 0;
        for (Edge e : edges) {
            Graphs.removeNodeFromScene(e.getArrow());
            Graphs.removeNodeFromScene(e.getLinia());
            Graphs.removeNodeFromScene(e.getWaga());
        }
        edges.clear();
        m = 0;
        super.clear();
    }


    public void ReadGraphDialog() {

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Wczytywanie grafu");

        VBox dialogVbox = new VBox(20);
        dialogVbox.setAlignment(Pos.CENTER);

        Text text = new Text("Wpisz tutaj dane grafu");
        dialogVbox.getChildren().add(text);

        CheckBox bezWag = new CheckBox("Graf bez wag");
        bezWag.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (bezWag.isSelected()) {
                    wagi = false;
                } else {
                    wagi = true;
                }
            }
        });

        dialogVbox.getChildren().add(bezWag);

        TextArea field = new TextArea();
        field.setMinSize(200, 200);
        field.setMaxSize(200, 200);

        dialogVbox.getChildren().add(field);

        Button ok = new Button("Wczytaj");
        dialogVbox.getChildren().add(ok);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearAll();
                try {
                    int tmp;
                    Scanner s = new Scanner(field.getText());
                    tmp = s.nextInt();
                    for (int i = 0; i < tmp; i++) {
                        Graphs.addNodeToScene(addNode());
                    }
                    tmp = s.nextInt();
                    if (s.hasNextLine()) {
                        for (int i = 0; i < tmp; ++i) {
                            int a, b, c;
                            a = b = c = 0;
                            a = s.nextInt();
                            b = s.nextInt();
                            if (wagi) {
                                c = s.nextInt();
                                addEdge(a, b, c);
                                if (!digraph) {
                                    addEdge(b, a, c);
                                }
                            } else {
                                addEdge(a, b, 1);
                                if (!digraph) {
                                    addEdge(b, a, 1);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    text.setText("Złe dane grafu!");
                }

                dialog.close();
            }
        }
        );

        Scene dialogScene = new Scene(dialogVbox, 300, 350);

        dialog.setScene(dialogScene);

        dialog.show();

    }

    //methods for visualisatino algorithms
    public void markEdge(int from, int to, int kolor) {
        for (Edge e : edges) {
            if (e.getSkad() == from && e.getDokad() == to) {
                if (kolor == 0) {
                    e.getLinia().setStyle("-fx-stroke: black;");
                    e.getLinia().setStrokeWidth(2);
                } else if (kolor == 1) {
                    e.getLinia().setStyle("-fx-stroke: red;");
                    e.getLinia().setStrokeWidth(8);
                } else if (kolor == 2) {
                    e.getLinia().setStyle("-fx-stroke: green;");
                    e.getLinia().setStrokeWidth(6);
                }
            }
        }
    }

    public void markNode(int index, int kolor) {
        if (kolor == 0) {
            nodes.get(index).setStyle("-fx-background-color: #ffaa00");
        } else if (kolor == 1) {
            nodes.get(index).setStyle("-fx-background-color: #ff0000");
        } else if (kolor == 2) {
            nodes.get(index).setStyle("-fx-background-color: #00ff00");
        } else if (kolor == 3) {
            nodes.get(index).setStyle("-fx-background-color: #00a1ff");
        } else if (kolor == 4) {
            nodes.get(index).setStyle("-fx-background-color: #aaaaaa");
        }
    }
    
    public void nextStep() {
        Step krok;
        System.out.println("Krok: " + aktStep);
        if (aktStep < kroki.size()) {
            krok = kroki.get(aktStep);
            aktStep++;
        } else {
            aktStep = 0;
            return;
        }
        //przywracanie domyslnych
        for(int i = 0; i < n; ++i){
            markNode(i, 0);
        }
        for(int i = 0; i < m ; ++i){
            markEdge(edges.get(i).getSkad(), edges.get(i).getDokad(), 0);
        }
        if (krok.getOdwiedzW() != null) {
            for (int i = 0; i < krok.getOdwiedzW().size(); ++i) {
                markNode(krok.getOdwiedzW().get(i), 2);            
            }
        }
        if(krok.getOdwiedzK() != null){
           for (int i = 0; i < krok.getOdwiedzK().size(); ++i) {
                markEdge(krok.getOdwiedzK().get(i).getKey(),krok.getOdwiedzK().get(i).getValue() ,2);
                if(!digraph){
                    markEdge(krok.getOdwiedzK().get(i).getValue(),krok.getOdwiedzK().get(i).getKey() ,2);
                }
    
           } 
        }
        
        markNode(krok.getAktW(),3);       
        if(krok.getAktK() != null){
            markEdge(krok.getAktK().getKey(),krok.getAktK().getValue(),1);
            if(!digraph){
                markEdge(krok.getAktK().getValue(),krok.getAktK().getKey(),1);
            }
        }
    }

}
