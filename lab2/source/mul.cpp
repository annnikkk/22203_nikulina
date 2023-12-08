#include "operations.h"
#include "factory.h"

class Mul : public Operations{
public:
    void Operation(std::stack<int> &stack) override {
        Empty_check(stack);
        int previous_top = stack.top();
        stack.pop();
        Empty_check(stack);
        int res = previous_top * stack.top();
        stack.pop();
        stack.push(res);
    }
    static void Empty_check(std::stack<int> &stack){
        if(stack.empty()){
            throw std::runtime_error("stack is empty");
        }
    }
};

namespace {
    Operations* createMul() {
        return new Mul;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string("*"), createMul);
}
