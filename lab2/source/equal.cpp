#include "operations.h"
#include "factory.h"

class Equal : public Operations{
public:
    void Operation(std::stack<int> &stack) override {
        Empty_check(stack);
        int previous_top = stack.top();
        stack.pop();
        Empty_check(stack);
        bool res = stack.top() = previous_top;
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
    Operations* createEqual() {
        return new Equal;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string( "="), createEqual);
}