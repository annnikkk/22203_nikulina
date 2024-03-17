#include "operations.h"
#include "factory.h"

class Emit: public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override {
        if(stack.empty()){
            throw std::runtime_error("stack is empty");
        }
        std::cout<< (unsigned char)(stack.top()) <<std::endl;
    }
};

namespace {
    Operations* createEmit() {
        return new Emit;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string( "emit"), createEmit);
}
