#include <stdio.h>
#include <stdlib.h>
#include <iostream>

class string {
public:
    string(size_t initial_capacity) {
        capacity = initial_capacity;
        memory = (char*)malloc(capacity);
        std::cout << "constructor" << std::endl;
    }

    string(const string& s2) {
        capacity = s2.capacity;
        size = s2.size;
        memory = (char*)malloc(capacity);
        for (int i = 0; i < size; i++) {
            memory[i] = s2.memory[i];
        }
    }

    void append(char c) {
        if (size < capacity) {
            memory[size] = c;
            size++;
        }
        else {
            char* tmp;
            tmp = (char*)realloc(memory, capacity);
            if (tmp == nullptr) {
                std::cout << "error" << std::endl;
                return;
            }
            memory = tmp;
            capacity *= 2;
            memory[size] = c;
            size++;
        }
    }

    char operator[](int n) {
        return memory[n];
    }

    string& operator= (const string& s2) {
        capacity = s2.capacity;
        size = s2.size;
        memory = (char*)malloc(capacity);
        for (int i = 0; i < size; i++) {
            memory[i] = s2.memory[i];
        }
        return *this;
    }

    void show() {
        for (int i = 0; i < size; i++) {
            std::cout << memory[i];
        }
        std::cout << std::endl;
    }

    ~string() {
        free(memory);
        std::cout << "destructor" << std::endl;
    }

private:
    size_t capacity = 0;
    size_t size = 0;
    char* memory = nullptr;
};

int main() {

    string s1 = string(4);
    char c;
    for (int i = 0; i < 8; i++) {
        std::cin >> c;
        s1.append(c);
    }
    s1.show();
    std::cout << s1.operator[](2) << std::endl;
    string s2 = string(s1);
    s2.show();
    string s3(4);
    s3.operator=(s1);
    s3.show();
    return 0;
}