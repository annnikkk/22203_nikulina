#include "hash_table.h"

HashTable::HashTable() : HashTable(256) {
}

HashTable::HashTable(size_t initial_capacity) {
    table = new std::list<std::pair<Key, struct Value>>[initial_capacity];
    capacity = initial_capacity;
}

HashTable::~HashTable() {
    clear();
    delete[] table;
}

HashTable::HashTable(const HashTable &b) {
    capacity = b.capacity;
    table = new std::list<std::pair<Key, struct Value>>[capacity];
    for (size_t i = 0; i < capacity; ++i) {
        std::list<std::pair<Key, struct Value>>::iterator k;
        for (k = b.table[i].begin(); k != b.table[i].end(); ++k) {
            table[i].push_back({k->first, k->second});
        }
    }
}

HashTable::HashTable(const HashTable &&b) {
    capacity = b.capacity;
    table = std::move(b.table);
};

void HashTable::swap(HashTable &b) {

}

HashTable &HashTable::operator=(const HashTable &b){

    //???????????????????????????????????????????????
    //??????????????????????????????????????????????
    //??????????????????????????????????????????????

    capacity = b.capacity;
    table = new std::list<std::pair<Key, struct Value>>[b.capacity];
    for (int i = 0; i < capacity; ++i) {
        std::list<std::pair<Key, struct Value>>::iterator k;
        for (k = b.table[i].begin(); k != b.table[i].end(); ++k) {
            table[i].push_back({k->first, k->second});
        }
    }
    return *this;
}

void HashTable::clear() {
    for (int i = 0; i < capacity; ++i) {
        table[i].clear();
    }
}

bool HashTable::erase(const Key &k) {
    unsigned int index = HashFunction(k);
    std::list<std::pair<Key, struct Value>>::iterator i;
    for (i = table[index].begin(); i != table[index].end(); i++) {
        if (i->first == k) {
            break;
        }
    }
    if (i != table[index].end()) {
        table[index].erase(i);
        return true;
    } else {
        return false;
    }
}

void HashTable::resize(size_t new_size){
    auto *tmp = new std::list<std::pair<Key, struct Value>>[new_size];
    for (int i = 0; i < capacity; ++i) {
        std::list<std::pair<Key, struct Value>>::iterator k;
        for (k = table[i].begin(); k != table[i].end(); ++k) {
            tmp[HashFunction(k->first)].push_back({k->first, k->second});
        }
    }
    delete[] table;
    table = new std::list<std::pair<Key, struct Value>>[new_size];
    for (int i = 0; i < capacity; ++i) {
        std::list<std::pair<Key, struct Value>>::iterator k;
        for (k = tmp[i].begin(); k != tmp[i].end(); ++k) {
            table[i].push_back({k->first, k->second});
        }
    }
}

bool HashTable::insert(const Key &k, const Value &v) {
    unsigned int index = HashFunction(k);
    double coef = (double)size()/(double)capacity;
    if(coef >= 0.7 ){
        capacity *= 2;
        resize(capacity);
    }
    table[index].push_back({k, v});
}

bool HashTable::contains(const Key &k) const {
    unsigned int index = HashFunction(k);
    std::list<std::pair<Key, struct Value>>::iterator i;
    for (i = table[index].begin(); i != table[index].end(); i++) {
        if (i->first == k) {
            break;
        }
    }
    return (i != table[index].end());
}

Value &HashTable::operator[](const Key &k) {
    unsigned int index = HashFunction(k);
    std::list<std::pair<Key, struct Value>>::iterator i;
    for (i = table[index].begin(); i != table[index].end(); ++i) {
        if (i->first == k) {
            return i->second;
            break;
        }
    }
    table[index].push_back({k, Value()});
    return table[index].end()->second;
}

Value &HashTable::at(const Key &k){

}

const Value &HashTable::at(const Key &k) const{

}

size_t HashTable::size() const {
    size_t k = 0;
    for(int i = 0; i < capacity; ++i){
        if(!table[i].empty()){
            k += table[i].size();
        }
    }
    return k;
}

bool HashTable::empty() const {
    for(int i = 0; i < capacity; ++i){
        if(!table[i].empty()){
            return false;
        }
    }
    return true;
}

bool operator==(const HashTable &a, const HashTable &b) {
    for(int i = 0; i < a.capacity; ++i){
        std::list<std::pair<Key, struct Value>>::iterator k, l;
        for(k = a.table[i].begin(), l = b.table[i].begin(); k != a.table[i].end() || l != b.table[i].end(); k++, l++){
            if (k != l){
                return false;
            }
        }
    }
    return true;
}

bool operator!=(const HashTable &a, const HashTable &b){
    for(int i = 0; i < a.capacity; ++i){
        std::list<std::pair<Key, struct Value>>::iterator k, l;
        for(k = a.table[i].begin(), l = b.table[i].begin(); k != a.table[i].end() || l != b.table[i].end(); k++, l++){
            if (k != l){
                return true;
            }
        }
    }
    return false;
}

unsigned int HashTable::HashFunction(const Key &k) const {
    unsigned int res;
    res = ((k[0])*(k[1])*(k[k.size() - 2])*(k.back()))%capacity;
    return res;
};
