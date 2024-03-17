#include <fstream>
#include <gtest/gtest.h>
#include "interpretator.h"

TEST(Plus, PlusOperation) {
    std::stringstream in("100000 2000000 +");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 2100000);
    std::stringstream in1("0 +");
    EXPECT_THROW(inter.Reading(in1), std::runtime_error);
}

TEST(Minus, MinusOperation) {
    std::stringstream in("2000000 1000000 - 1000000 - -1 -");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 1);
}

TEST(Mul, MulOperation) {
    std::stringstream in("90 140 * 15 *");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 189000);
}

TEST(Del, DelOperation) {
    std::stringstream in("7208 53 / 10 /");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 13);
    std::stringstream in1("10 0 /");
    EXPECT_THROW(inter.Reading(in1), std::runtime_error);
    std::stringstream in2("10 /");
    EXPECT_THROW(inter.Reading(in2), std::runtime_error);
}

TEST(Mod, ModOperation) {
    std::stringstream in("7208 10 mod 5 mod");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 3);
    std::stringstream in1("0 mod");
    EXPECT_THROW(inter.Reading(in1), std::runtime_error);
    std::stringstream in2("10 0 mod");
    EXPECT_THROW(inter.Reading(in2), std::runtime_error);
}

TEST(If, IfBranch) {
    std::stringstream in("0 if 2 2 + then ;");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 0);
    std::stringstream in1("2 if 2 + then ;");
    res = inter.Reading(in1);
    EXPECT_EQ(res, 4);
    std::stringstream in2("0 if 2 2 + else 20 20 + then ;");
    res = inter.Reading(in2);
    EXPECT_EQ(res, 40);
    std::stringstream in3("2 if 2 + else 20 20 + then ;");
    res = inter.Reading(in3);
    EXPECT_EQ(res, 4);
    std::stringstream in4("0 if if then ;");
    EXPECT_THROW(inter.Reading(in4), std::runtime_error);
    std::stringstream in5("0 if then then ;");
    EXPECT_THROW(inter.Reading(in5), std::runtime_error);
    std::stringstream in6("5 if 2 + else 2 - else then ;");
    EXPECT_THROW(inter.Reading(in6), std::runtime_error);
    std::stringstream in7("5 if 0 if -10000 then ; if 7000 + else 5000 + then ; else 2 - then ;");
    res = inter.Reading(in7);
    EXPECT_EQ(res, 5000);
    std::stringstream in8("2 if 2 2 + else 2 2 - then");
    EXPECT_THROW(inter.Reading(in8), std::runtime_error);
    std::stringstream in9("2 if 2 2 + then");
    EXPECT_THROW(inter.Reading(in9), std::runtime_error);
    std::stringstream in10("0 if 2 + else 2 - else then ;");
    EXPECT_THROW(inter.Reading(in10), std::runtime_error);
    std::stringstream in11("0 if if  19 + else 19 - then ;");
    EXPECT_THROW(inter.Reading(in11), std::runtime_error);
}

TEST(Loop, LoopBranch) {
    std::stringstream in("10 5 do i loop ; + + + +");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res,35);
    std::stringstream in1("10 15 do i do loop ;");
    EXPECT_THROW(inter.Reading(in1), std::runtime_error);
    std::stringstream in2("10 15 do i i loop ");
    EXPECT_THROW(inter.Reading(in2), std::runtime_error);
    std::stringstream in3("10 5 do i i + loop ;");
    res = inter.Reading(in3);
    EXPECT_EQ(res,18);
}

TEST(Drop, DropOperation) {
    std::stringstream in(" 10 5 7 8 drop");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 7);
    std::stringstream in1("drop");
    EXPECT_THROW(inter.Reading(in1), std::runtime_error);
}

TEST(Dup, DupOperation) {
    std::stringstream in("5 dup +");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 10);
    std::stringstream in1("dup");
    EXPECT_THROW(inter.Reading(in1), std::runtime_error);
}

