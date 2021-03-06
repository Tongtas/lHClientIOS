/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class SQLite3_Database */

#ifndef _Included_SQLite3_Database
#define _Included_SQLite3_Database
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     SQLite3_Database
 * Method:    _open
 * Signature: (Ljava/lang/String;I)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1open
  (JNIEnv *, jobject, jstring, jint);

/*
 * Class:     SQLite3_Database
 * Method:    _open4
 * Signature: (Ljava/lang/String;ILjava/lang/String;Z)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1open4
  (JNIEnv *, jobject, jstring, jint, jstring, jboolean);

/*
 * Class:     SQLite3_Database
 * Method:    _finalize
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1finalize
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1close
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _exec
 * Signature: (Ljava/lang/String;LSQLite/Callback;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1exec__Ljava_lang_String_2LSQLite3_Callback_2
  (JNIEnv *, jobject, jstring, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _exec
 * Signature: (Ljava/lang/String;LSQLite/Callback;[Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1exec__Ljava_lang_String_2LSQLite3_Callback_2_3Ljava_lang_String_2
  (JNIEnv *, jobject, jstring, jobject, jobjectArray);

/*
 * Class:     SQLite3_Database
 * Method:    _last_insert_rowid
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_SQLite3_Database__1last_1insert_1rowid
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _interrupt
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1interrupt
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _changes
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_SQLite3_Database__1changes
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _busy_handler
 * Signature: (LSQLite/BusyHandler;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1busy_1handler
  (JNIEnv *, jobject, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _busy_timeout
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1busy_1timeout
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Database
 * Method:    _complete
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_SQLite3_Database__1complete
  (JNIEnv *, jclass, jstring);

/*
 * Class:     SQLite3_Database
 * Method:    version
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SQLite3_Database_version
  (JNIEnv *, jclass);

/*
 * Class:     SQLite3_Database
 * Method:    dbversion
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SQLite3_Database_dbversion
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _create_function
 * Signature: (Ljava/lang/String;ILSQLite/Function;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1create_1function
  (JNIEnv *, jobject, jstring, jint, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _create_aggregate
 * Signature: (Ljava/lang/String;ILSQLite/Function;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1create_1aggregate
  (JNIEnv *, jobject, jstring, jint, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _function_type
 * Signature: (Ljava/lang/String;I)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1function_1type
  (JNIEnv *, jobject, jstring, jint);

/*
 * Class:     SQLite3_Database
 * Method:    _errmsg
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SQLite3_Database__1errmsg
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    error_string
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SQLite3_Database_error_1string
  (JNIEnv *, jclass, jint);

/*
 * Class:     SQLite3_Database
 * Method:    _set_encoding
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1set_1encoding
  (JNIEnv *, jobject, jstring);

/*
 * Class:     SQLite3_Database
 * Method:    _set_authorizer
 * Signature: (LSQLite/Authorizer;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1set_1authorizer
  (JNIEnv *, jobject, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _trace
 * Signature: (LSQLite/Trace;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1trace
  (JNIEnv *, jobject, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _backup
 * Signature: (LSQLite/Backup;LSQLite/Database;Ljava/lang/String;LSQLite/Database;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1backup
  (JNIEnv *, jclass, jobject, jobject, jstring, jobject, jstring);

/*
 * Class:     SQLite3_Database
 * Method:    _profile
 * Signature: (LSQLite/Profile;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1profile
  (JNIEnv *, jobject, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _status
 * Signature: (I[IZ)I
 */
JNIEXPORT jint JNICALL Java_SQLite3_Database__1status
  (JNIEnv *, jclass, jint, jintArray, jboolean);

/*
 * Class:     SQLite3_Database
 * Method:    _db_status
 * Signature: (I[IZ)I
 */
JNIEXPORT jint JNICALL Java_SQLite3_Database__1db_1status
  (JNIEnv *, jobject, jint, jintArray, jboolean);

