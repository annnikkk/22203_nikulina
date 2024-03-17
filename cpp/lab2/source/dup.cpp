#include "operations.h"
#include "factory.h"

class Dup : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override {
        Empty_check(stack);
        stack.push(stack.top());
    }
};

namespace {
    Operations* createDup() {
        return new Dup;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string( "dup"), createDup);
}