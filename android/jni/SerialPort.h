/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class android_serialport_api_SerialPort */

#ifndef _Included_android_serialport_api_SerialPort
#define _Included_android_serialport_api_SerialPort
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_me_android_serialport_api_SerialPort
 * Method:    open
 * Signature: (Ljava/lang/String;II)Ljava/io/FileDescriptor;
 */
JNIEXPORT jobject JNICALL Java_com_me_android_1serialport_1api_SerialPort_open
  (JNIEnv *, jclass, jstring, jint, jint);

/*
 * Class:     com_me_android_serialport_api_SerialPort
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_me_android_1serialport_1api_SerialPort_close
  (JNIEnv *, jobject);

/*
 * Class:     com_androiddynamic_DynamicModule
 * Method:    check
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_me_android_1serialport_1api_SerialPort_check
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_androiddynamic_DynamicModule
 * Method:    checkSignandPackageName
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_me_android_1serialport_1api_SerialPort_checkSignandPackageName
  (JNIEnv *, jclass, jstring);

#ifdef __cplusplus
}
#endif
#endif
