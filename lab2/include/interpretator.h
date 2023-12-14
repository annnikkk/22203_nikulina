#pragma once
#include "operations.h"
#include "factory.h"
#include <fstream>
#include <vector>
#include <algorithm>


class interpretator{
public:
    int ReadingFromFile(std::ifstream& fin) {
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
                if(word == "do"){
                    LoopBranch(stack, fin);
                    continue;
                }
                StackOperations(word, stack);
            }
            return stack.top();
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

    void LoopBranch(std::stack<int> &stack, std::ifstream& fin){
        int previous_top = stack.top();
        stack.pop();
        int stack_top = stack.top();
        stack.pop();
        std::string word;
        std::vector<std::string> commands;
        while(word != "loop"){
            word = reading(fin);
            if(word != "loop") commands.push_back(word);
        }
        for(int i = previous_top; i < stack_top; i++){
            for (const std::string& str : commands) {
                if(str == "i") StackOperations(std::to_string(i), stack);
                else StackOperations(str, stack);
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
            if (std::all_of(word.begin()+1, word.end(), ::isdigit)) {
                stack.push(std::stoi(word));
            } else {
                std::cerr << "Error: Invalid number - " << word << std::endl;
            }
        } else {
            OperationsFactory::getInstance()->CreateOperation(word)->Operation(stack);
        }
        //std::cout<<stack.top()<<std::endl;
    }
};

