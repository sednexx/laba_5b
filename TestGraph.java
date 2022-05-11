import java.util.ArrayList;
import java.util.List;

public class TestGraph {

    public static void main(String[] args) {
        List<List<Integer>> graph = new ArrayList<>();
//        {
//            {1, 4},
//            {0, 2, 4, 5},
//            {1, 5},
//            {4, 5},
//            {0, 1, 3, 5},
//            {1, 2, 3, 4}
//        };
        int V = 6;
        for (int i = 0; i < V; i++) {
            ArrayList<Integer> item = new ArrayList<>();
            graph.add(item);
        }
        graph.get(0).add(1);
        graph.get(0).add(4);

        graph.get(1).add(0);
        graph.get(1).add(2);
        graph.get(1).add(4);
        graph.get(1).add(5);

        graph.get(2).add(1);
        graph.get(2).add(5);

        graph.get(3).add(4);
        graph.get(3).add(5);

        graph.get(4).add(0);
        graph.get(4).add(1);
        graph.get(4).add(3);
        graph.get(4).add(5);

        graph.get(5).add(1);
        graph.get(5).add(2);
        graph.get(5).add(3);
        graph.get(5).add(4);

//        graph.get(0).add(4);
//        graph.get(0).add(5);
//
//        graph.get(1).add(3);
//
//        graph.get(2).add(3);
//
//        graph.get(3).add(1);
//        graph.get(3).add(2);
//        graph.get(3).add(9);
//
//        graph.get(4).add(0);
//
//        graph.get(5).add(0);
//
//        graph.get(7).add(8);
//
//        graph.get(8).add(7);
//
//        graph.get(9).add(3);



        for (int i = 0; i < graph.size(); i++) {         //show graph
            System.out.print("{ ");
            for (int j = 0; j < graph.get(i).size(); j++) {
                System.out.print(graph.get(i).get(j) + " ");
            }
            System.out.println("}");
        }
        System.out.println();
        Graph g = new Graph();

        //-_-_-_-_-_-_-_-_-_-_-_-_-_-_Kosaraju-_-_-_-_-_-_-_-_-_-_-_-_-_-_

        List<List<Integer>> reverse_graph = g.reverse(graph);
        for (int i = 0; i < reverse_graph.size(); i++) {         //show graph
            System.out.print("{ ");
            for (int j = 0; j < reverse_graph.get(i).size(); j++) {
                System.out.print(reverse_graph.get(i).get(j) + " ");
            }
            System.out.println("}");
        }
//        System.out.println("Graph(0) size = " + graph.get(0).size());

        g.kosaraju(reverse_graph);
//        System.out.println("Graph(0) size = " + graph.get(0).size());

        //-_-_-_-_-_-_-_-_-_-_-_-_-_-_Tarian-_-_-_-_-_-_-_-_-_-_-_-_-_-_

        g.tarian_algo(graph);
//        System.out.println("Graph(0) size = " + graph.get(0).size());


        //-_-_-_-_-_-_-_-_-_-_-_-_-_-_Fleri-_-_-_-_-_-_-_-_-_-_-_-_-_-_

        g.fleri_algo(graph, reverse_graph);
//        System.out.println("Graph(0) size = " + graph.get(0).size());

       //-_-_-_-_-_-_-_-_-_-_-_-_-_-_Euler cycle another realisation-_-_-_-_-_-_-_-_-_-_-_-_-_-_

        g.cycle_algo(graph, reverse_graph);
//        System.out.println("Graph(0) size = " + graph.get(0).size());

    }
}
