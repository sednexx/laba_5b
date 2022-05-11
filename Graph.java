import java.util.*;

public class Graph {
    class Node implements Comparator<Node> {
        public int node;
        public int cost;

        public Node(int node, int cost) {
            this.node = node;
            this.cost = cost;
        }

        public Node() {}

        @Override
        public int compare(Node node1, Node node2)
        {
            if (node1.cost < node2.cost)
                return -1;
            if (node1.cost > node2.cost)
                return 1;
            return 0;
        }
    }

    ArrayList<Integer> sorted;
    ArrayList<Integer> order;
    ArrayList<Integer> used;
    ArrayList<Integer> cycle;

    public Graph() {
        sorted = new ArrayList<>();
        order = new ArrayList<>();
        used = new ArrayList<>();
    }

    void dfs_list(List<List<Integer>> graph, int current_vertex, int p) {
        used.set(current_vertex, 1);

        for (int i = 0; i < used.size(); i++) {
            if (used.get(i) == null) {
                break;
            }
        }

        for(int i = 0; i < graph.get(current_vertex).size(); i++) {
            int adjacent_vertex = graph.get(current_vertex).get(i);
            if(used.get(adjacent_vertex) == null) {
                dfs_list(graph, adjacent_vertex, p);
            }
        }
        if(p == 0) {
            order.add(current_vertex);
        } if(p == 1) {
            sorted.add(current_vertex);
        }
    }

    List<List<Integer>> reverse(List<List<Integer>> graph) {
        used = new ArrayList<>((graph.size()));
        for(int i = 0; i < graph.size(); i++) {
            used.add(null);
        }
        List<List<Integer>> reverse_graph = new ArrayList<>(graph.size());

        for(int i = 0; i < graph.size(); i++) {
            if(used.get(i) == null) {
                dfs_list(graph, i, 0);
            }
        }

        for(int i = 0; i < order.size()/2; i++) {
//            swap(order.get(i), order.get(order.size() - i - 1));
            Integer tmp = order.get(i);
            order.set(i, order.get(order.size() - i - 1));
            order.set(order.size() - i - 1, tmp);
        }

        for(int i = 0; i < graph.size(); i++) {
            reverse_graph.add(new ArrayList<Integer>());
        }

        for(int i = 0; i < graph.size(); i++) {
            for(int j = 0; j < graph.get(i).size(); j++) {
                reverse_graph.get(graph.get(i).get(j)).add(i);
            }
        }
        return reverse_graph;
    }

    void kosaraju(List<List<Integer>> reverse_graph) {
        System.out.println("\n\nKosaraju\n\n");

        used = new ArrayList<>(reverse_graph.size());
        for(int i = 0; i < reverse_graph.size(); i++) {
            used.add(null);
        }

        for(int i = 0; i < order.size(); i++) {
            if(used.get(order.get(i)) == null) {
                dfs_list(reverse_graph, order.get(i), 1);
                for(int j = 0; j < sorted.size(); j++) {
                    System.out.print(sorted.get(j) + " ");
                }
                System.out.println();
                sorted.clear();
            }
        }
    }

    //*************************************************************

    void tarian_algo(List<List<Integer>> graph, ArrayList<Integer> used, ArrayList<Integer> sorted, int current_vertex) {
        used.set(current_vertex, 1);//grey

        for(int i = 0; i < graph.get(current_vertex).size(); i++) {
            int adjacent_vertex = graph.get(current_vertex).get(i);
            if(used.get(adjacent_vertex) == 1 && adjacent_vertex != current_vertex) {
                throw new NullPointerException("Exception!");
            }
            if(used.get(adjacent_vertex) == 0) {
                tarian_algo(graph, used, sorted, adjacent_vertex);
            }
        }
        sorted.add(current_vertex);
        used.set(current_vertex, 2);
    }

