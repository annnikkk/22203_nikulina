#include "operations.h"
#include "factory.h"

class Rot : public Operations{
public:
    void Operation(std::stack<int> &stack) override {
        Empty_check(stack);
        int first = stack.top();
        stack.pop();
        Empty_check(stack);
        int second = stack.top();
        stack.pop();
        Empty_check(stack);
        int third = stack.top();
        stack.pop();
        stack.push(first);
        stack.push(third);
        stack.push(second);
    }
    static void Empty_check(std::stack<int> &stack){
        if(stack.empty()){
            throw std::runtime_error("stack is empty");
        }
    }
};


namespace {
    Operations* createRot() {
        return new Rot;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string ("rot"), createRot);
}