#pragma once
#include <stack>

class Operations{
public:
    void Plus();
    void Minus();
    void Mul();
    void Del();
    void Mod();
    void More();
    void Less();
    void Equal();
    void dup();
    void drop();
    void point();
    void swap();
    void rot();
    void over();
    void emit();
    void cr();
private:
    std::stack<int> stack;
    int previous_top;
};
