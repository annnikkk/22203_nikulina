#include "hash_table.h"
#include <gtest/gtest.h>


TEST(Empty, BasicAssertions) {
    HashTable table1 = HashTable();
    EXPECT_EQ(table1.empty(), 1);
    table1.insert("Anna", {30, 60});
    EXPECT_EQ(table1.empty(), 0);
}

TEST(Clear, EmpyingTable){
    HashTable table1 = HashTable();
    table1.insert("Anna", {30, 60});
    EXPECT_EQ(table1.empty(), 0);
    table1.clear();
    EXPECT_EQ(table1.empty(), 1);
}