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
    HashTable table2 = table1;
    EXPECT_EQ(table1 == table2, 1);
    table2 = table2;
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
    EXPECT_EQ(table2.contains("Antonina"), 1);
    EXPECT_EQ(table2.contains("Anna"), 1);
    EXPECT_EQ(table2.contains("Igor"), 0);
    EXPECT_EQ(table1.contains("Igor"), 1);
    EXPECT_EQ(table1.contains("Anna"), 0);
    EXPECT_EQ(table1.contains("Yana"), 0);
    EXPECT_EQ(table1.contains("Antonina"), 0);
    EXPECT_EQ(table2.size(), 3);
    EXPECT_EQ(table1.size(), 1);
}

TEST(AssignmentOperator, AssignsValue) { //operator=
    HashTable table1 = HashTable();
    HashTable table2 = HashTable();
    table1.insert("Anna", {30, 60});
    table1.insert("Antonina", {14, 30});
    table1.insert("Yana", {14, 30});
    table2 = table1;
    EXPECT_EQ(table2.contains("Anna"), 1);
    EXPECT_EQ(table2.contains("Antonina"), 1);
    EXPECT_EQ(table2.contains("Yana"), 1);
    EXPECT_EQ(table2.size(), 3);
}

TEST(AssignmentOperator, SizesNotEqual) { //==?
    HashTable table1 = HashTable();
    HashTable table2 = HashTable();
    table1.insert("Anna", {30, 60});
    table1.insert("Antonina", {14, 30});
    table2.insert("Yana", {14, 30});
    EXPECT_EQ(table1==table2, 0);
}

TEST(Erase, ErasingKey) { //erase
    HashTable table1 = HashTable(2);
    table1.insert("Anna", {30, 60});
    table1.insert("Antonina", {14, 30});
    table1.insert("Yana", {14, 30});
    table1.erase("Anna");
    table1.erase("Antonina");
    table1.erase("Yana");
    EXPECT_EQ(table1.contains("Anna"), 0);
    EXPECT_EQ(table1.contains("Antonina"), 0);
    EXPECT_EQ(table1.contains("Yana"), 0);
    EXPECT_EQ(table1.erase("Artem"), false);
}

TEST(FindOperator, FindKey) { //operator[]
    HashTable table1 = HashTable();
    table1.insert("Anna", {30, 60});
    table1.insert("Antonina", {14, 30});
    EXPECT_EQ(table1["Anna"].age, 30);
    EXPECT_EQ(table1["Anna"].weight, 60);
    table1["NoName"];
    EXPECT_EQ(table1["NoName"].age, 30);
    EXPECT_EQ(table1["NoName"].weight, 80);
}

TEST(At, FindKey) { //at
    HashTable table1 = HashTable();
    table1.insert("Anna", {30, 60});
    table1.insert("Antonina", {14, 80});
    EXPECT_EQ(table1.at("Anna").age, 30);
    EXPECT_EQ(table1.at("Anna").weight, 60);
    EXPECT_EQ(table1.at("Antonina").age, 14);
    EXPECT_EQ(table1.at("Antonina").weight, 80);
    EXPECT_THROW(table1.at("Artem"), std::runtime_error);
}

TEST(At2, FindKey) { //at
    const HashTable table1 = HashTable();
    const Key a = "Anna";
    EXPECT_THROW(table1.at(a).age, std::exception);
}


TEST(NotEqual, Comparing){
    HashTable table1 = HashTable();
    HashTable table2 = HashTable();
    table1.insert("Anna", {19, 45});
    table2.insert("Ann", {19, 45});
    EXPECT_EQ(table1 != table2, 1);
    table2.erase("Ann");
    table2.insert("Anna", {19, 45});
    EXPECT_EQ(table1 != table2, 0);
}

TEST(ResizeInInsert, Resizing) {
    HashTable table1 = HashTable(3);
    table1.insert("Anna", {30, 60});
    table1.insert("Antonina", {14, 30});
    table1.insert("Misha", {14, 30});
    table1.insert("NoName", {30, 60});
    table1.insert("NoName1", {14, 30});
    table1.insert("N", {14, 30});
    EXPECT_EQ(table1.contains("NoName1"), 1);
    EXPECT_EQ(table1.contains("N"), 1);
    EXPECT_EQ(table1.contains("Anna"), 1);
    EXPECT_EQ(table1.contains("NoName"), 1);
    EXPECT_EQ(table1.contains("Antonina"), 1);
    EXPECT_EQ(table1.contains("Misha"), 1);
    EXPECT_EQ(table1.size(), 6);
}
