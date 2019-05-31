/**
 * Determine which words are outcasts in an input
 * Aditya Prasad - Charlie Luo - Grace Zhang
 * 5/3/19
 
 Input:
 java Outcast synsets.txt hypernyms.txt outcast5.txt outcast8.txt outcast11.txt
 
 Output:
 outcast5.txt: table
 outcast8.txt: bed
 outcast11.txt: potato
 
 
 Notes:
 Uses WordNet's distance function- if multiple synsets contain the same noun, Outcast will find the shortest distance between all synsets with that noun
 WordNet is a dependency
 SAP is a dependency
 
 */

public class Outcast {
    
    private WordNet wordnet;
    
    public Outcast(WordNet wordnet) { this.wordnet = wordnet; }
    
    /**
     * Given an array of WordNet nouns, return an outcast
     * Finds all distances between the nouns, computes each noun's total distance, and finds the noun with the smallest distance
     * @param nouns - array of nouns given
     */
    public String outcast(String[] nouns) {
        
        int max = 0;
        int maxDist = 0;
        int[] distances = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            // System.out.println(nouns[i]);
            for (int j = 0; j < nouns.length; j++) {
                int d = wordnet.distance(nouns[i], nouns[j]);
                // System.out.println("to " + nouns[j] + " " + d);
                // System.out.println("ancestor: " + wordnet.sap(nouns[i], nouns[j]));
                distances[i] += d;
            }
            // System.out.println("TOTAL: " + distances[i]);
            if (distances[i] > maxDist) { max = i; maxDist = distances[i]; }
        }
        
        /*
        for (int i = 0; i < distances.length; i++) {
            System.out.println(nouns[i]);
            System.out.println(distances[i]);
        }
        */
        return nouns[max];
    
    }
    
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}