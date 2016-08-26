#include "logcatcher.h"

static int log_epollfd;
static int log_listen_fd;
static int log_worker_fd = -1;
static int log_conn_reopen = CONN_NORMAL;
static int log_conn_counter;

static uint64_t log_seg_limit = DEFAULT_SEGMENT_SIZE;
static uint64_t log_total_limit = DEFAULT_TOTAL_SIZE;
static char* log_path = NULL;

void log_epoll_delete(void)
{
    
}

void log_rw_close(void)
{
    
}

void log_data_read()
{
    
}

void log_data_write()
{
    
}

static void log_rw_handler(uint32_t events)
{
    if(events & EPOLLHUP)
    {
        ALOGE("logcatcher connected");
        if(!log_conn_reopened)
        {
            log_epoll_delete();
        }
    }
    else if(events & EPOLLIN)
    {
        int ret;
        char buf[100];
        
        ret = read(log_worker_fd,buf,100);
        if(-1 == ret)
        {
            ALOGE("read data failed , errno = %d ." ,errno);
        }
        else if(0 == ret)
        {
            ALOGE("get EOF on control data socket");
        }
        ALOGD("read data is %s ", buf);
    }
}

static void log_connect_handler(uint32_t events)
{
    struct epoll_event event;
    struct sockaddr addr;
    int addr_length;

    if(log_worker_fd > 0)
    {
        ALOGE("connect reopen at %s - %s ." ,__DATE__, __TIME__);
        log_conn_reopen = CONN_REOPENED;
        log_rw_close();
    }

    addr_length = sizeof(addr);
    log_worker_fd = accept(log_listen_fd,&addr, &addr_length);
    if(-1 == log_worker_fd)
    {
        ALOGE("accept failed , and errno = %d .", errno);
        return;
    }
    
    ALOGD("socket connected.");
    log_conn_counter++;
    
    event.events = EPOLLIN;
    event.data.ptr = (void*)log_rw_handler;
    if(-1 == epoll_ctl(log_worker_fd,EPOLL_CTL_ADD,&event))
    {
        ALOGE("epoll_ctl failed in log_connect_handler at %s - %s.",__DATE__, __TIME__);
        log_rw_close();
        return;
    }
}

void init_config(void)
{
    FILE* fp;
    char* ret;
    char one_line[ARRAYSIZE_UNIT];

    fp = fopen(CONFIG_PATH,"r");
    if(NULL == fp)
    {
        ALOGE("open configuration failed at %s, %s - %s",__LINE__,__DATE__,__TIME__);
        log_path = DEFAULT_DIRECTORY;
        log_seg_limit = 1024 * 1024 * 50 * 8;
        log_total_limit = 1024 * 1024 * 1024 * 8;
    }
    else
    {
        ALOGD("read configuration...");
        while(NULL != fgets(one_line,ARRAYSIZE_UNIT,fp))
        {
            if(NULL != strstr(one_line,STR_EXTERNAL))
            {
                log_path = DIRECTORY_EXTERNAL;
            }
            else if(NULL != strstr(one_line,STR_INTERNAL))
            {
                log_patch = DIRECTORY_INTERNAL;
            }
            else if(NULL != strstr(one_line,STR_CACHE)
            {
                log_path = DIRECTORY_CACHE;
            }
            else if(NULL != strstr(one_line,"Total Size") && NULL != (ret = strstr(one_line,">")))
            {
                int i = 0;
                ret++;
                char * number;
                //get the number
                while(ret[i] >= '0' && ret[i] <= '9')
                {
                    number[i] = ret[i];
                    i++;
                }
                number[i] = '\0';
                log_total_size = (uint64_t)atoi(number) * 1024 * 1024;
                ALOGD("log total size is %ld.", log_total_size);
            }
            else if(NULL != strstr(one_line,"Segment Size") && NULL != (ret = strstr(one_line,">")))
            {
                int i = 0;
                ret++;
                char * number;
                //get the number
                while(ret[i] >= '0' && ret[i] <= '9')
                {
                    number[i] = ret[i];
                    i++;
                }
                number[i] = '\0';
                log_segment_size = (uint64_t)atoi(number) * 1024 * 1024;
                ALOGD("log segment size is %ld.", log_total_size);
            }
        }
        fclose(fp);
    }
}

static int init_logcatcher(void)
{
    int ret;
    
    struct epoll_event event;

    init_config();
    
    log_epollfd = epoll_create(EPOLLEVENTS);
    if(-1 == log_epollfd)
    {
        ALOGE("epoll_create failed (errno = %d)",errno);
        return -1;
    }
    
    log_listen_fd = android_get_control_socket(SOCKET_NAME);
    if(-1 == log_listen_fd)
    {
        ALOGE("get logcatcher control socket failed");
        return -1;
    }
    
    ret = listen(log_listen_fd,1);
    if(ret < 0)
    {
        ALOGE("logcatcher control listen failed (errno = %d)",errno);
        return -1;
    }
    
    event.events = EPOLLIN;
    event.data.ptr = (void *)log_connect_handler;
    ret = epoll_ctl(log_epollfd, EPOLL_CTL_ADD, &event);
    if(-1 == ret)
    {
        ALOGE("epoll_ctl failed , (errno = %d).",errno);
        return -1;
    }
    log_conn_counter ++;
    return 0;
}

static void main_loop(void)
{
    for(;;)
    {
        struct epoll_event events[EPOLLEVENTS];
        int nfds;
        int i;

        log_conn_reopen = CONN_NORMAL;
        nfds = epoll_wait(log_epollfd,events,EPOLLEVENTS,-1);
        
        if(-1 == nfds)
        {
            perror("epoll_wait");
            ALOGE("epoll_wait failed, errno is %d ." , errno);
            continue;
        }
        
        for(i = 0; i < nfds; i++)
        {
            if(NULL != events[i].data.ptr)
            {
                (*(void(*)(uint32_t))events[i].data.ptr)(events[i].events);
                ALOGD("handle event in %s - %s", __DATE__,__TIME__);
            }
        }
    }
}

int main(int argc,char** argv)
{
    ALOGI("Logcatcher start at %s - %s.",__DATE__,__TIME__);

    if(!init_logcatcher())
    {
        main_loop();
    }

    ALOGI("Logcatcher exit at %s - %s.",__DATE__,__TIME__);
    return 0;
}
