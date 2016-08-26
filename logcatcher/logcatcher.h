#define LOG_TAG "Subject_logcatcher"

#include <arpa/inet.h>
#include <errno.h>
#include <signal.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <time.h>
#include <sys/cdefs.h>
#include <sys/epoll.h>
#include <sys/eventfd.h>
#include <sys/mman.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/un.h>
#include <unistd.h>
#include <pthread.h>
#include <cutils/sockets.h>
#include <log/log.h>
#include <signal.h>
#include <processgroup/processgroup.h>
#include <private/android_filesystem_config.h>
#include <cutils/properties.h>
#include <dirent.h>
#include <sys/stat.h>
#include <sys/statfs.h>

#define ARRAYSIZE_UNIT                    128

#define EPOLLEVENTS                       3
#define SOCKET_NAME                       "logcatcher"

#define DEFAULT_TOTAL_SIZE                1073741824L //default 1G limit
#define DEFAULT_SEGMENT_SIZE              52428800L //default segment 50M
#define DISK_FREE_LIMIT                   52428800L //50M left

#define LOGCATCHER_SOCKET                 "/dev/socket/logcatcher"
#define DEFAULT_DIRECTORY                 "/sdcard/Logcatcher/"
#define CONFIG_PATH                       "/data/data/ts.subject.logcatcher/shared_prefs/ts.subject.logcatcher_preferences.xml"

#define DEFAULT_LOGCAT_DIRECTORY          "logcat"
#define DEFAULT_KMSG_DIRECTORY            "kmsg"

#define CONN_REOPENED                     1
#define CONN_NORMAL                       0

#define STR_EXTERNAL                      "Internal SDcard"
#define STR_INTERNAL                      "External SDcard"
#define STR_CACHE                         "Cache"
#define DIRECTORY_EXTERNAL                "/sdcard/Logcatcher/"
#define DIRECTORY_INTERNAL                "/storage/sdcard1/Logcatcher/"
#define DIRECTORY_CACHE                   "/cache/Logcatcher/"


