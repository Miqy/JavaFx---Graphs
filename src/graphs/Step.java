/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphs;

import java.util.ArrayList;
import javafx.util.Pair;

/**
 *
 * @author x
 */
public class Step {

    //pola potrzebne do wizualizacji dzialania poszczegolnych algorytmow
    private ArrayList<Integer> odwiedzW = new ArrayList<>();

    public ArrayList<Integer> getOdwiedzW() {
        return odwiedzW;
    }

    public ArrayList<Pair<Integer, Integer>> getOdwiedzK() {
        return odwiedzK;
    }

    public int getAktW() {
        return aktW;
    }

    public Pair<Integer, Integer> getAktK() {
        return aktK;
    }
    private ArrayList<Pair<Integer, Integer>> odwiedzK = new ArrayList<Pair<Integer, Integer>>();
    private int aktW;
    private Pair<Integer, Integer> aktK;

    Step(int aktW, Pair<Integer, Integer> aktK, ArrayList<Integer> W, ArrayList<Pair<Integer, Integer>> K) {
        this.aktW = aktW;
        this.aktK = aktK;
        odwiedzW = W;
        odwiedzK = K;
    }

    public String toString() {
        if (aktK != null) {
            return String.format("%d %d %d", aktW, aktK.getKey(), aktK.getValue());
        } else {
            return String.format("%d %d %d", aktW, -1, -1);
        }
    }
}
