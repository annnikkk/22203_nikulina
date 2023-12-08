#include "operations.h"
#include "factory.h"

class Swap : public Operations{
public:
    void Operation(std::stack<int> &stack) override {
        Empty_check(stack);
        int previous_top = stack.top();
        stack.pop();
        Empty_check(stack);
        int tmp = stack.top();
        stack.pop();
        stack.push(previous_top);
        stack.push(tmp);
    }
    static void Empty_check(std::stack<int> &stack){
        if(stack.empty()){
            throw std::runtime_error("stack is empty");
        }
    }
};

namespace {
    Operations* createSwap() {
        return new Swap;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string("swap"), createSwap);
}