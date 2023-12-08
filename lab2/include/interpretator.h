#pragma once
#include "operations.h"
#include "factory.h"
#include <fstream>

class interpretator{
public:
    void ReadingFromFile(std::ifstream& fin) {
        std::stack<int> stack;
        if (fin.is_open()) {
            std::string word;
            while(fin >> word){
                if(word == ".\""){
                    word = reading(fin);
                    word.pop_back();
                    std::cout << word << std::endl;
                    continue;
                }
                if(word == "if"){
                    IfBranch(stack, fin);
                    continue;
                }
                StackOperations(word, stack);
            }
        }
    }

    void IfBranch(std::stack<int> &stack, std::ifstream& fin){
        std::string word;
        if(stack.top() == 0){
            while (word != "then" && word != "else"){
                word = reading(fin);
            }
            if (word == "else"){
                while (word != "then"){
                    word = reading(fin);
                    if(word == ".\"") {
                        word = reading(fin);
                        word.pop_back();
                        std::cout << word << std::endl;
                        continue;
                    }
                    if (word != "then" && word != "else") {
                        StackOperations(word, stack);
                    }
                }
            }
        } else {
            while (word != "then" && word != "else"){
                word = reading(fin);
                if(word == ".\""){
                    word = reading(fin);
                    word.pop_back();
                    std::cout << word << std::endl;
                    continue;
                }
                if(word != "then" && word != "else") {
                    StackOperations(word, stack);
                }
                if (word == "else") {
                    while (word != "then") {
                        word = reading(fin);
                    }
                }
            }
        }
        word = reading(fin);
    }

    std::string reading(std::ifstream& fin){
        std::string word;
        fin >> word;
        return word;
    }

    static void StackOperations(const std::string& word, std::stack<int> &stack){
        if (!OperationsFactory::getInstance()->FindOperation(word)) {
            stack.push(stoi(word));
        } else {
            OperationsFactory::getInstance()->CreateOperation(word)->Operation(stack);
        }
        std::cout<<stack.top()<<std::endl;
    }
};

