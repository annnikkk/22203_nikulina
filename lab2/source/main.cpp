#include "interpretator.h"

int main() {
    //????
    interpretator inter;
    std::ifstream fin("in.txt");
    if(!fin.is_open()){
        std::cout<<("error!!!")<<std::endl;
    }
    inter.ReadingFromFile(fin);
    return 0;
}