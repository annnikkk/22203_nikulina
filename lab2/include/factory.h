#pragma once
#include "operations.h"
#include <map>
#include <string>

class OperationsFactory{
public:
    static OperationsFactory * getInstance() {
        static OperationsFactory f;
        return &f;
    }
    using CreateOperationsCallback = Operations* (*)();

    bool RegisterOperation(const std::string& OperationID, CreateOperationsCallback createOp) {
        return callbacks.insert(CallbackMap::value_type(OperationID, createOp)).second;
    }

    bool UnregisteredOperation(const std::string& OperationID) {
        return callbacks.erase(OperationID) == 1;
    }

    bool FindOperation(const std::string& OperationID){
        auto i = callbacks.find(OperationID);
        if (i == callbacks.end()) return false;
        return true;
    }

    Operations* CreateOperation(const std::string& OperationID){
        auto i = callbacks.find(OperationID);
        if (i == callbacks.end()) throw std::runtime_error("Неизвестный идентификатор");
        return (i->second)();
    }
private:
    typedef std::map<std::string, CreateOperationsCallback> CallbackMap;
    CallbackMap callbacks;
};