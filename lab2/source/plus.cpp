#include "operations.h"
#include "factory.h"

class Plus : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override{
        Empty_check(stack);
        int previous_top = stack.top();
        stack.pop();
        Empty_check(stack);
        int res = previous_top + stack.top();
        stack.pop();
        stack.push(res);
    }

};

namespace {
    Operations* createPlus() {
        return new Plus;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string("+"), createPlus);
}
