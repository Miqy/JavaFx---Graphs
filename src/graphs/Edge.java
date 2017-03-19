/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 * @author x
 */
public class Edge {

    private Text waga;
    private ImageView arrow;
    private int skad;
    private int dokad;
    private int value;

    Edge(Line linia, Text tekst, ImageView arrow, int skad, int dokad, int value) {
        this.linia = linia;
        this.waga = tekst;
        this.arrow = arrow;
        this.skad = skad;
        this.dokad = dokad;
        this.value = value;
    }

    public void updateStart(int x, int y, double size, boolean digraph) {
        linia.setStartX(x);
        linia.setStartY(y);
        if (digraph) {
            waga.setLayoutX((x + linia.getEndX()) / 2 + 0.25 * (linia.getEndX() - x));
            waga.setLayoutY((y + linia.getEndY()) / 2 + 0.25 * (linia.getEndY() - y));
        } else {
            waga.setLayoutX((x + linia.getEndX()) / 2);
            waga.setLayoutY((y + linia.getEndY()) / 2);
        }
        arrow.setLayoutX(linia.getEndX() - arrow.getImage().getWidth() / 2 + 1 / size * (linia.getStartX() - linia.getEndX()));
        arrow.setLayoutY(linia.getEndY() - arrow.getImage().getHeight() / 2 + 1 / size * (linia.getStartY() - linia.getEndY()));
        //counting the right angle of arrow
        arrow.setRotate(Math.atan2(linia.getEndY() - y, linia.getEndX() - x) * 180 / 3.14159);
    }

    public void updateEnd(int x, int y, double size, boolean digraph) {
        linia.setEndX(x);
        linia.setEndY(y);
        if (digraph) {
            waga.setLayoutX((x + linia.getStartX()) / 2 + 0.25 * (x - linia.getStartX()));
            waga.setLayoutY((y + linia.getStartY()) / 2 + 0.25 * (y - linia.getStartY()));
        } else {
            waga.setLayoutX((x + linia.getStartX()) / 2);
            waga.setLayoutY((y + linia.getStartY()) / 2);
        }
        arrow.setLayoutX(linia.getEndX() - arrow.getImage().getWidth() / 2 + 1 / size * (linia.getStartX() - linia.getEndX()));
        arrow.setLayoutY(linia.getEndY() - arrow.getImage().getHeight() / 2 + 1 / size * (linia.getStartY() - linia.getEndY()));
        //counting the right angle of arrow
        arrow.setRotate(Math.atan2(linia.getEndY() - linia.getStartY(), linia.getEndX() - linia.getStartX()) * 180 / 3.14159);
    }

    public int getSkad() {
        return skad;
    }

    public int getDokad() {
        return dokad;
    }

    private Line linia;

    public Line getLinia() {
        return linia;
    }

    public Text getWaga() {
        return waga;
    }

    public ImageView getArrow() {
        return arrow;
    }
}
