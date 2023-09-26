#ifndef LAB1_HASH_TABLE_H
#define LAB1_HASH_TABLE_H

#include <iostream>
#include <string>
#include <list>
#include <corecrt.h>

typedef std::string Key;
using std::list;
using std::pair;

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
    HashTable() : HashTable(256){
    }
    HashTable(size_t initial_capacity){
        table = new list<pair<Key, struct Value>>[initial_capacity];
        capacity = initial_capacity;
    }

    ~HashTable(){
        clear();
        delete[] table;
    }

    HashTable(const HashTable& b){
        capacity = b.capacity;
        table = new list<pair<Key, struct Value>>[b.capacity];
        for (int i = 0; i < capacity; ++i) {
            list<pair<Key, struct Value>>::iterator k;
            for (k = b.table[i].begin(); k != b.table[i].end(); ++k) {
                table[i].push_back({k->first, k->second});
            }
        }
    }

    HashTable(const HashTable&& b);

    void swap(HashTable& b);

    HashTable& operator=(const HashTable& b){
        capacity = b.capacity;
        table = new list<pair<Key, struct Value>>[b.capacity];
        for (int i = 0; i < capacity; ++i) {
            list<pair<Key, struct Value>>::iterator k;
            for (k = b.table[i].begin(); k != b.table[i].end(); ++k) {
                table[i].push_back({k->first, k->second});
            }
        }
        return *this;
    }

    void clear(){
        for(int i = 0; i < capacity; ++i){
            table[i].clear();
        }
    }

    bool erase(const Key& k){
        unsigned int index = HashFunction(k);
        list<pair<Key, struct Value>>::iterator i;
        for (i = table[index].begin(); i != table[index].end(); i++) {
            if (i->first == k) {
                break;
            }
        }
        if (i != table[index].end()) {
            table[index].erase(i);
            return true;
        } else {
            std::cout << "element not found" << std::endl;
            return false;
        }
    }

    void resize(size_t new_size){
        auto *tmp = new list<pair<Key, struct Value>>[new_size];
        for (int i = 0; i < capacity; ++i) {
            list<pair<Key, struct Value>>::iterator k;
            for (k = table[i].begin(); k != table[i].end(); ++k) {
                tmp[HashFunction(k->first)].push_back({k->first, k->second});
            }
        }
        delete[] table;
        table = new list<pair<Key, struct Value>>[new_size];
        for (int i = 0; i < capacity; ++i) {
            list<pair<Key, struct Value>>::iterator k;
            for (k = tmp[i].begin(); k != tmp[i].end(); ++k) {
                table[i].push_back({k->first, k->second});
            }
        }
    }

    bool insert(const Key& k, const Value& v){
        unsigned int index = HashFunction(k);
        double coef = (double)size()/(double)capacity;
        if(coef >= 0.7 ){
            capacity *= 2;
            resize(capacity);
        }
        table[index].push_back({k, v});
    }

    bool contains(const Key& k) const{
        unsigned int index = HashFunction(k);
        list<pair<Key, struct Value>>::iterator i;
        for (i = table[index].begin(); i != table[index].end(); i++) {
            if (i->first == k) {
                break;
            }
        }
        if (i != table[index].end()) {
            return true;
        } else {
            std::cout << "element not found" << std::endl;
            return false;
        }
    }

    Value& operator[](const Key& k){
        unsigned int index = HashFunction(k);
        list<pair<Key, struct Value>>::iterator i;
        for (i = table[index].begin(); i != table[index].end(); ++i) {
            if (i->first == k) {
                return i->second;
                break;
            }
        }
        table[index].push_back({k, Value()});
        return table[index].end()->second;
    }

    Value& at(const Key& k);
    const Value& at(const Key& k) const;

    size_t size() const{
        size_t k = 0;
        for(int i = 0; i < capacity; ++i){
            if(!table[i].empty()){
                k += table[i].size();
            }
        }
        return k;
    }

    bool empty() const{
        for(int i = 0; i < capacity; ++i){
            if(!table[i].empty()){
                return false;
            }
        }
        return true;
    }

    friend bool operator==(const HashTable& a, const HashTable& b){
        for(int i = 0; i < a.capacity; ++i){
            list<pair<Key, struct Value>>::iterator k, l;
            for(k = a.table[i].begin(), l = b.table[i].begin(); k != a.table[i].end() || l != b.table[i].end(); k++, l++){
                if (k != l){
                    return false;
                }
            }
        }
        return true;
    }

    friend bool operator!=(const HashTable& a, const HashTable& b){
        for(int i = 0; i < a.capacity; ++i){
            list<pair<Key, struct Value>>::iterator k, l;
            for(k = a.table[i].begin(), l = b.table[i].begin(); k != a.table[i].end() || l != b.table[i].end(); k++, l++){
                if (k != l){
                    return true;
                }
            }
        }
        return false;
    }

private:
    size_t capacity = 0;
    list<pair<Key, struct Value>> *table;

    unsigned int HashFunction(const Key& k) const {
        unsigned int res;
        res = ((k[0])*(k[1])*(k[k.size() - 2])*(k.back()))%capacity;
        return res;
    };
};


#endif //LAB1_HASH_TABLE_H
