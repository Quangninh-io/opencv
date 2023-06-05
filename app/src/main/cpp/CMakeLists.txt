cmake_minimum_required(VERSION 3.22.1)

project("native-lib")

#opencv
find_package(OpenCV REQUIRED)


add_library( # Sets the name of the library.
        native-lib
        SHARED
        detect_face.cpp)

find_library( # Sets the name of the path variable.
        native-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

target_link_libraries( # Specifies the target library.
        native-lib

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib} ${OpenCV_LIBS})