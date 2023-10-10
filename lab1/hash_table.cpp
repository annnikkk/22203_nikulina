#include "hash_table.h"

#include<algorithm>

HashTable::HashTable() : HashTable(256) {
}

HashTable::HashTable(size_t initial_capacity) :
        capacity(initial_capacity),
        cur_size(0) {
        table = new std::list<std::pair<Key, struct Value>>[initial_capacity];
}

HashTable::~HashTable() {
    clear();
    delete[] table;
}

HashTable::HashTable(const HashTable &b){
    capacity = b.capacity;
    cur_size = b.cur_size;
    table = new std::list<std::pair<Key, struct Value>>[capacity];
    for (size_t i = 0; i < capacity; ++i) {
        std::copy(b.table[i].begin(), b.table[i].end(), std::back_inserter(table[i]));
    }
};

HashTable::HashTable(HashTable &&b) :
        cur_size(b.cur_size),
        capacity(b.capacity),
        table(b.table) {
    b.cur_size = 0;
    b.capacity = 0;
    b.table = nullptr;
};

void HashTable::swap(HashTable &b) {
    HashTable tmp = std::move(b);
    b = std::move(*this);
    *this = std::move(tmp);
}

HashTable& HashTable::operator=(const HashTable &b){
    if(&b == this){
        return *this;
    }
    delete[] table;
    table = new std::list<std::pair<Key, struct Value>>[b.capacity];
    for (size_t i = 0; i < capacity; ++i) {
        std::copy(b.table[i].begin(), b.table[i].end(), std::back_inserter(table[i]));
    }
    capacity = b.capacity;
    cur_size = b.cur_size;
    return *this;
}

HashTable&& HashTable::operator=(HashTable &&b) {
    cur_size = b.cur_size;
    capacity = b.capacity;
    table = b.table;
    b.cur_size = 0;
    b.capacity = 0;
    b.table = nullptr;
    return std::move(*this);
}

void HashTable::clear() {
    for (int i = 0; i < capacity; ++i) {
        table[i].clear();
    }
    cur_size = 0;
}

bool HashTable::erase(const Key &k) {
    const size_t index = HashFunction(k);
    for (auto i = table[index].begin(); i != table[index].end(); ++i) {
        if (i->first == k && i != table[index].end()) {
            table[index].erase(i);
            cur_size -= 1;
            return true;
        }
    }
    return false;
}

void HashTable::resize(size_t new_capacity){
    if(new_capacity == capacity) return;
    auto *tmp = new std::list<std::pair<Key, struct Value>>[new_capacity];
    for (int i = 0; i < capacity; ++i) {
        for (auto & k : table[i]) {
            tmp[HashFunction(k.first)].push_back({k.first, k.second});
        }
    }
    capacity = new_capacity;
    delete[] table;
    table = tmp;
}

bool HashTable::insert(const Key &k, const Value &v) {
    if(contains(k)) return true;
    double coef = (double)size()/(double)capacity;
    if(coef >= ResizingCoef ){
        resize(NewCapacity);
    }
    const int index = HashFunction(k);
    table[index].push_back({k, v});
    cur_size += 1;
    return true;
}

bool HashTable::contains(const Key &k) const {
    const size_t index = HashFunction(k);
    for (auto i = table[index].begin(); i != table[index].end(); ++i) {
        if (i->first == k) {
            return (i != table[index].end());
        }
    }
    return false;
}

Value &HashTable::operator[](const Key &k) {
    const size_t index = HashFunction(k);
    for (auto i = table[index].begin(); i != table[index].end(); ++i) {
        if (i->first == k) {
            return i->second;
        }
    }
    table[index].push_back({k, Value()});//????????????????????????????
    return table[index].end()->second;
}

Value &HashTable::at(const Key &k){
    const size_t index = HashFunction(k);
    for (auto i = table[index].begin(); i != table[index].end(); ++i) {
        if (i->first == k) {
            return i->second;
        }
    }
    throw std::exception();
}

const Value &HashTable::at(const Key &k) const{
    const size_t index = HashFunction(k);
    for (auto & i : table[index]) {
        if (i.first == k) {
            return i.second;
        }
    }
    throw std::exception();
}

size_t HashTable::size() const {
    return cur_size;
}

bool HashTable::empty() const {
    return (cur_size == 0);
}

bool operator==(const HashTable &a, const HashTable &b) {
    if(a.size() != b.size()) return false;
    for(int i = 0; i < a.capacity; ++i){
        for(auto k = a.table[i].begin(); k != a.table[i].end(); k++){
            if(!b.contains(k->first)) return false;
        }
    }
    return true;
}

bool operator!=(const HashTable &a, const HashTable &b){
    return !(a == b);
}

size_t HashTable::HashFunction(const Key &k) const {
    size_t res;
    res = ((k[0]) * (k[1]) * k[k.size()-2] * k.back()) % capacity;
    return res;
};