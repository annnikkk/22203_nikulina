#include "operations.h"
#include "factory.h"

class Mul : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override {
        Empty_check(stack);
        int previous_top = stack.top();
        stack.pop();
        Empty_check(stack);
        int res = previous_top * stack.top();
        stack.pop();
        stack.push(res);
    }

};

namespace {
    Operations* createMul() {
        return new Mul;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string("*"), createMul);
}
