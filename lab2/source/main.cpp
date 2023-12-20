#include <fstream>
#include <gtest/gtest.h>
#include "interpretator.h"

int main() {
    std::stringstream in4(".\" bla blabla blablabla\"");
    interpretator inter;
    inter.Reading(in4);
    return 0;
}