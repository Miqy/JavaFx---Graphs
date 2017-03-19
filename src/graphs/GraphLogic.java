
package graphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.util.Pair;

public class GraphLogic {
    protected int n;
    protected int m;
    protected boolean digraph;
    private ArrayList<ArrayList<Pair<Integer, Integer>>> lista = new ArrayList<>(); 
    
    protected ArrayList<Step> kroki = new ArrayList<>();
    protected int aktStep;
    
    public GraphLogic(){
        digraph = true;
        aktStep = 0;
    }
    
    public void addW(){
        lista.add(new ArrayList<Pair<Integer, Integer>>());
        n++;
    }
    
    public void remW(){
        n--;
        lista.remove(n);
    }
    
    public void addK(int from, int to, int value){
        if(from > n || to > n){
            return;
        }
        lista.get(from).add(new Pair(to,value));
        m++;
    }
    
    public void remK(int from, int to){
        for(int i = 0; i < lista.size(); ++i){
            for(int j = 0; j < lista.get(i).size(); ++j){
                if(i == from && lista.get(i).get(j).getKey() == to){
                    lista.get(i).remove(j);
                    m--;
                }
            }
        }
    }
    public void BreadthFirstSearch(int s, int t){
        if(s > n || s < 0 || t < 0 || t > n){
            return;
        }
        //for algorithm visualization
        kroki.clear();
        aktStep = 0;
        
        ArrayList<Integer> Wki = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> Krawedzie = new ArrayList<>();
        //droga
        ArrayList<Integer> droga = new ArrayList<>();
        //tablica ojcow i odwiedzin
        boolean []d = new boolean[n];
        int []f = new int[n];
        //kolejka nie dziala
        LinkedList<Integer> kolejka = new LinkedList<>();
        for(int i = 0; i < n; i++){
            d[i] = false;
            f[i] = -1;
        }
        d[s] = true;
        kolejka.addLast(s);
        //dla wizualizacji
        kroki.add(new Step(s, null, null, null));
        Wki.add(s);
      
        //glowna petla algorytmu
        ArrayList<Integer> a = new ArrayList<>(); 
        ArrayList<Pair<Integer, Integer>> b = new ArrayList<>();
        while(!kolejka.isEmpty()){
            int tmp = kolejka.getFirst();
            /*for visualisation*/
            Wki.add(tmp);
            a = new ArrayList<>(Wki);
            kroki.add(new Step(tmp, null, a, b));
            /**/
            kolejka.removeFirst();
            for(int i = 0; i < lista.get(tmp).size(); i++){
                if(!d[lista.get(tmp).get(i).getKey()]){
                    kolejka.addLast(lista.get(tmp).get(i).getKey());
                    f[lista.get(tmp).get(i).getKey()] = tmp;
                    d[lista.get(tmp).get(i).getKey()] = true;
                }
                Krawedzie.add(new Pair(tmp, lista.get(tmp).get(i).getKey()));
                b = new ArrayList<>(Krawedzie);
                kroki.add(new Step(tmp,new Pair(tmp, lista.get(tmp).get(i).getKey()),a,b));
            }
        }  
        int tmp = t;
        droga.add(droga.size(), tmp);
        do{
           tmp = f[tmp];
           droga.add(droga.size(), tmp);
        }while(tmp != s);
        for(int i = 0; i < droga.size(); ++i){
            System.out.print(droga.get(i));
            if(droga.get(i) != s){
                System.out.print("->");
            }
        }
        for(int i = 0; i < kroki.size(); ++i){
            System.out.println(kroki.get(i));
        }
    }
    
    
    public void clear(){
        lista.clear();
    }
    
    public void printGraph(){
        for(int i = 0; i < n; i++){
            for (Iterator it = lista.get(i).iterator(); it.hasNext();) {
                Pair p = (Pair) it.next();
                System.out.println(i + "->" + p.getKey() + ":" + p.getValue());
            }
        }
    }
}
