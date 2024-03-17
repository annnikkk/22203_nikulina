#include "operations.h"
#include "factory.h"

class Drop : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override {
        if(stack.empty()){
                throw std::runtime_error("stack is empty");
        }
        stack.pop();
    }
};

namespace {
    Operations* createDrop() {
        return new Drop;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string( "drop"), createDrop);
}