### Chess ISU

# Sprite Paths

- sprites
    - background.png
    - white
      - *insert_piece_here*.png
    - black
      - *insert_piece_here*.png 

# Chess Board Array Arrangement

Piece[file][rank]  

i.e. In order to iterate through row 3, you would have to do  

```java
for (int i = 0; i < 7; i++)  
    board[i][2];
```