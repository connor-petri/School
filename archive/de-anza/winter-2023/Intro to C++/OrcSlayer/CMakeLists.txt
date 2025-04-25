cmake_minimum_required(VERSION 3.24)
project(OrcSlayer)

set(CMAKE_CXX_STANDARD 20)

add_executable(OrcSlayer main.cpp entities.cpp entities.h items.cpp items.h dice.cpp dice.h)
