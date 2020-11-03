Sudoku
======

[JL coding challenge 21](https://coding-challenges.jl-engineering.net/challenges/challenge-21/)

------

## Instructions

In this challenge you wll need to solve Sudoku puzzles.

A Sudoku grid consists of 9 columns and 9 rows.
The grid is broken into nine regions, each with three columns and three rows.

Each position on a complete Sudoku grid must contain a number between 1 and 9 such that every column and every row on the grid contains every number between 1 and 9 and every region contains every number between 1 and 9.

A Sudoku puzzle is a grid with numbers removed and to solve the puzzle you need to determine the value of every missing number until the grid is complete.


In this challenge you are required to create a function that adds missing numbers to a Sudoku grid until the grid is complete or there are only grid positions left that could contain more than one value.

The function will take an array (or an ordered list) of 81 numbers as input and write out a list of 81 numbers with a value between 0 and 9. Each sublist of 9 numbers represents a row on the grid. Value 0 represents an empty grid position.

------

## Bonus

As a bonus improve the function so that it returns all complete solutions. This will return an array of arrays of 81 numbers, returning an empty array if there is no solution.

------

## Sample

Sample data for a simple Sudoku:
```
7,0,9,0,0,2,6,8,0,
0,0,2,0,5,0,7,0,4,
0,0,0,0,0,0,2,0,0,
1,9,0,0,0,7,0,6,0,
8,6,7,1,9,5,0,4,0,
5,0,4,0,0,0,0,9,0,
4,3,5,7,8,0,0,2,0,
0,0,6,4,0,0,0,0,1,
9,8,0,5,0,6,0,0,3
```

Sample data for an unsolvable Sudoku:
```
7,8,1,5,4,3,9,2,6,
0,0,6,1,7,9,5,0,0,
9,5,4,6,2,8,7,3,1,
6,9,5,8,3,7,2,1,4,
1,4,8,2,6,5,3,7,9,
3,2,7,9,1,4,8,0,0,
4,1,3,7,5,2,6,9,8,
0,0,2,0,0,0,4,0,0,
5,7,9,4,8,6,1,0,3
```

Sample data for a hard Sudoku:
```
0,0,0,0,7,4,3,1,6,
0,0,0,6,0,3,8,4,0,
0,0,0,0,0,8,5,0,0,
7,2,5,8,0,0,0,3,4,
0,0,0,0,3,0,0,5,0,
0,0,0,0,0,2,7,9,8,
0,0,8,9,4,0,0,0,0,
0,4,0,0,8,5,9,0,0,
9,7,1,3,2,6,4,8,5
```

Sample data for a Sudoku with more than one solution:
```
0,8,0,0,0,9,7,4,3,
0,5,0,0,0,8,0,1,0,
0,1,0,0,0,0,0,0,0,
8,0,0,0,0,5,0,0,0,
0,0,0,8,0,4,0,0,0,
0,0,0,3,0,0,0,0,6,
0,0,0,0,0,0,0,7,0,
0,3,0,5,0,0,0,8,0,
9,7,2,4,0,0,0,5,0
```

Solved puzzle
```
7,2,3,8,4,6,1,5,9,
6,1,5,3,9,2,4,7,8,
8,4,9,7,1,5,6,3,2,
3,7,8,6,5,4,9,2,1,
1,9,4,2,8,7,3,6,5,
2,5,6,9,3,1,8,4,7,
5,6,1,4,7,9,2,8,3,
4,8,7,1,2,3,5,9,6,
9,3,2,5,6,8,7,1,4
```


[Example grids](http://www.sudoku-download.net/files/Solution_60_Sudokus_Pattern_Easy.pdf) 