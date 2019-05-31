/**
 * Structure to get shortest ancestral path from a digraph
 * Aditya Prasad - Charlie Luo - Grace Zhang
 * 5/3/19
 
 Notes:
 DAG check is implemented with a DFS
 Ancestral path is implemented with 2 DFS, marking distances, and finding the shortest one
 Ancestor is implemented the same way as length
 
 SAP makes the assumption that every given node is unique, that vertices will never have overlapping elements
 */

import java.util.LinkedList;

public class SAP {
    
    private Digraph graph;
    
    // constructor takes a digraph (not necessarily a DAG)
    public SAP (Digraph G) { this.graph = G; }
    
    
    /**
     * Helper function to perform DFS for DAG check
     *
     * @param v - vertex to be searched
     * @param marked - array of visited vertices
     * @return if at any point DFS reaches a vertex that has already been visited, return false, since a cycle has been discovered
     */
    private boolean dfs(int v, boolean[] marked, boolean[] onStack) {
        marked[v] = true;
        onStack[v] = true;
        
        for (int e : graph.adj(v)) {
            if (onStack[e]) return false;
            if (!marked[e]) if (!dfs(e, marked, onStack)) return false;
        }
        
        return true;
    }
    
    
    /**
     * Check DAG status - does a DFS on unmarked nodes, marking sections as acyclic and combining them
     */
    public boolean isDAG() {
        boolean[] marked = new boolean[graph.V()];
        
        for (int v = 0; v < graph.V(); v++) {
            boolean[] stack = new boolean[graph.V()];
            if (!marked[v]) if (!dfs(v, marked, stack)) return false;
        }
        
        return true;
    }
    
    /**
     * If a DAG is a DAG, it must have a single root- check outdegree
     */
    public boolean isRootedDAG() {
        
        int roots = 0;
        for (int v = 0; v < graph.V(); v++) {
            if (graph.outdegree(v) == 0) roots++;
        }
        
        if (roots == 1) return isDAG();
        else return false;
    }
    
    
    /**
     * Length of shortest ancestral path between v and w; -1 if no such path
     * Performs a BFS from two unique nodes (length 0 if not unique)
     * @param v & w - vertices to search distance from
     * @return shortest length from common ancestor, -1 otherwise
     */
    public int length(int v, int w) {
    
        int[] distances = new int[graph.V()];
        boolean[] visitedV = new boolean[graph.V()];
        boolean[] visitedW = new boolean[graph.V()];
    
        if (v == w) return 0;
        
        LinkedList<Integer> toSearch = new LinkedList<>();
        toSearch.add(v);
        toSearch.add(w);
        visitedV[v] = true;
        visitedW[w] = true;
    
    
        while (!toSearch.isEmpty()) {
            int s = toSearch.pop();
            for (int e : graph.adj(s)) {
                if (visitedV[s]) visitedV[e] = true;
                if (visitedW[s]) visitedW[e] = true;
            
                // Found common ancestor - must be shortest path since BFS
                if (visitedV[e] && visitedW[e]) return distances[e] + distances[s] + 1;
                else if (distances[e] > distances[s] + 1 || distances[e] == 0) { distances[e] = distances[s] + 1; toSearch.add(e); }
            }
        }
    
    
        return -1;
    }
    
    /**
     * A common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
     * Performs a BFS from 2 unique nodes (ancestor is the node, otherwise)
     * @param v & w - vertices to find the common ancestor from
     * @return the index of the common ancestor, -1 otherwise
     */
    public int ancestor(int v, int w) {
    
        int[] distances = new int[graph.V()];
        boolean[] visitedV = new boolean[graph.V()];
        boolean[] visitedW = new boolean[graph.V()];
    
        if (v == w) return v;
    
        LinkedList<Integer> toSearch = new LinkedList<>();
        toSearch.add(v);
        toSearch.add(w);
        visitedV[v] = true;
        visitedW[w] = true;
    
    
        while (!toSearch.isEmpty()) {
            int s = toSearch.pop();
            for (int e : graph.adj(s)) {
                if (visitedV[s]) visitedV[e] = true;
                if (visitedW[s]) visitedW[e] = true;
            
                // Found common ancestor - must be shortest path since BFS
                if (visitedV[e] && visitedW[e]) return e;
                else if (distances[e] > distances[s] + 1 || distances[e] == 0) { distances[e] = distances[s] + 1; toSearch.add(e); }
            }
        }
    
    
        return -1;
        
    }
    
    /**
     * Length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
     * Performs a BFS from a combined pool of origins
     * The first intersection between a path of v and a path of w is the shortest length and is returned
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        
        int[] distances = new int[graph.V()];
        boolean[] visitedV = new boolean[graph.V()];
        boolean[] visitedW = new boolean[graph.V()];
    
        LinkedList<Integer> toSearch = new LinkedList<>();
        for (int i : v) { toSearch.add(i); visitedV[i] = true; }
        for (int j : w) {
            toSearch.add(j);
            if (visitedV[j]) return 0;
            visitedW[j] = true;
        }
        
        
        while (!toSearch.isEmpty()) {
            int s = toSearch.pop();
            for (int e : graph.adj(s)) {
                if (visitedV[s]) visitedV[e] = true;
                if (visitedW[s]) visitedW[e] = true;
                
                // Found common ancestor - must be shortest path since BFS
                if (visitedV[e] && visitedW[e]) return distances[e] + distances[s] + 1;
                else if (distances[e] > distances[s] + 1 || distances[e] == 0) { distances[e] = distances[s] + 1; toSearch.add(e); }
            }
        }
    
    
        return -1;
    }
    
    /**
     * A common ancestor that participates in shortest ancestral path; -1 if no such path
     * Performs a BFS from a combined pool of origins
     * The first intersection between a path of v and a path of w is the common ancestor and is returned
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    
        int[] distances = new int[graph.V()];
        boolean[] visitedV = new boolean[graph.V()];
        boolean[] visitedW = new boolean[graph.V()];
    
        LinkedList<Integer> toSearch = new LinkedList<>();
        for (int i : v) { toSearch.add(i); visitedV[i] = true; }
        for (int j : w) {
            toSearch.add(j);
            if (visitedV[j]) return j;
            visitedW[j] = true;
        }
    
    
        while (!toSearch.isEmpty()) {
            int s = toSearch.pop();
            for (int e : graph.adj(s)) {
                if (visitedV[s]) visitedV[e] = true;
                if (visitedW[s]) visitedW[e] = true;
            
                // Found common ancestor - must be shortest path since BFS
                if (visitedV[e] && visitedW[e]) return e;
                else if (distances[e] > distances[s] + 1 || distances[e] == 0) { distances[e] = distances[s] + 1; toSearch.add(e); }
            }
        }
    
    
        return -1;
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        System.out.println(G);
        System.out.println("Is a DAG: " + sap.isDAG());
        System.out.println("Is a rooted DAG: " + sap.isRootedDAG());
        /*
        System.out.println("Distance between 3 and 10: " + sap.length(3, 10)); */
        
    }
}