#pragma once

#include <iostream>

template <class T>
class shared_ptr{
public:
    shared_ptr(T* p = nullptr){
        ptr = p;
        (*counter)++;
    }
    ~shared_ptr(){
        (*counter)--;
        if(*counter < 1){
            delete ptr;
            delete counter;
        }
    }
    shared_ptr(const shared_ptr& other){
        ptr = other.ptr;
        counter = other.counter;
        (*counter)++;
    };
    shared_ptr(shared_ptr&& other){
        ptr = other.ptr;
        counter = other.counter;
        other.ptr = nullptr;
        other.counter = nullptr;
        other = nullptr;
    };
    shared_ptr& operator=(const shared_ptr& other){
        if(*this != other){
            ~shared_ptr(*this);
            ptr = other.ptr;
            counter = other.counter;
            (*counter)++;
        }
        return *this;
    }
    shared_ptr& operator=(const shared_ptr&& other){
        if(*this != other){
            ~shared_ptr(*this);
            ptr = other.ptr;
            counter  = other.counter;
            (*counter)++;
            other.ptr = nullptr;
            other.counter = nullptr;
        }
        return *this;
    };
    T* get(){
        return ptr;
    };
    T* reset(T* newnew = nullptr){
        ptr = nullptr;
        ptr = newnew;
        *counter = 0;
        return ptr;
    };
    T *release() {
        (*counter)--;
        T *tmp = ptr;
        ptr = nullptr;
        return tmp;
    }
    T& operator*(){
        return *ptr;
    };
    T* operator->(){
        return ptr;
    };

private:
    T* ptr = nullptr;
    int* counter = new int(0);
};


