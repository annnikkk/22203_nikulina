#pragma once

#include <string>
#include <list>

typedef std::string Key;


struct Value {
    unsigned int age;
    unsigned int weight;

    Value() : Value(30, 80){
    }

    Value(unsigned int a, unsigned int w){
        age = a;
        weight = w;
    }
};


class HashTable
{
public:
    HashTable();

    HashTable(size_t initial_capacity);

    ~HashTable();

    HashTable(const HashTable& b);

    HashTable(HashTable&& b);

    void swap(HashTable& b);

    HashTable& operator=(const HashTable& b);

    void clear();

    bool erase(const Key& k);


    void resize(size_t new_size);

    bool insert(const Key& k, const Value& v);


    bool contains(const Key& k) const;


    Value& operator[](const Key& k);

    Value& at(const Key& k);
    const Value& at(const Key& k) const;

    size_t size() const;

    bool empty() const;

    friend bool operator==(const HashTable& a, const HashTable& b);

    friend bool operator!=(const HashTable& a, const HashTable& b);

private:
    size_t capacity;
    size_t cur_size;
    std::list<std::pair<Key, struct Value>> *table;

    unsigned int HashFunction(const Key& k) const;
};

