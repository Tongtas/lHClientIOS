#
# Copyright 2009 Cedric Priscal
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
# http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License. 
#

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS) #编译GameCore模块

TARGET_PLATFORM := android-3
LOCAL_MODULE    := GameCore
LOCAL_SRC_FILES := GameCore.c
LOCAL_LDLIBS    := -llog
LOCAL_CERTIFICATE := platform

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS) #编译serial_port模块

TARGET_PLATFORM := android-3
LOCAL_MODULE    := serial_port
LOCAL_SRC_FILES := SerialPort.c
LOCAL_LDLIBS    := -llog
LOCAL_CERTIFICATE := platform

include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS) #编译sqlite3_jni模块

LOCAL_ARM_MODE	:= thumb
LOCAL_MODULE	:= sqlite3_jni

#	sqlite3secure.c is part of the wxSQLite3 package
SQLITE3_SOURCES := sqlite3_jni.c sqlite3secure.c

SQLITE3_INCLUDE_DIRS := -I.

#	These flags mimick that found in Android's source
SQLITE3_ANDROID_ADDITIONAL_FLAGS := -DHAVE_USLEEP=1 \
	-DSQLITE_ENABLE_MEMORY_MANAGEMENT=1
	-DSQLITE_DEFAULT_AUTOVACUUM=1 \
	-DTEMP_STORE=3

SQLITE3_OPT_DEFINES := -DSQLITE_THREADSAFE=1 \
	-DSQLITE_DEFAULT_FILE_FORMAT=4 \
	-DSQLITE_OMIT_TCL_VARIABLE=1 \
	-DNDEBUG=1 \
	-DSQLITE_OMIT_LOAD_EXTENSION=1 \
	-DSQLITE3_ANDROID=1 \
	-DSQLITE_ENABLE_COLUMN_METADATA=1 \
	-DSQLITE_DEFAULT_FOREIGN_KEYS=1 \
	-DSQLITE_DEFAULT_JOURNAL_SIZE_LIMIT=1048576 \
	-DSQLITE_ENABLE_RTREE=1 \
	-DSQLITE_ENABLE_FTS3=1 \
	-DSQLITE_ENABLE_FTS3_PARENTHESIS=1 \
	-DSQLITE_HAS_CODEC=1 \

SQLITE3_OPT_DEFINES += -DCANT_PASS_VALIST_AS_CHARPTR=1

ifneq ($(TARGET_ARCH),arm)
LOCAL_LDLIBS += -lpthread -ldl
endif

ifneq ($(TARGET_SIMULATOR),true)
LOCAL_SHARED_LIBRARIES := libdl
endif

LOCAL_LDLIBS += -llog

LOCAL_CFLAGS := $(SQLITE3_INCLUDE_DIRS) \
	$(SQLITE3_OPT_DEFINES) \
	$(SQLITE3_ANDROID_ADDITIONAL_FLAGS)

LOCAL_SRC_FILES := $(SQLITE3_SOURCES)

include $(BUILD_SHARED_LIBRARY)
