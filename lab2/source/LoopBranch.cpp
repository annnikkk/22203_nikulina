#include "operations.h"
#include "factory.h"
#include "interpretator.h"
#include <vector>


class LoopBranch : public Operations {
public:
    void Operation(std::stack<int> &stack, std::istream &fin) override {
        int previous_top = stack.top();
        stack.pop();
        int stack_top = stack.top();
        stack.pop();
        std::string word;
        std::vector<std::string> commands;
        int do_counter = 1;
        while (do_counter != 0) {
            if(!(fin >> word)){
                throw std::runtime_error("incorrect cycle");
            }
            if(word == "loop") {
                fin >> word;
                if (word == ";") do_counter -= 1;
                continue;
            }
            if(word == "do") {
                do_counter += 1;
                continue;
            }
            if (word != "loop" ) commands.push_back(word);
        }
        for (int i = previous_top; i < stack_top; i++) {
            for (const std::string &str: commands) {
                if (str == "i") interpretator::StackOperations(std::to_string(i), stack, fin);
                else interpretator::StackOperations(str, stack, fin);
            }
        }
    }
};

namespace {
    Operations *createLoopBranch() {
        return new LoopBranch;
    }

    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string("do"), createLoopBranch);
}
