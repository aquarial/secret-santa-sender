package solver;

import java.util.*;

public class Solver {

    private List<Node> nodes;

    Solver(List<Node> nodes) {
        this.nodes = nodes;
    }


    private void swapNodes(int a, int b) {
        Node tmp = nodes.get(a);
        nodes.set(a, nodes.get(b));
        nodes.set(b, tmp);
    }

    /**
     * Internally computes a possible gift exchange route
     */
    public void generateSolution() {
        Collections.shuffle(nodes);

        int fst = 0;
        for (int round = 0; round < nodes.size() * nodes.size(); round++) {
            int snd = (fst + 1) % nodes.size();
            int thr = (fst + 2) % nodes.size();
            if (nodes.get(fst).groupID == nodes.get(snd).groupID) {
                swapNodes(snd, thr);
            }
            fst = snd;
        }
    }

    /**
     * @return If the gift exchange has a group sending to itself
     */
    public boolean validSolution() {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).groupID == nodes.get((i + 1) % nodes.size()).groupID)
                return false;
        }
        return true;
    }

//    VALIDATE IF THE GIFT EXCHANGE ALGORITHM IS FAIR
//
//    private static void validateSolution(List<solver.Node> nodes) {
//        HashMap<String, Integer> solutions = new HashMap<>();
//        for (int round = 0; round < 100000; round++) {
//            String key = nodes.stream().map(node -> node.groupID).collect(toList()).toString();
//            need to sort list so cycles are obvious
//            solutions.compute(key, (k, v) -> (v == null) ? 1 : v + 1);
//        }
//
//        for (String li : solutions.keySet()) {
//            System.out.println(li + " - " + solutions.get(li));
//        }
//        System.out.println(solutions.size());
//    }

}
