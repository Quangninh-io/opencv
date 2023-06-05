#include <jni.h>
#include <string>
#include <cstdio>
#include <iostream>
#include <opencv2/opencv.hpp>
#include <opencv2/imgproc/types_c.h>
#include "opencv2/objdetect.hpp"
#include "opencv2/highgui.hpp"
#include "opencv2/imgproc.hpp"
#include "opencv2/videoio.hpp"
#include <vector>
#include <jni.h>
#include <jni.h>

using namespace cv;


using namespace std;
extern "C"
JNIEXPORT void JNICALL
Java_com_fftools_myapplication_MainActivity_testImage(JNIEnv *env, jobject thiz, jlong input,
                                                      jlong output) {
    // Convert the input jlong values to Mat objects
    Mat& inputMat = *(Mat*)input;
    Mat& outputMat = *(Mat*)output;

    // Calculate the heights of each part
    int height = inputMat.rows;
    int part1Height = height / 4;
    int part2Height = height / 3;
    int part3Height = height - part1Height - part2Height;

    // Split the input image into three parts
    Mat part1 = inputMat(Rect(0, 0, inputMat.cols, part1Height));
    Mat part2 = inputMat(Rect(0, part1Height, inputMat.cols, part2Height));
    Mat part3 = inputMat(Rect(0, part1Height + part2Height, inputMat.cols, part3Height));

    // Adjust the height of part2 by doubling its size
    resize(part2, part2, Size(part2.cols, 2 * part2.rows));

    // Concatenate the three parts vertically into the output image
    vconcat(part1, part2, outputMat);
    vconcat(outputMat, part3, outputMat);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_fftools_myapplication_MainActivity_scaleImage(JNIEnv *env, jobject thiz, jlong input,
                                                       jlong output, jfloat scale) {
    // Convert the input jlong values to Mat objects
    Mat& inputMat = *(Mat*)input;
    Mat& outputMat = *(Mat*)output;

    // Calculate the heights of each part
    int height = inputMat.rows;
    int part1Height = height / 4;
    int part2Height = height / 3;
    int part3Height = height - part1Height - part2Height;

    // Split the input image into three parts
    Mat part1 = inputMat(Rect(0, 0, inputMat.cols, part1Height));
    Mat part2 = inputMat(Rect(0, part1Height, inputMat.cols, part2Height));
    Mat part3 = inputMat(Rect(0, part1Height + part2Height, inputMat.cols, part3Height));

    int newPart2Height = static_cast<int>(part2Height * scale);
    // Adjust the height of part2 by doubling its size
    resize(part2, part2, Size(part2.cols, newPart2Height));

    // Concatenate the three parts vertically into the output image
    vconcat(part1, part2, outputMat);
    vconcat(outputMat, part3, outputMat);
}