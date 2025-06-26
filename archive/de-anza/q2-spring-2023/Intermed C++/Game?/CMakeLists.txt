cmake_minimum_required(VERSION 3.24)
project(Game_)

set(CMAKE_CXX_STANDARD 20)

add_executable(Game_ main.cpp entities/Entity.cpp entities/Entity.h Dice.cpp Dice.h items/Item.cpp items/Item.h entities/Player.cpp entities/Player.h items/Inventory.cpp items/Inventory.h)