/*
 * Class:     SQLite3_Database
 * Method:    vm_compile
 * Signature: (Ljava/lang/String;LSQLite/Vm;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database_vm_1compile
  (JNIEnv *, jobject, jstring, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    vm_compile_args
 * Signature: (Ljava/lang/String;LSQLite/Vm;[Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database_vm_1compile_1args
  (JNIEnv *, jobject, jstring, jobject, jobjectArray);

/*
 * Class:     SQLite3_Database
 * Method:    stmt_prepare
 * Signature: (Ljava/lang/String;LSQLite/Stmt;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database_stmt_1prepare
  (JNIEnv *, jobject, jstring, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _open_blob
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JZLSQLite/Blob;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1open_1blob
  (JNIEnv *, jobject, jstring, jstring, jstring, jlong, jboolean, jobject);

/*
 * Class:     SQLite3_Database
 * Method:    _progress_handler
 * Signature: (ILSQLite/ProgressHandler;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1progress_1handler
  (JNIEnv *, jobject, jint, jobject);

#ifdef SQLITE_HAS_CODEC  
/*
 * Class:     SQLite3_Database
 * Method:    _key
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1key
  (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     SQLite3_Database
 * Method:    _rekey
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database__1rekey
  (JNIEnv *, jobject, jbyteArray);
#endif

/*
 * Class:     SQLite3_Database
 * Method:    _enable_shared_cache
 * Signature: (Z)Z
 */
JNIEXPORT jboolean JNICALL Java_SQLite3_Database__1enable_1shared_1cache
  (JNIEnv *, jclass, jboolean);

/*
 * Class:     SQLite3_Database
 * Method:    internal_init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Database_internal_1init
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class SQLite3_Vm */

#ifndef _Included_SQLite3_Vm
#define _Included_SQLite3_Vm
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     SQLite3_Vm
 * Method:    step
 * Signature: (LSQLite/Callback;)Z
 */
JNIEXPORT jboolean JNICALL Java_SQLite3_Vm_step
  (JNIEnv *, jobject, jobject);

/*
 * Class:     SQLite3_Vm
 * Method:    compile
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_SQLite3_Vm_compile
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Vm
 * Method:    stop
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Vm_stop
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Vm
 * Method:    finalize
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Vm_finalize
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Vm
 * Method:    internal_init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Vm_internal_1init
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class SQLite3_FunctionContext */

#ifndef _Included_SQLite3_FunctionContext
#define _Included_SQLite3_FunctionContext
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     SQLite3_FunctionContext
 * Method:    set_result
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_FunctionContext_set_1result__Ljava_lang_String_2
  (JNIEnv *, jobject, jstring);

/*
 * Class:     SQLite3_FunctionContext
 * Method:    set_result
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_SQLite3_FunctionContext_set_1result__I
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_FunctionContext
 * Method:    set_result
 * Signature: (D)V
 */
JNIEXPORT void JNICALL Java_SQLite3_FunctionContext_set_1result__D
  (JNIEnv *, jobject, jdouble);

/*
 * Class:     SQLite3_FunctionContext
 * Method:    set_error
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_FunctionContext_set_1error
  (JNIEnv *, jobject, jstring);

/*
 * Class:     SQLite3_FunctionContext
 * Method:    set_result
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Java_SQLite3_FunctionContext_set_1result___3B
  (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     SQLite3_FunctionContext
 * Method:    set_result_zeroblob
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_SQLite3_FunctionContext_set_1result_1zeroblob
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_FunctionContext
 * Method:    count
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_SQLite3_FunctionContext_count
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_FunctionContext
 * Method:    internal_init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_FunctionContext_internal_1init
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class SQLite3_Stmt */

#ifndef _Included_SQLite3_Stmt
#define _Included_SQLite3_Stmt
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     SQLite3_Stmt
 * Method:    prepare
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_SQLite3_Stmt_prepare
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Stmt
 * Method:    step
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_SQLite3_Stmt_step
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Stmt
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Stmt_close
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Stmt
 * Method:    reset
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Stmt_reset
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Stmt
 * Method:    clear_bindings
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Stmt_clear_1bindings
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Stmt
 * Method:    bind
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Stmt_bind__II
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    bind
 * Signature: (IJ)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Stmt_bind__IJ
  (JNIEnv *, jobject, jint, jlong);

