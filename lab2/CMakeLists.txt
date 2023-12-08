cmake_minimum_required(VERSION 3.22)
project(lab2)

set(CMAKE_CXX_STANDARD 17)
set(SRC source/main.cpp source/plus.cpp source/mul.cpp source/minus.cpp source/del.cpp source/mod.cpp source/more.cpp source/less.cpp
        source/equal.cpp source/dup.cpp source/drop.cpp source/point.cpp source/swap.cpp source/rot.cpp source/over.cpp
        source/emit.cpp source/cr.cpp)

add_executable(${PROJECT_NAME} ${SRC})

target_include_directories(lab2 PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/include)
