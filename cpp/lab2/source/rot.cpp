#include "operations.h"
#include "factory.h"

class Rot : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override {
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

};


namespace {
    Operations* createRot() {
        return new Rot;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string ("rot"), createRot);
}