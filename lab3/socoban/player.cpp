#include "player.h"

player::player(int squareSize) {
    playerSize = squareSize;
}

void player::set_position(int posX, int posY, int squareSize){
    playerX = posX;
    playerY = posY;
    fieldSize = squareSize;
}

int player::get_position_x(){
    return playerX;
}

int player::get_position_y(){
    return playerY;
}