    void tarian_algo(List<List<Integer>> graph) {
        System.out.println("\n\nTarian -->\n\nSorted array: ");
        used = new ArrayList<>((graph.size()));
        for(int i = 0; i < graph.size(); i++) {
            used.add(0);
        }

        try {
            tarian_algo(graph, used, sorted, 0);
            for(int i = sorted.size() - 1; i >= 0; i--) {
                System.out.print(sorted.get(i) + " ");
            }
        } catch (NullPointerException e) {
            System.out.println("Graph has cycles, topo-sort is impossible!");
        }
    }

    //*************************************************************

    boolean continue_algo(List<List<Integer>> graph, List<List<Integer>> reverse_graph) {
        for(int i = 0; i < graph.size(); i++) {     //check if all graph is a strong connection component
            if(graph.get(i).size() % 2 == 1) { return false; }  //if deg of some vertex is odd
            if(used.get(order.get(i)) == null) {
                dfs_list(reverse_graph, order.get(i), 1);
                if(sorted.size() != graph.size()) { return false; }
            }
        }
        return true;
    }

    //************************************************

    void dfs(List<List<Integer>> graph, Map<String, Integer> edges, int current_vertex) {
        for(int i = 0; i < graph.get(current_vertex).size(); i++) {
            int adjacent_vertex = graph.get(current_vertex).get(i);
            int a = current_vertex;
            int b = adjacent_vertex;
            if(a > b) {
                int tmp = a;
                a = b;
                b = tmp;
            }
            String key = Integer.toString(a);
            key = key.concat("-" + Integer.toString(b));

            if(edges.get(key) != null) {
                if(edges.get(key) - 1 == 0) {
                    edges.put(key, null);
                } else {
                    edges.put(key, edges.get(key) - 1);
                }
                dfs(graph, edges, adjacent_vertex);
            }
        }
        cycle.add(current_vertex);
    }

    void fleri_algo(List<List<Integer>> graph, List<List<Integer>> reverse_graph) {
        System.out.println(graph.size() + " " + reverse_graph.size());
        if(!continue_algo(graph, reverse_graph)) { return; }

        System.out.print("\n\nFleri -->\n\nCycle: ");
        cycle = new ArrayList<>();
        Map<String, Integer> edges = new HashMap<>();
        for(int i = 0; i < graph.size(); i++) {
            for(int j = 0; j < graph.get(i).size(); j++) {
                if(i < graph.get(i).get(j)) {
                    String key = Integer.toString(i);
                    key = key.concat("-" + Integer.toString(graph.get(i).get(j)));
//                    i, graph.get(i).get(j)
                    if(edges.containsKey(key)) {
                        edges.put(key, edges.get(key) + 1);
                    } else {
                        edges.put(key, 1);
                    }
                }
            }
        }

        dfs(graph, edges, 0);

        for(int i = 0; i < cycle.size(); i++) {
            System.out.print(cycle.get(i) + 1 + " ");
        }
        System.out.println();
    }

    //*************************************************************

    void find_euler_cycle(List<List<Integer>> graph, int current_vertex) {
        if(graph.isEmpty()) { return; }
        for(int i = 0; i < graph.get(current_vertex).size(); i++) {
            int next = graph.get(current_vertex).get(i);
            graph.get(current_vertex).remove(i);
            for(int k = 0; k < graph.get(next).size(); k++) {
                if(current_vertex == graph.get(next).get(k)) {
                    graph.get(next).remove(k);
                    break;
                }
            }
            find_euler_cycle(graph, next);
        }
        cycle.add(current_vertex);
    }

    void cycle_algo(List<List<Integer>> graph, List<List<Integer>> reverse_graph) {
        if(!continue_algo(graph, reverse_graph)) { return; }

        System.out.print("\n\nEuler cycle --> \n\nCycle: ");
        cycle  = new ArrayList<>();

        find_euler_cycle(graph, 0);

        for(int i = 0; i < cycle.size(); i++) {
            System.out.print(cycle.get(i) + 1 + " ");
        }
        System.out.println();
    }
}
