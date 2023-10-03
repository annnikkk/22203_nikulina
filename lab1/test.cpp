#include "hash_table.h"
#include "hash_table.cpp"
#include <gtest/gtest.h>


TEST(Empty, BasicAssertions) { //insert, empty, конструктор по умолчанию
    HashTable table1 = HashTable();
    EXPECT_EQ(table1.empty(), 1);
    table1.insert("Anna", {30, 60});
    EXPECT_EQ(table1.empty(), 0);
}

TEST(Clear, EmpyingTable){ //clear
    HashTable table1 = HashTable();
    table1.insert("Anna", {30, 60});
    EXPECT_EQ(table1.empty(), 0);
    table1.clear();
    EXPECT_EQ(table1.empty(), 1);
}

TEST(CopyConstr, MakingCopy){ //copy, ==
    HashTable table1 = HashTable();
    table1.insert("Anna", {30, 60});
    table1.insert("Yana", {14, 30});
    HashTable table2 = HashTable(table1);
    EXPECT_EQ(table1 == table2, 1);
}

TEST(EquallyOperator, Comparison) { // ==, resize
    HashTable table1 = HashTable();
    table1.insert("Anna", {30, 60});
    table1.insert("Antonina", {14, 30});
    HashTable table2 = HashTable(table1);
    table1.resize(512);
    EXPECT_EQ(table1 == table2, 1);
}

TEST(Collisions, Comparison) { //collisions, ==
    HashTable table1 = HashTable();
    table1.insert("Anna", {30, 60});
    HashTable table2 = HashTable();
    table2.insert("Antonina", {14, 30});
    EXPECT_EQ(table1 == table2, 0);
}

TEST(Size, CheckingSize) { //size
    HashTable table1 = HashTable();
    table1.insert("Anna", {18, 40});
    table1.insert("Antonina", {19, 70});
    EXPECT_EQ(table1.size(), 2);
}

TEST(Moving, IsTableMoved) { //moving, contains
    HashTable table1 = HashTable();
    table1.insert("Anna", {30, 60});
    table1.insert("Antonina", {14, 30});
    table1.insert("Yana", {14, 30});
    HashTable table2 = HashTable(std::move(table1));
    EXPECT_EQ(table2.contains("Yana"), 1);
    EXPECT_EQ(table1.empty(), 1);
}

TEST(Swap, IsTableSwapped) { //swap
    HashTable table1 = HashTable();
    HashTable table2 = HashTable();
    table1.insert("Anna", {30, 60});
    table1.insert("Antonina", {14, 30});
    table1.insert("Yana", {14, 30});
    table2.insert("Igor", {50, 90});
    table1.swap(table2);
    EXPECT_EQ(table2.contains("Yana"), 1);
    EXPECT_EQ(table1.contains("Igor"), 1);
    EXPECT_EQ(table2.size(), 3);
}

TEST(AssignmentOperator, AssignsValue) { //operator=
    HashTable table1 = HashTable();
    HashTable table2 = HashTable();
    table1.insert("Anna", {30, 60});
    table1.insert("Antonina", {14, 30});
    table1.insert("Yana", {14, 30});
    table2 = table1;
    EXPECT_EQ(table2.contains("Yana"), 1);
    EXPECT_EQ(table2.size(), 3);
}

TEST(Erase, ErasingKey) { //erase
    HashTable table1 = HashTable();
    table1.insert("Anna", {30, 60});
    table1.insert("Antonina", {14, 30});
    table1.insert("Yana", {14, 30});
    table1.erase("Anna");
    EXPECT_EQ(table1.contains("Anna"), 0);
    EXPECT_EQ(table1.size(), 2);
}

TEST(FindOperator, FindKey) { //operator[]
    HashTable table1 = HashTable();
    table1.insert("Anna", {30, 60});
    table1.insert("Antonina", {14, 30});
    EXPECT_EQ(table1.operator[]("Anna").age, 30);
    EXPECT_EQ(table1.operator[]("Anna").weight, 60);
}