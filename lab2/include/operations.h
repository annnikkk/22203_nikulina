#pragma once
#include <iostream>
#include <stack>

class Operations{
public:
    virtual void Operation(std::stack<int> &stack, std::istream& fin) = 0;
    virtual ~Operations() = default;

    static void Empty_check(std::stack<int> &stack){
    if(stack.empty()){
        throw std::runtime_error("stack is empty");
    }
}
};