TEST(String, StringOperation) {
    std::stringstream in(".\" bla blabla blablabla\"");
    std::streambuf* buffer = std::cout.rdbuf();
    std::ostringstream out;
    std::cout.rdbuf(out.rdbuf());
    interpretator inter;
    int res = inter.Reading(in);
    std::cout.rdbuf(buffer);
    EXPECT_EQ(out.str(), "bla blabla blablabla\n");
}

TEST(Cr, CrOperation) {
    std::stringstream in("100 200 cr");
    std::streambuf* buffer = std::cout.rdbuf();
    std::ostringstream out;
    std::cout.rdbuf(out.rdbuf());
    interpretator inter;
    int res = inter.Reading(in);
    std::cout.rdbuf(buffer);
    EXPECT_EQ(out.str(), "200\n100\n");
}


TEST(Emit, EmitOperation) {
    std::stringstream in("65 emit");
    interpretator inter;
    unsigned char res = inter.Reading(in);
    EXPECT_EQ(res, 'A');
    std::stringstream in1("emit");
    EXPECT_THROW(inter.Reading(in1), std::runtime_error);
}

TEST(Equal, EqualOperation) {
    std::stringstream in("65 5 + 70 =");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 1);
    std::stringstream in1("70 5 - 70 =");
    res = inter.Reading(in1);
    EXPECT_EQ(res, 0);
    std::stringstream in2("100 =");
    EXPECT_THROW(inter.Reading(in2), std::runtime_error);
}

TEST(Less, LessOperation) {
    std::stringstream in("65 5 - 70 <");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 1);
    std::stringstream in1("70 0 - 70 <");
    res = inter.Reading(in1);
    EXPECT_EQ(res, 0);
    std::stringstream in2("75 10 <");
    res = inter.Reading(in2);
    EXPECT_EQ(res, 0);
    std::stringstream in3("100 <");
    EXPECT_THROW(inter.Reading(in3), std::runtime_error);
}

TEST(More, MoreOperation) {
    std::stringstream in("65 5 - 70 >");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 0);
    std::stringstream in1("70 0 - 70 >");
    res = inter.Reading(in1);
    EXPECT_EQ(res, 0);
    std::stringstream in2("75 10 >");
    res = inter.Reading(in2);
    EXPECT_EQ(res, 1);
    std::stringstream in3("100 >");
    EXPECT_THROW(inter.Reading(in3), std::runtime_error);
}

TEST(Over, OverOperation) {
    std::stringstream in("65 5 135 over");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 5);
    std::stringstream in1("28 70 over");
    res = inter.Reading(in1);
    EXPECT_EQ(res, 28);
    std::stringstream in2("100 over");
    EXPECT_THROW(inter.Reading(in2), std::runtime_error);
}

TEST(Point, PointOperation) {
    std::stringstream in("65 5 .");
    std::streambuf* buffer = std::cout.rdbuf();
    std::ostringstream out;
    std::cout.rdbuf(out.rdbuf());
    interpretator inter;
    int res = inter.Reading(in);
    std::cout.rdbuf(buffer);
    EXPECT_EQ(out.str(), "5\n");
    EXPECT_EQ(res, 65);
    std::stringstream in1(".");
    EXPECT_THROW(inter.Reading(in1), std::runtime_error);
}

TEST(Rot, RotOperation) {
    std::stringstream in("4 1 2 3 rot");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 2);
    std::stringstream in1("4 1 2 3 rot .");
    res = inter.Reading(in1);
    EXPECT_EQ(res, 1);
    std::stringstream in2("4 1 2 3 rot . .");
    res = inter.Reading(in2);
    EXPECT_EQ(res, 3);
    std::stringstream in3("1 2 rot");
    EXPECT_THROW(inter.Reading(in3), std::runtime_error);
}

TEST(Swap, SwapOperation) {
    std::stringstream in("65 5 135 swap");
    interpretator inter;
    int res = inter.Reading(in);
    EXPECT_EQ(res, 5);
    std::stringstream in1("65 135 swap");
    res = inter.Reading(in1);
    EXPECT_EQ(res, 65);
    std::stringstream in2("100 swap");
    EXPECT_THROW(inter.Reading(in2), std::runtime_error);
}
