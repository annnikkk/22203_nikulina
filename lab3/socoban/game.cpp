#include "game.h"
#include "field.h"
#include "player.h"

game::game()
    : game_field(400) , player_((game_field.get_field_size())/10)
{

}

void game::start_game(int level){
    std::ifstream fin;
    switch (level) {
    case 1:
        fin.open("../socoban/level1.txt");
        if(!fin.is_open()){
            throw std::runtime_error("error readig from file");        }
        game_field.loading_field(fin);
        break;
    case 2:
        fin.open("../socoban/level2.txt");
        if(!fin.is_open()){
            throw std::runtime_error("error readig from file");        }
        game_field.loading_field(fin);
        break;
    case 3:
        fin.open("../socoban/level3.txt");
        if(!fin.is_open()){
            throw std::runtime_error("error readig from file");        }
        game_field.loading_field(fin);
        break;
    case 4:
        fin.open("../socoban/level4.txt");
        if(!fin.is_open()){
            throw std::runtime_error("error readig from file");        }
        game_field.loading_field(fin);
        break;
    case 5:
        fin.open("../socoban/level5.txt");
        if(!fin.is_open()){
            throw std::runtime_error("error readig from file");        }
        game_field.loading_field(fin);
        break;
    default:
            throw std::runtime_error("error readig from file");
        return;
    }

}


bool game::check_table_records(int level, double game_time, const std::string& player_name){
    std::string path;
    switch (level) {
    case 1:
        path = "../socoban/level1_table.txt";
        break;
    case 2:
        path = "../socoban/level2_table.txt";
        break;
    case 3:
        path = "../socoban/level3_table.txt";
        break;
    case 4:
        path = "../socoban/level4_table.txt";
        break;
    case 5:
        path = "../socoban/level5_table.txt";
        break;
    default:
            throw std::runtime_error("error readig from file");
        return false;
    }

    std::ifstream fin(path);
    std::map<std::string, double> tmp;
    std::string name;
    double time;
    while(fin >> name >> time){
        if (fin.fail()) {
            throw std::runtime_error("error readig from file");
        }
        tmp[name] = time;
    }
    tmp[player_name] = game_time;

    std::vector<std::pair<std::string, double>> table(tmp.begin(), tmp.end());

    std::sort(table.begin(), table.end(), [](const std::pair<std::string, double>& a, const std::pair<std::string, double>& b) {
        return a.second < b.second;
    });

    if(table.size() > 5){
        table.erase(table.end() - 1);
    }

    fin.close();
    std::ofstream fout(path);
    fout.clear();
    for(auto& it : table){
        fout << it.first << " " << it.second << std::endl;
    }

    fout.close();
    for(auto& it : table){
        if(it.first == player_name) return true;
    }
    return false;
}

void game::movement(char direction){
    if(game_field.check_win_condition()){
        win_condition = true;
    }
    game_field.changing_field(player_.get_position_x(), player_.get_position_y(), direction);
}

player& game::get_player(){
    return player_;
}

field& game::get_field(){
    return game_field;
}
