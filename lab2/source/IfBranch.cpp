#include "operations.h"
#include "factory.h"
#include "interpretator.h"


class IfBranch : public Operations{
public:
    void Operation(std::stack<int> &stack, std::istream& fin) override{
        int if_counter = 1;
        std::string word;
        if(stack.top() == 0){
            while (if_counter != 0){
                if(!(fin >> word)){
                    throw std::runtime_error("incorrect cycle");
                }
                if(word == "if"){
                    if_counter += 1;
                }
                if(word == "then"){
                    fin >> word;
                    if(word == ";") if_counter -= 1;
                }
                if (word == "else"){
                    while (if_counter != 0){
                        if(!(fin >> word)){
                            throw std::runtime_error("incorrect cycle");
                        }
                        if (word != "then" && word != "else") {
                            interpretator::StackOperations(word, stack, fin);
                        }
                        if(word == "then"){
                            fin >> word;
                            if(word == ";") if_counter -= 1;
                        }
                        if(word == "else"){
                            throw std::runtime_error("incorrect cycle");
                        }
                    }
                }
            }
        } else {
            while (if_counter != 0){
                if(!(fin >> word)){
                    throw std::runtime_error("incorrect if_branch");
                }
                if(word != "then" && word != "else") {
                    interpretator::StackOperations(word, stack, fin);
                }
                if(word == "then"){
                    fin >> word;
                    if(word == ";") if_counter -= 1;
                }
                if (word == "else") {
                    while (if_counter != 0) {
                        if(!(fin >> word)){
                            throw std::runtime_error("incorrect if_branch");
                        }
                        if(word == "then"){
                            fin >> word;
                            if(word == ";") if_counter -= 1;
                        }
                        if(word == "else"){
                            throw std::runtime_error("incorrect if_branch");
                        }
                    }
                }
            }
        }
    }

};

namespace {
    Operations* createIfBranch() {
        return new IfBranch;
    }
    bool x = OperationsFactory::getInstance()->RegisterOperation(std::string("if"), createIfBranch);
}