/*
 * Class:     SQLite3_Stmt
 * Method:    bind
 * Signature: (ID)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Stmt_bind__ID
  (JNIEnv *, jobject, jint, jdouble);

/*
 * Class:     SQLite3_Stmt
 * Method:    bind
 * Signature: (I[B)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Stmt_bind__I_3B
  (JNIEnv *, jobject, jint, jbyteArray);

/*
 * Class:     SQLite3_Stmt
 * Method:    bind
 * Signature: (ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Stmt_bind__ILjava_lang_String_2
  (JNIEnv *, jobject, jint, jstring);

/*
 * Class:     SQLite3_Stmt
 * Method:    bind
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Stmt_bind__I
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    bind_zeroblob
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_SQLite3_Stmt_bind_1zeroblob
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    bind_parameter_count
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_SQLite3_Stmt_bind_1parameter_1count
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Stmt
 * Method:    bind_parameter_name
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SQLite3_Stmt_bind_1parameter_1name
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    bind_parameter_index
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_SQLite3_Stmt_bind_1parameter_1index
  (JNIEnv *, jobject, jstring);

/*
 * Class:     SQLite3_Stmt
 * Method:    column_int
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_SQLite3_Stmt_column_1int
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    column_long
 * Signature: (I)J
 */
JNIEXPORT jlong JNICALL Java_SQLite3_Stmt_column_1long
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    column_double
 * Signature: (I)D
 */
JNIEXPORT jdouble JNICALL Java_SQLite3_Stmt_column_1double
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    column_bytes
 * Signature: (I)[B
 */
JNIEXPORT jbyteArray JNICALL Java_SQLite3_Stmt_column_1bytes
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    column_string
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SQLite3_Stmt_column_1string
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    column_type
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_SQLite3_Stmt_column_1type
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    column_count
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_SQLite3_Stmt_column_1count
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Stmt
 * Method:    column_name
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SQLite3_Stmt_column_1name
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    column_table_name
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SQLite3_Stmt_column_1table_1name
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    column_database_name
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SQLite3_Stmt_column_1database_1name
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    column_decltype
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SQLite3_Stmt_column_1decltype
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    column_origin_name
 * Signature: (I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_SQLite3_Stmt_column_1origin_1name
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Stmt
 * Method:    status
 * Signature: (IZ)I
 */
JNIEXPORT jint JNICALL Java_SQLite3_Stmt_status
  (JNIEnv *, jobject, jint, jboolean);

/*
 * Class:     SQLite3_Stmt
 * Method:    finalize
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Stmt_finalize
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Stmt
 * Method:    internal_init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Stmt_internal_1init
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class SQLite3_Blob */

#ifndef _Included_SQLite3_Blob
#define _Included_SQLite3_Blob
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     SQLite3_Blob
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Blob_close
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Blob
 * Method:    write
 * Signature: ([BIII)I
 */
JNIEXPORT jint JNICALL Java_SQLite3_Blob_write
  (JNIEnv *, jobject, jbyteArray, jint, jint, jint);

/*
 * Class:     SQLite3_Blob
 * Method:    read
 * Signature: ([BIII)I
 */
JNIEXPORT jint JNICALL Java_SQLite3_Blob_read
  (JNIEnv *, jobject, jbyteArray, jint, jint, jint);

/*
 * Class:     SQLite3_Blob
 * Method:    finalize
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Blob_finalize
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Blob
 * Method:    internal_init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Blob_internal_1init
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class SQLite3_Backup */

#ifndef _Included_SQLite3_Backup
#define _Included_SQLite3_Backup
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     SQLite3_Backup
 * Method:    _finalize
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Backup__1finalize
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Backup
 * Method:    _step
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_SQLite3_Backup__1step
  (JNIEnv *, jobject, jint);

/*
 * Class:     SQLite3_Backup
 * Method:    _remaining
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_SQLite3_Backup__1remaining
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Backup
 * Method:    _pagecount
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_SQLite3_Backup__1pagecount
  (JNIEnv *, jobject);

/*
 * Class:     SQLite3_Backup
 * Method:    internal_init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_SQLite3_Backup_internal_1init
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
/* Header for class SQLite3_Profile */

#ifndef _Included_SQLite3_Profile
#define _Included_SQLite3_Profile
#ifdef __cplusplus
extern "C" {
#endif
#ifdef __cplusplus
}
#endif
#endif
