#pragma once

#include "operations.h"
#include "factory.h"
#include <fstream>
#include <algorithm>


class interpretator {
public:
    int Reading(std::istream &fin) {
        std::stack<int> stack;
        std::string word;
        while (fin >> word) {
            StackOperations(word, stack, fin);
        }
        if(!stack.empty()){
            return stack.top();
        } else {
            return 0;
        }
    }

    static void StackOperations(const std::string &word, std::stack<int> &stack, std::istream &fin) {
        if (! OperationsFactory::getInstance()->FindOperation(word)) {
            if (std::all_of(word.begin() + 1, word.end(), ::isdigit)) {
                stack.push(std::stoi(word));
            } else {
                std::cerr << "Error: Invalid number - " << word << std::endl;
            }
        } else {
            OperationsFactory::getInstance()->CreateOperation(word)->Operation(stack, fin);
        }
    }
};