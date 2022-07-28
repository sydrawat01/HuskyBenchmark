# :dog2: Husky Benchmarking

## Quick Sort: Analysis

Do you agree that the number of swaps in “standard” quicksort is ${1\over 6}$ times the number of comparisons?

Is this figure correct? If not, why not. How do you explain it.

## Shell Sort: Analysis

1. When shell-sorting a sorted array, the number of comparisons is logarithmic. Do you agree? What is the base of the logs? Explain your observations.
2. Can you develop an expression for the average number of comparisons for shell-sort(choose any one of the “common” gap sequences)? Recall that this is “unknown.”
3. Can you develop an expression for the worst-case number of comparisons for the gap sequence you choose?

## Hibbard Deletion of BST: Analysis

According to the course lecture notes, after a number of (Hibbard) deletions have been made, the average height of the tree is ${\sqrt n}$.
Do you agree with this? How does it look after modifying the deletion process to either:
1. Randomly choose which direction to look for the node to be deleted, or
2. Choose the direction according to the size of the candidate nodes.

## General Benchmarking

Run benchmarks on at least five independent algorithms and analyze the numbers of array accesses.
To what extent can you predict the execution time based solely on the number of array accesses?

- [x] Bubble Sort
- [x] Insertion Sort
- [x] Selection Sort
- [x] Quick Sort (Basic)
- [x] Shell Sort

## :busts_in_silhouette: Authors

[Siddharth Rawat](mailto:rawat.sid@northeastern.edu)

[Sumeet Deshpande](mailto:deshpande.su@northeastern.edu)

[Yash Firke](mailto:firke.y@northeastern.edu)
