#include "operations.h"
#include "factory.h"

class Minus : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override {
        Empty_check(stack);
        int previous_top = stack.top();
        stack.pop();
        Empty_check(stack);
        int res = stack.top() - previous_top;
        stack.pop();
        stack.push(res);
    }

};

namespace {
    Operations* createMinus() {
        return new Minus;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string("-"), createMinus);
}