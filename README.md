# Othello (Shellos Shuffle)
This is a digital version of the Othello (a.k.a. Reversi) board game written in Java. It was created for ICS3U1 (Introduction to Computer Science) in May 2017.

![shellos_shuffle](https://user-images.githubusercontent.com/34670205/36081474-36e0797c-0f6d-11e8-97bf-c7e9ce55e388.png)

## Game Rules

Please note that there are slight differences between these rules and the ones used for the original Othello.

### Game Set-up

The dimension of the game board is 8X8. Initially, the game will be set-up with 2 black disks and 2 white disks at the centre of the board. They are arranged with black forming a North-East to South-West direction and white forming the North-West to South-East direction. This is illustrated in Figure 1 below.

![Figure 1](https://user-images.githubusercontent.com/34670205/34325083-5128aa64-e855-11e7-9d29-9263a4a23215.JPG)

### Making Moves

*	Black always moves first.
*	A disk can only be placed on an unoccupied square.
*	A move is made by placing a disk of the player’s colour on the board in a space that is adjacent horizontally, vertically, or diagonally to an existing disk.

In Figure 2 below, the red checkmarks mark all the valid spaces for the next move.

![Figure 2](https://user-images.githubusercontent.com/34670205/34325088-9ff3df7e-e855-11e7-8b0d-39950ed5a5e5.JPG)

A move may result in “outflanking” one or more opponent’s disks. A disk or row of disks is outflanked when it is surrounded at the ends by disks of the opposite colour. The opponent’s disk(s) which is/are outflanked in that move will then be flipped over to the player’s colour. In Figure 3 below, by placing a black disk at D7, white disks at D4, D5, and D6 will be outflanked. Those white disks will be flipped to black, as illustrated in Figure 4.

![Figure 3](https://user-images.githubusercontent.com/34670205/34325102-f81d1d96-e855-11e7-8aa3-36b48c9a939e.JPG)


![Figure 4](https://user-images.githubusercontent.com/34670205/34325103-040cc14c-e856-11e7-9baf-96debabdbf23.JPG)

In a move, a disk may outflank any number of disks in one or more rows in any directions at the same time: horizontally, vertically, or diagonally. In Figure 5 below, placing a white disk at G3 will outflank the black disks at D3, E3, and F3 vertically, G4 horizontally and D6, E5, and F4 diagonally. These black disks will be flipped to white, as illustrated in Figure 6.

![Figure 5](https://user-images.githubusercontent.com/34670205/34325119-6a065d50-e856-11e7-98e5-44560a89e923.JPG)


![Figure 6](https://user-images.githubusercontent.com/34670205/34325122-6feaca58-e856-11e7-8854-d4f392bf4912.JPG)

A player may not skip over his own colour disk to outflank an opponent’s disk. In Figure 7, placing a black disk at G1 only outflanks the white disk at G2, and no others.

![Figure 7](https://user-images.githubusercontent.com/34670205/34325124-72eaf002-e856-11e7-8ffd-89448b623fd2.JPG)

Disks may only be outflanked as a direct result of a move and must fall in the direct line of the disk placed down. In Figure 8, placing a black disk at B1 outflanks white disks at C1 and D1. After the two white disks are flipped to black, white disks at D2 and D3 remains white, as illustrated in Figure 9.

![Figure 8](https://user-images.githubusercontent.com/34670205/34325126-79a437d2-e856-11e7-957b-7ba5d5f51a5b.JPG)


![Figure 9](https://user-images.githubusercontent.com/34670205/34325127-7c121138-e856-11e7-9f36-9eea01d22be6.JPG)

### Ending the Game
The game ends when there is no more empty space left on the board. The player with the majority of his disks on the board is the winner.
