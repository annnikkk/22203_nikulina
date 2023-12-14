#include <fstream>
#include <gtest/gtest.h>
#include "interpretator.h"

TEST(Plus, PlusOperation) {
    std::ofstream out("../text.txt");
    out << "1000000 2000000 + -1 +";
    out.close();
    std::ifstream in("../text.txt");
    interpretator inter;
    int res = inter.ReadingFromFile(in);
    EXPECT_EQ(res, 2999999);
    /*std::ofstream empty_stack("../text.txt");
    empty_stack.close();
    std::ifstream in1("../text.txt");
    empty_stack << "0 +";
    EXPECT_THROW(inter.ReadingFromFile(in1), std::runtime_error);*/
}

TEST(Minus, MinusOperation) {
    std::ofstream out("../text.txt");
    out << "2000000 1000000 - 1000000 - -1 -";
    out.close();
    std::ifstream in("../text.txt");
    interpretator inter;
    int res = inter.ReadingFromFile(in);
    EXPECT_EQ(res, 1);
}

TEST(Mul, MulOperation) {
    std::ofstream out("../text.txt");
    out << "90 140 * 15 *";
    out.close();
    std::ifstream in("../text.txt");
    interpretator inter;
    int res = inter.ReadingFromFile(in);
    EXPECT_EQ(res, 189000);
}

TEST(Del, DelOperation) {
    std::ofstream out("../text.txt");
    out << "7208 53 / 10 /";
    out.close();
    std::ifstream in("../text.txt");
    interpretator inter;
    int res = inter.ReadingFromFile(in);
    EXPECT_EQ(res, 13);
}

TEST(Mod, ModOperation) {
    std::ofstream out("../text.txt");
    out << "7208 10 mod 5 mod";
    out.close();
    std::ifstream in("../text.txt");
    interpretator inter;
    int res = inter.ReadingFromFile(in);
    EXPECT_EQ(res, 3);
}