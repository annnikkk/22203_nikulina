#include "operations.h"
#include "factory.h"

class Swap : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override {
        Empty_check(stack);
        int previous_top = stack.top();
        stack.pop();
        Empty_check(stack);
        int tmp = stack.top();
        stack.pop();
        stack.push(previous_top);
        stack.push(tmp);
    }

};

namespace {
    Operations* createSwap() {
        return new Swap;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string("swap"), createSwap);
}