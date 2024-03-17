#include "operations.h"
#include "factory.h"

class Cr: public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override {
        Empty_check(stack);
        int previous_top = stack.top();
        stack.pop();
        Empty_check(stack);
        std::cout<< previous_top << "\n" << stack.top() << std::endl;
        stack.push(previous_top);
    }
};

namespace {
    Operations* createCr() {
        return new Cr;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string( "cr"), createCr);
}