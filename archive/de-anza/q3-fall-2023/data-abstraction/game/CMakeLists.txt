cmake_minimum_required(VERSION 3.24)
project(game)

set(CMAKE_CXX_STANDARD 17)

add_executable(game main.cpp Entities/Entity.cpp Entities/Entity.h Entities/EntityTests.h Entities/EntityTests.cpp Entities/Player/Player.cpp Entities/Player/Player.h)
