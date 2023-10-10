cmake_minimum_required(VERSION 3.23)
project(lab1)

set(CMAKE_CXX_STANDARD 17)
set(COMPILE_FLAGS "--coverage")
set(CMAKE_EXE_LINKER_FLAGS "--coverage")

add_executable(lab1 main.cpp hash_table.cpp)

include(FetchContent)
FetchContent_Declare(
        googletest
        URL https://github.com/google/googletest/archive/58d77fa8070e8cec2dc1ed015d66b454c8d78850.zip
)
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(googletest)

add_executable(lab1_test test.cpp)
target_link_libraries(lab1_test gtest_main)