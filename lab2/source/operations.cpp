#include "../include/operations.h"
#include <iostream>

void PrintOk(){
    std::cout<< "ok" <<std::endl;
};

void Operations::Plus(){
    previous_top = stack.top();
    stack.pop();
    int res = previous_top + stack.top();
    stack.pop();
    stack.push(res);
    //проверка на все ок
}

void Operations::Minus() {
    previous_top = stack.top();
    stack.pop();
    int res = stack.top() - previous_top;
    stack.pop();
    stack.push(res);
    //проверка на все ок
}

void Operations::Mul(){
    previous_top = stack.top();
    stack.pop();
    int res = previous_top * stack.top();
    stack.pop();
    stack.push(res);
    //проверка на все ок
}

void Operations::Del() {
    previous_top = stack.top();
    stack.pop();
    int res =  stack.top() / previous_top;
    stack.pop();
    stack.push(res);
    //проверка на все ок
}

void Operations::Mod() {
    previous_top = stack.top();
    stack.pop();
    int res =  stack.top() % previous_top;
    stack.pop();
    stack.push(res);
    //проверка на все ок
}

void Operations::More() {
    previous_top = stack.top();
    stack.pop();
    bool res = stack.top() > previous_top;
    stack.pop();
    stack.push(res);
}

void Operations::Less() {
    previous_top = stack.top();
    stack.pop();
    bool res = stack.top() < previous_top;
    stack.pop();
    stack.push(res);
}

void Operations::Equal() {
    previous_top = stack.top();
    stack.pop();
    bool res = stack.top() = previous_top;
    stack.pop();
    stack.push(res);
}

void Operations::dup(){
    stack.push(stack.top());
}

void Operations::drop() {
    stack.pop();
}

void Operations::point() {
    std::cout<< stack.top() << std::endl;
    stack.pop();
}

void Operations::swap() {
    previous_top = stack.top();
    stack.pop();
    int tmp = stack.top();
    stack.pop();
    stack.push(previous_top);
    stack.push(tmp);
}

void Operations::rot() {
    int first = stack.top();
    stack.pop();
    int second = stack.top();
    stack.pop();
    int third = stack.top();
    stack.pop();
    stack.push(first);
    stack.push(third);
    stack.push(second);
}

void Operations::over() {
    previous_top = stack.top();
    stack.pop();
    int tmp = stack.top();
    stack.push(previous_top);
    stack.push(tmp);
}

void Operations::emit() {
    std::cout<< char(stack.top()) <<std::endl;
}

void Operations::cr() {
    previous_top = stack.top();
    stack.pop();
    std::cout<< previous_top << "\n" << stack.top() << std::endl;
    stack.push(previous_top);
}