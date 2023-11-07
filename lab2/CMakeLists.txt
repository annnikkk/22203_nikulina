cmake_minimum_required(VERSION 3.22)
project(lab2)

set(CMAKE_CXX_STANDARD 17)
set(SRC main.cpp source/interpretator.cpp source/operations.cpp)

add_executable(${PROJECT_NAME} ${SRC})

target_include_directories(lab2 PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/include)
