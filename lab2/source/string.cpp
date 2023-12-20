#include <fstream>
#include "operations.h"
#include "factory.h"

class String : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override{
        std::string word;
        fin >> word;
        while(word.back() != '"') {
            std::cout << word << " ";
            fin >> word;
        }
        word.pop_back();
        std::cout << word << std::endl;
    }

};

namespace {
    Operations* createString() {
        return new String;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string(".\""), createString);
}
