# Pathfinding algorithms

This is a small project in getting better at Java language, learning various pathfinding algorithms and visualizing how they work.

### Work in progress

- [ ] Make this README more interesting
- [ ] Add non-diagonal pathfinding
- [ ] Change non-existent path check
- [ ] Add screen movement
- [ ] Add speed slider
- [ ] BFS (breadth-first search) algorithm
- [ ] DFS (depth-first search) algorithm
- [ ] Dijkstra's algorithm
- [X] ~~A* (A Star) algorithm~~

### Usage

Run the program and it opens a grid. On the grid you can draw obstacles and select start and end nodes.

|     Input combination    |        Description        |
| ------------------------ | ------------------------- |
| Left mouse button (LMB)  |     Adds border on map    |
|        A + LMB           | Adds starting node on map |
|        S + LMB           |    Adds end node on map   |
| Right mouse button (RMB) |   Removes border on map   |

On bottom left corner is control panel for easy access of small settings for pathfinding algorithm, node information and **running**, **stopping**, **resuming** and **reset** pathfinding algorithm.

### Development

- Used Java 1.8.0.252
- Gradle
