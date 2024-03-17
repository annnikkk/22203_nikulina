#include "operations.h"
#include "factory.h"

class Del : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override {
        Empty_check(stack);
        int previous_top = stack.top();
        stack.pop();
        Empty_check(stack);
        if(previous_top == 0){
            throw std::runtime_error("Error: division by zero");
        }
        int res =  stack.top() / previous_top;
        stack.pop();
        stack.push(res);
    }
};

namespace {
    Operations* createDel() {
        return new Del;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string( "/"), createDel);
}
