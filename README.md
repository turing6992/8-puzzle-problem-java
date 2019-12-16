# 8-puzzle-problem-java
Java implementation of the 8 puzzle problem using Breadth first search

The process:

* The inital state of the pieces in an 8 buzzle block is taken as the root node.

* Then the root node is passed into an unprocessed queue.

* From there a copy of the intial state is proceesd by pushing the empty block in all possible directions.

* The moved matrices becomes the child node of the initial state.

* The children are then pushed to the unprocessed list and the above process is carried out

* This process is repeated until the goal state is reached.

* Once he goal state is reached, the route for the solution is backtraced from the goal node to the root node.

* it is then passed as a Vector to the main class and the solution is displayed step by step

