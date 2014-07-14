LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := MemoryVision
LOCAL_SRC_FILES := MemoryVision.cpp

include $(BUILD_SHARED_LIBRARY)
