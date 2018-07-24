# rubiks
Brute force study on the Rubik's cube solution set.

This is purely a pet project designed to reduce the number of Rubik’s cube permutations when discovered in some brute force strategy.  Strategies include “spatial”, “colormap”, and “reverse”.  

Spatial disregards X,Y,Z orientation of the entire cube.  
Colormap pretends the red stickers might as well be yellow stickers.  
Reverse explores CW v CCW rotations.

I also became very interested in visualizing the data.  I added a 3D renderer which somewhat accurately draws a cube in 3D space using PDF bezier curves and lines.  Early test renders can be found here:

https://github.com/brianfox/rubiks/tree/master/notes/visualization/proofs/3d


