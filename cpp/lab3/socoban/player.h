#ifndef PLAYER_H
#define PLAYER_H


class player
{
public:
    player(int squareSize);
    void set_position(int posX, int posY, int fieldSize);
    int get_position_x();
    int get_position_y();
private:
    int playerX;
    int playerY;
    int playerSize;
    int fieldSize;
};

#endif // PLAYER_H
