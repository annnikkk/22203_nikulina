#ifndef FIELD_H
#define FIELD_H

#include <fstream>
#include <vector>

class field
{
public:
    enum class tile_types{
        nothing = 0,
        wall = 1,
        box_place = 2,
        box = 3,
        player = 4,
        box_on_place = 5,
        player_on_box_place = 6
    };

    field(int size);
    void loading_field(std::ifstream &fin);
    int get_field_size();
    void changing_field(int i, int j, char direction);
    tile_types get_type(int x, int y) const;
    bool check_win_condition();
    int get_field_X() const;
    int get_field_Y() const;
    void set_field_X(int x);
    void set_field_Y(int y);
private:
    int field_X;
    int field_Y;
    int field_size;
    std::vector<tile_types> field_map;
};

#endif // FIELD_H
