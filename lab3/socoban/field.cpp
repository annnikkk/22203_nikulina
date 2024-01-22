#include "field.h"


field::field(int size) : field_size(size){
    field_map = std::vector<tile_types>(field_size);
}



void field::loading_field(std::ifstream &fin){
    int num;
    for(int i = 0; i < 10; i++){
        for(int j = 0; j < 10; j++){
            fin >> num;
            field_map[i*10+j] = static_cast<tile_types>(num);
        }
    }
 }

int field::get_field_size(){
    return field_size;
}

field::tile_types field::get_type(int x, int y) const{
    return field_map[y*10 + x];
}

int field::get_field_X() const{
    return field_X;
}

int field::get_field_Y() const{
    return field_Y;
}

void field::set_field_X(int x){
    field_X = x;
}

void field::set_field_Y(int y){
    field_Y = y;
}

void field::changing_field(int i, int j, const char direction){
    switch(direction){
    case 'r':
        if(field_map[i*10+(j+1)] == tile_types::box && field_map[i*10+j] == tile_types::box){
            break;
        }
        if(field_map[i*10+(j+1)] == tile_types::box){
            changing_field(i, j+1, 'r');
        }
        if(field_map[i*10+(j+1)] == tile_types::nothing){
            if(field_map[i*10+j] == tile_types::player_on_box_place){
                field_map[i*10+(j+1)] = tile_types::player;
                field_map[i*10+j] = tile_types::box_place;
            } else {
            std::swap(field_map[i*10+j],field_map[i*10+(j+1)]);
            }
            break;
        }
        if(field_map[i*10+(j+1)] == tile_types::wall){
            break;
        }
        if(field_map[i*10+(j+1)] == tile_types::box_place && field_map[i*10+j] == tile_types::box){
            field_map[i*10+(j+1)] = tile_types::box_on_place;
            field_map[i*10+j] = tile_types::nothing;
            break;
        }
        if(field_map[i*10+(j+1)] == tile_types::box_place && field_map[i*10+j] == tile_types::player){
            field_map[i*10+(j+1)] = tile_types::player_on_box_place;
            field_map[i*10+j] = tile_types::nothing;
            break;
        }
        if(field_map[i*10+(j+1)] == tile_types::box_on_place){
            if(field_map[i*10+(j+2)] != tile_types::wall){
                field_map[i*10+(j+1)] = tile_types::player_on_box_place;
                field_map[i*10+(j+2)] = tile_types::box;
                field_map[i*10+j] = tile_types::nothing;
            }
            break;
        }
        break;
    case 'l':
        if(field_map[i*10+(j-1)] == tile_types::box && field_map[i*10+j] == tile_types::box){
            break;
        }
        if(field_map[i*10+(j-1)] == tile_types::box){
            changing_field(i, j-1, 'l');
        }
        if(field_map[i*10+(j-1)] == tile_types::nothing){
            if(field_map[i*10+j] == tile_types::player_on_box_place){
                field_map[i*10+(j-1)] = tile_types::player;
                field_map[i*10+j] = tile_types::box_place;
            } else {
            std::swap(field_map[i*10+j],field_map[i*10+(j-1)]);
            }
            break;
        }
        if(field_map[i*10+(j-1)] == tile_types::wall){
            break;
        }
        if(field_map[i*10+(j-1)] == tile_types::box_place && field_map[i*10+j] == tile_types::box){
            field_map[i*10+(j-1)] = tile_types::box_on_place;
            field_map[i*10+j] = tile_types::nothing;
            break;
        }
        if(field_map[i*10+(j-1)] == tile_types::box_place && field_map[i*10+j] == tile_types::player){
            field_map[i*10+(j-1)] = tile_types::player_on_box_place;
            field_map[i*10+j] = tile_types::nothing;
            break;
        }
        if(field_map[i*10+(j-1)] == tile_types::box_on_place){
            if(field_map[i*10+(j-2)] != tile_types::wall){
                field_map[i*10+(j-1)] = tile_types::player_on_box_place;
                field_map[i*10+(j-2)] = tile_types::box;
                field_map[i*10+j] = tile_types::nothing;
            }
            break;
        }
        break;
    case 'u':
        if(field_map[(i-1)*10+j] == tile_types::box && field_map[i*10+j] == tile_types::box){
            break;
        }
        if(field_map[(i-1)*10+j] == tile_types::box){
            changing_field(i-1, j, 'u');
        }
        if(field_map[(i-1)*10+j] == tile_types::nothing){
            if(field_map[i*10+j] == tile_types::player_on_box_place){
                field_map[(i-1)*10+j] = tile_types::player;
                field_map[i*10+j] = tile_types::box_place;
            } else {
            std::swap(field_map[i*10+j],field_map[(i-1)*10+j]);
            }
            break;
        }
        if(field_map[(i-1)*10+j] == tile_types::wall){
            break;
        }
        if(field_map[(i-1)*10+j] == tile_types::box_place && field_map[i*10+j] == tile_types::box){
            field_map[(i-1)*10+j] = tile_types::box_on_place;
            field_map[i*10+(j)] = tile_types::nothing;
            break;
        }
        if(field_map[(i-1)*10+j] == tile_types::box_place && field_map[i*10+j] == tile_types::player){
            field_map[(i-1)*10+j] = tile_types::player_on_box_place;
            field_map[i*10+j] = tile_types::nothing;
            break;
        }
        if(field_map[(i-1)*10+j] == tile_types::box_on_place){
            if(field_map[(i-2)*10+j] != tile_types::wall){
                field_map[(i-1)*10+j] = tile_types::player_on_box_place;
                field_map[(i-2)*10+j] = tile_types::box;
                field_map[i*10+j] = tile_types::nothing;
            }
            break;
        }
        break;
    case 'd':
        if(field_map[(i+1)*10+j] == tile_types::box && field_map[i*10+j] == tile_types::box){
            break;
        }
        if(field_map[(i+1)*10+j] == tile_types::box){
            changing_field(i+1, j, 'd');
        }
        if(field_map[(i+1)*10+j] == tile_types::nothing){
            if(field_map[i*10+j] == tile_types::player_on_box_place){
                field_map[(i+1)*10+j] = tile_types::player;
                field_map[i*10+j] = tile_types::box_place;
            } else {
                std::swap(field_map[i*10+j],field_map[(i+1)*10+j]);
            }
            break;
        }
        if(field_map[(i+1)*10+j] == tile_types::wall){
            break;
        }
        if(field_map[(i+1)*10+j] == tile_types::box_place && field_map[i*10+j] == tile_types::box){
            field_map[(i+1)*10+j] = tile_types::box_on_place;
            field_map[i*10+(j)] = tile_types::nothing;
            break;
        }
        if(field_map[(i+1)*10+j] == tile_types::box_place && field_map[i*10+j] == tile_types::player){
            field_map[(i+1)*10+j] = tile_types::player_on_box_place;
            field_map[i*10+j] = tile_types::nothing;
            break;
        }
        if(field_map[(i+1)*10+j] == tile_types::box_on_place){
            if(field_map[(i+2)*10+j] != tile_types::wall){
                field_map[(i+1)*10+j] = tile_types::player_on_box_place;
                field_map[(i+2)*10+j] = tile_types::box;
                field_map[i*10+j] = tile_types::nothing;
            }
            break;
        }
        break;
    }

}

bool field::check_win_condition(){
    int boxes = 0;
    for(size_t i = 0; i < 100; i++){
        if(field_map[i] == tile_types::box){
            boxes += 1;
        }
    }
    return boxes == 0;
}
