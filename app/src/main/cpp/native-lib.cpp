#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_bravetestproject_Native_getLog(
        JNIEnv *env,
        jobject /* this */, jstring name, jstring time_stamp, jstring value) {

    const char *nx, *tx, *vx;
    nx = env->GetStringUTFChars(name, NULL);
    tx = env->GetStringUTFChars(time_stamp, NULL);
    vx = env->GetStringUTFChars(value, NULL);
    char *out = (char*)malloc(strlen(nx) + strlen(tx) + strlen(vx));
    strcpy(out, "");
    strcat(out, nx);
    strcat(out, " ");
    strcat(out, tx);
    strcat(out, " ");
    strcat(out, vx);
    jstring jstrBuf = env->NewStringUTF(out);

    env->ReleaseStringUTFChars(name, nx);
    env->ReleaseStringUTFChars(time_stamp, tx);
    env->ReleaseStringUTFChars(value, vx);

    return jstrBuf;
}