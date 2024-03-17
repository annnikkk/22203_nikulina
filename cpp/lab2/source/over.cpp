#include "operations.h"
#include "factory.h"

class Over : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override {
        Empty_check(stack);
        int previous_top = stack.top();
        stack.pop();
        Empty_check(stack);
        int tmp = stack.top();
        stack.push(previous_top);
        stack.push(tmp);
    }

};

namespace {
    Operations* createOver() {
        return new Over;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string("over"), createOver);
}