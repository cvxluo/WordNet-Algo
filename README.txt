/******************************************************************************
 *  Name: Aditya Prasad, Charlie Luo, Grace Zhang
 *
 *  Hours to complete assignment (optional): -1
 *
 ******************************************************************************/

Programming Assignment 6: WordNet


/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in synsets.txt. Why did you make this choice?
 *****************************************************************************/
We used an ArrayList storing instances of a custom Synset class. Because there is a decent amount of data for each synset,
we felt that a custom class would more effectively represent a synset rather than a series of arrays. The ArrayList was used
to work with synsets.txt and hypernyms.txt- because they didn't specify the number of synsets or edges, so memory had to be
allocated dynamically.


/******************************************************************************
 *  Describe concisely the data structure(s) you used to store the
 *  information in hypernyms.txt. Why did you make this choice?
 *****************************************************************************/
A digraph was used to store hypernyms information, because each synset could be represented as a node and each hypernym
link could be represented as an edge. Thus, DFS and other graphical abstractions could be applied to the data.


/******************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithms as a function of the number of vertices V and the
 *  number of edges E in the digraph?
 *****************************************************************************/

Description:
Go through each vertex of the graph. If a vertex has an outdegree of 0, that node must be a root. Thus, if the graph is a
DAG, then the graph is a rooted digraph.

Order of growth of running time:
O(V + E)
Going through all vertices and getting outdegree is O(V + E)
Running a DFS to check if the graph is a DAG is O(V)
O(V + V + E) is about O(V + E)



/******************************************************************************
 *  Describe concisely your algorithm to compute the shortest common
 *  ancestor in ShortestCommonAncestor. For each method, what is the order of
 *  growth of the worst-case running time as a function of the number of
 *  vertices V and the number of edges E in the digraph? For each method,
 *  what is the order of growth of the best-case running time?
 *
 *  If you use hashing, you should assume the uniform hashing assumption
 *  so that put() and get() take constant time.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't
 *  forget to count the time needed to initialize the marked[],
 *  edgeTo[], and distTo[] arrays.
 *****************************************************************************/

Description:
Check if v and w are the same node- if they are, return 0
Otherwise, run a BFS starting from both v and w, keeping track of nodes visited by v and nodes visited by w separately
If a node is visited by both v and w, that node must be the common ancestor, and the distance from both nodes can be summed
For the Iterables, we simply add more nodes to v and w, making sure no node from v and w are the same

                                              running time
method                               best case            worst case
------------------------------------------------------------------------
length(int v, int w)                   O(1)                 O(V)


ancestor(int v, int w)                 O(1)                 O(V)

length(Iterable<Integer> v,            O(1)                 O(V)
       Iterable<Integer> w)

ancestor(Iterable<Integer> v,          O(1)                 O(V)
         Iterable<Integer> w)




/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
Because nouns can be in multiple synsets, we have to search the entire list of nouns for every synset containing the noun.
Because of some liberal use of ArrayLists, SuppressWarnings is sometimes required for ArrayList use.


Requires:
- Bag
- Digraph
- In
- StdOut
- StdIn
- Stack
from Princeton libraries

Uses:
- Linked List (from STL)



/******************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 *****************************************************************************/
we love chris


/******************************************************************************
 *  Describe any serious problems you encountered.
 *****************************************************************************/
Before we realized nouns could be part of multiple synsets, Outcast didn't work at all. Dealing with those edge cases
resulted in the code becoming much more confusing to read, but ultimately worked.
We initially didn't realize BFS would be a better option to find length and ancestor.