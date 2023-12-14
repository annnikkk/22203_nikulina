cmake_minimum_required(VERSION 3.22)
project(lab2)

set(CMAKE_CXX_STANDARD 17)
set(SRC source/plus.cpp source/mul.cpp source/minus.cpp source/del.cpp source/mod.cpp source/more.cpp source/less.cpp
        source/equal.cpp source/dup.cpp source/drop.cpp source/point.cpp source/swap.cpp source/rot.cpp source/over.cpp
        source/emit.cpp source/cr.cpp)

add_executable(${PROJECT_NAME} source/main.cpp ${SRC})

target_include_directories(lab2 PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/include)

include(FetchContent)
FetchContent_Declare(
        googletest
        URL https://github.com/google/googletest/archive/58d77fa8070e8cec2dc1ed015d66b454c8d78850.zip
)
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(googletest)

add_executable(lab2_test source/test.cpp ${SRC})
target_link_libraries(lab2_test gtest_main)

target_include_directories(lab2_test PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/include)