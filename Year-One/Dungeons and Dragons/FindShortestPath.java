public class FindShortestPath {

    public static void main (String[] args) {
        try {
            if (args.length < 1) throw new Exception("No input file specified");
            //Checks if an file it input, if not it throws an exception
            String dungeonFileName = args[0];
            Dungeon dungeon = new Dungeon(dungeonFileName);
            // Creates a string containing the dungeon file name along with a dungeon object called dungeon

            DLPriorityQueue<Hexagon> Queue = new DLPriorityQueue<>();
            Hexagon start = dungeon.getStart();
            Queue.add(start, 0);
            start.markEnqueued();
            // Creates a priority queue object called queue of type Hexagon then creates a start hexagon and adds it to the queue with the highest priority
            // Then sets the start value to be considered in queue

            while (Queue.isEmpty() == false) {
                Hexagon current = Queue.removeMin();
                current.markDequeued();
                //Checks if the queue is empty, if not continues, otherwise exits the loop
                // Creates a hexagon object called current which is equal to the queue's recently removed value and then marks it dequeued

                if (current.isExit()) {
                    int penis = current.getDistanceToStart() + 1;
                    System.out.println("Path of length: "+penis);
                    return;
                    // Checks if the current value is the exit, if so exits the entire method preventing the "No path found" statement found later
                }
                else if (current.isDragon() || current.getType() == Hexagon.HexType.DRAGON) {
                    continue;
                    // Checks if the current node is a dragon or if any of its neigbours are a dragon node, if so restarts the while loop
                }
                for (int i = 0; i < 6; i++) {
                    if (current.getNeighbour(i) != null && !current.getNeighbour(i).isDragon() && !current.getNeighbour(i).isMarkedDequeued() && !current.getNeighbour(i).isWall()) {
                        //  considers each one of the neighbouring chambers to the current one that are not null and not of type wall and have not been marked as dequeued
                    	int D = 1 + current.getDistanceToStart();
                    	// Creates variable D that has the distance of the current nde to the initial one
                        if (current.getNeighbour(i).getDistanceToStart() > D) {
                            current.getNeighbour(i).setDistanceToStart(D);
                            current.getNeighbour(i).setPredecessor(current);
                            // if the If distance of neighbour to initial chamber is larger than D then set the distance of neighbour to the initial chamber to D 
                            if (current.getNeighbour(i).isMarkedEnqueued()) {
                            	// Checks If neighbour is marked as enqueued and the distance from neighbour to the initial chamber was modified in the previous step
                                Queue.updatePriority(current.getNeighbour(i), D + current.getNeighbour(i).getDistanceToExit(dungeon));
                                // Updates the priority of the neighbour in the queue to the priority of D + the neightbour's distance to the exit
                            }
                            else if (!current.getNeighbour(i).isMarkedEnqueued()) {
                            	// Checks if neighbour is not marked as enqueued then add it to the queue with priority equal to its distance to the initial chamber
                                Queue.add(current.getNeighbour(i), current.getNeighbour(i).getDistanceToStart() + current.getNeighbour(i).getDistanceToExit(dungeon));
                                current.getNeighbour(i).markEnqueued();
                                // adds the neighbour chamber to the queue with priority equal to its distance to the initial chamber then marks it enqueued
                            }
                        }

                    }
                }
            }
            System.out.println("No path found");
             // Only through if no exit is found due to the return statement in the 1st if statement inside the while loop

            

        }    
        
        catch (InvalidNeighbourIndexException e) {
        	System.out.println("Invalid index for neighbour must be 0-5");
        	// Catches invalid index for neighbour exception
        }
        catch (InvalidDungeonCharacterException e) {
        	System.out.println("Invalid character within input file name");
        	// Catches invalid dungeon charcter in name exception
        }
        catch (InvalidElementException e) {
        	System.out.println("InvalidElement Exception: "+ e.getMessage());
        	// Catches invalid element exception
        }
        catch (EmptyPriorityQueueException e) {
        	System.out.println("EmptyPriorityQueueException Exception: "+ e.getMessage());
        	// Catches empty priority queue exception
        }
        catch (Exception e) {
        	System.out.println("Exception: "+ e.getMessage());
        	// Catches exception
        }
    }
}
