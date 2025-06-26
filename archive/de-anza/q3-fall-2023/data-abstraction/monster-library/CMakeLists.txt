cmake_minimum_required(VERSION 3.24)
project(monster_library)

set(CMAKE_CXX_STANDARD 17)

add_executable(monster_library main.cpp Monster/Monster.cpp Monster/Monster.h Monster/MonsterBuilder.cpp Monster/MonsterBuilder.h)

INCLUDE_DIRECTORIES(/opt/homebrew/Cellar/jsoncpp/1.9.5/include)
LINK_DIRECTORIES(/opt/homebrew/Cellar/jsoncpp/1.9.5/lib)

file(GLOB LIBRARIES "/opt/homebrew/Cellar/jsoncpp/1.9.5/lib/*.dylib")
MESSAGE("LIBRARIES: ${LIBRARIES}")

TARGET_LINK_LIBRARIES(monster_library ${LIBRARIES})
