#include "operations.h"
#include "factory.h"

class Over : public Operations{
public:
    void Operation(std::stack<int> &stack) override {
        Empty_check(stack);
        int previous_top = stack.top();
        stack.pop();
        Empty_check(stack);
        int tmp = stack.top();
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
    Operations* createOver() {
        return new Over;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string("over"), createOver);
}