#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_pl_developit_weatherexercise_domain_WeatherInteractor_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string encodedKey = "TzRwc3RtTG8xM2pEVmhUdTF1VmxjanRwOGY3dUFNa2Q=";
    return env->NewStringUTF(encodedKey.c_str());
}