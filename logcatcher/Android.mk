CAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES:= logcatcher.c utils.c

LOCAL_SHARED_LIBRARIES := libcutils liblog libselinux

LOCAL_MODULE := logcatcher
include $(BUILD_EXECUTABLE)

