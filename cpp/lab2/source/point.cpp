#include "operations.h"
#include "factory.h"

class Point : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override {
        if(stack.empty()){
            throw std::runtime_error("stack is empty");
        }
        std::cout<< stack.top() << std::endl;
        stack.pop();
    }
};

namespace {
    Operations* createPoint() {
        return new Point;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string("."), createPoint);
}