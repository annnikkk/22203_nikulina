#ifndef GAME_H
#define GAME_H

#include "field.h"
#include "player.h"
#include <map>
#include <algorithm>

class game
{
public:
    game();
    player& get_player();
    void movement(char direction);
    void start_game(int level);
    field& get_field();
    bool check_table_records(int level, double game_time, const std::string& player_name);

    bool win_condition = false;
private:
    field game_field;
    player player_;
};

#endif // GAME_H

