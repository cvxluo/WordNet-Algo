/**
 * WordNet class giving distances and ancestors for synsets given
 * Aditya Prasad - Charlie Luo - Grace Zhang
 * 5/3/19
 
 Input:
 java WordNet synsets.txt hypernyms.txt
 
 Output:
 1
 equine equid
 
 3
 furniture piece_of_furniture article_of_furniture
 
 
 Notes:
 WordNet does not make the assumption that nouns only appear once- if a noun appears in different synsets, getIDFromNoun handles it
 Relies on synsets.txt and hypernyms.txt from the website
 SAP is a dependency
 
 
 */

import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("unchecked")
public class WordNet {
    
    /**
     * Custom class to store synsets more easily
     */
    private class Synset {
        int id;
        String nouns[];
        String gloss;
        
        public Synset (int id, String[] nouns, String gloss) { this.id = id; this.nouns = nouns; this.gloss = gloss; }
        
    }
    
    private ArrayList<Synset> syns;
    private Digraph graph;
    private SAP saps;
    private ArrayList<String> nouns;
    
    
    /**
     * Constructor that creates the digraph from the hypernyms and a list of synsets from the synset inputs
     */
    public WordNet(String synsets, String hypernyms) {
        In synInput = new In(synsets);
        In hyperInput = new In(hypernyms);
        
        nouns = new ArrayList<String>();
        syns = new ArrayList<Synset>();
        
        int count = 0;
        while (!synInput.isEmpty()) {
            String line = synInput.readLine();
            String[] input = line.split(",");
            int id = Integer.parseInt(input[0]);
            String[] synNouns = input[1].split(" ");
            for (String noun : synNouns) {
                nouns.add(noun);
            }
            String gloss = input[2];
            
            Synset syn = new Synset(id, synNouns, gloss);
            syns.add(syn);
            count++;
        }
        
        Collections.sort(nouns);
        
        
        graph = new Digraph(count);
        
        while (!hyperInput.isEmpty()) {
            String line = hyperInput.readLine();
            String[] input = line.split(",");
            int v = Integer.parseInt(input[0]);
            
            for (int w = 1; w < input.length; w++) {
                graph.addEdge(v, Integer.parseInt(input[w]));
            }
            
        }
        
        saps = new SAP(graph);
        
    }
    
    /**
     * Returns all the WordNet nouns
     */
    public Iterable<String> nouns() { return nouns; }
    
    
    /**
     * Checks nouns to see if a noun exists
     */
    public boolean isNoun(String word) { return Collections.binarySearch(nouns, word) >= 0; }
    
    
    /**
     * Gets every vertex that contains a particular noun string
     */
    private ArrayList<Integer> getIDFromNoun (String noun) {
    
        ArrayList a = new ArrayList<Integer>();
        for (Synset syn : syns) {
            for (String n : syn.nouns) {
                if (noun.equals(n)) a.add(syn.id);
            }
        }
        return a;
    }
    
    /**
     * Get shortest distance between 2 nouns
     * Note that if multiple synsets have the same noun, the shortest path between all vertices with that noun and the other will be selected
     * Uses SAP digraph
     */
    public int distance(String nounA, String nounB) { return saps.length(getIDFromNoun(nounA), getIDFromNoun(nounB)); }
    
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    
    /**
     * Get common ancestor on the shortest path between 2 nouns
     * Runs the same way as distance does
     * Uses SAP digraph
     */
    public String sap(String nounA, String nounB) { return String.join(" ", syns.get(saps.ancestor(getIDFromNoun(nounA), getIDFromNoun(nounB))).nouns); }
    
    
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
    
        System.out.println(wordnet.distance("horse", "equine"));
        System.out.println(wordnet.sap("horse", "equine"));
        System.out.println();
        
        /*
        System.out.println("Nouns:");
        for (String noun : wordnet.nouns()) {
            System.out.println(noun);
        }
        System.out.println();
        */
    
   
        // For hypernyms15Tree and synsets15
        System.out.println(wordnet.distance("table", "chair"));
        System.out.println(wordnet.sap("table", "chair"));
        System.out.println();
    
    }
}