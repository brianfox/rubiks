# rubiks

## Brute force study on the Rubik's cube solution set.

This is purely a pet project designed to reduce the number of Rubik’s cube permutations when discovered in some brute force strategy.  Strategies include “spatial”, “colormap”, and “reverse”.  

Spatial disregards X,Y,Z orientation of the entire cube.  
Colormap pretends the red stickers might as well be yellow stickers.  
Reverse explores CW v CCW rotations.

## State of the Code
This code is imported into Git after being developed in a CVS repo.  

## Running It
The code is old.  But as of 2018, it still runs out of the box when imported into IntelliJ or Eclipse.  Classes of interest include:

https://github.com/brianfox/rubiks/blob/master/src/com/seefoxrun/rubiks/apps/interactive/TextPlayer.java
https://github.com/brianfox/rubiks/blob/master/src/com/seefoxrun/rubiks/apps/walker/simple/SimpleWalker.java

## Visualization
A 3D renderer is included to help depict patterns and help visualize ways to reduce the brute force set. The renderer approximates a cube in 3D space using PDF bezier curves and lines.  Early test renders can be found here:
https://github.com/brianfox/rubiks/tree/master/notes/visualization/proofs/3d


