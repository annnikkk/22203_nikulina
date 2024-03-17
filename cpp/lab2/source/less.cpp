#include "operations.h"
#include "factory.h"

class Less : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override {
        Empty_check(stack);
        int previous_top = stack.top();
        stack.pop();
        Empty_check(stack);
        int res = 0;
        if(stack.top() < previous_top) res = 1;
        stack.pop();
        stack.push(res);
    }

};

namespace {
    Operations* createLess() {
        return new Less;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string("<"), createLess);
}
