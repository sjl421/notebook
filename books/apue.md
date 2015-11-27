# apue

## fcntl.h

打开文件，返回文件描述符

```c
int open(const char *path, int oflag, ... /* mode_t mode */);
int openat(int fd, const char *path, int oflag, ... /* mode_t mode */);
int creat(const char *path, mode_t mode);
int fcntl(int fd, int cmd, ... /* int arg */);
```

## unistd.h

```c
//关闭文件描述符
int close(int fd);
//改变当前文件偏移量，影响文件读写操作，除非open时加O_APPEND
off_t lseek(int fd, off_t, int whence);
//读nbytes内容到buf从文件，返回读的字节数
ssize_t read(int fd, void *buf, size_t nbytes);
//写nbytes内容到文件从buf, 返回写的字节数
ssize_t write(int fd, const void *buf, size_t nbytes);
//原子操作，直接在指定偏移量处读写文件
ssize_t pread(int fd, void *buf, size_t nbytes, off_t offset);
ssize_t pwrite(int fd, const void *buf, size_t nbytes, off_t offset);
//文件描述符复制
int dup(int fd);
int dup2(int fd, int fd2);
//将写文件数据从块缓冲区写到磁盘，fdatasync只影响数据，fsync影响属性
int fsync(int fd);
int fdatasync(int fd);
//将块缓冲区排入写队列，返回，不等待实际写结束
void sync(void);
//以实际uid,gid进行访问权限测试, R_OK, W_OK, X_OK
int access(const char *pathname, int mode);
int faccessat(int fd, const char *pathname, int mode, int flag);
//截断文件
int truncate(const char *pathname, off_t length);
int ftruncate(int fd, off_t length);
//创建指向现有文件的链接, hard link
int link(const char *existingpath, const char *newpath);
int linkat(int efd, const char *existingpath, int nfd, const char *newpath, int flag);
//解除链接
int unlink(const char *pathname);
int unlinkat(int fd, const char *pathname, int flag);
//创建符号链接
int symlink(const char *actualpath, const char *sympath);
int symlinkat(const char *actualpath, int fd, const char *sympath);
//打开链接本身，读其内容
ssize_t readlink(const char *restrict pathname, char *restrict buf, size_t bufsize);
ssize_t readlinkat(int fd, const char *restrict pathname,
                  char *restrict buf, size_t bufsize);
//删除目录
int rmdir(const char *pathname);
//改变进程当前目录
int chdir(const char *pathname);
int fchdir(int fd);
//得到进程当前目录
char *getcwd(char *buf, size_t size);
```

## sys/ioctl.h

```c
int ioctl(int fd, int request, ...);
```

## sys/stat.h

```c
//获取文件的stat结构信息，写入buf
int stat(const char *restrict pathname, struct stat *restrict buf);
int fstat(int fd, struct stat *buf);
int lstat(const char *restrict pathname, struct stat *restrict buf);
int fstatat(int fd, const char *restrict pathname,
            struct stat *restrict buf, int flag);
//指定文件权限屏蔽字
mode_t umask(mode_t cmask);
//改变文件权限
int chmod(const char *pathname, mode_t mode);
int fchmod(int fd, mode_t mode);
int fchmodat(int fd, const char *pathname, mode_t mode, int flag);
//改变文件时间atime,mtime,ctime
int futimens(int fd, const struct timespec times[2]);
int utimesat(int fd, const char *path, const struct timespec times[2], int flag);
//创建目录
int mkdir(const char *pathname, mode_t mode);
int mkdirat(int fd, const char *pathname, mode_t mode);
```

## stdio.h

```c
int remove(const char *pathname);
//重命名文件
int rename(const char *oldname, const char *newname);
int renameat(int oldfd, const char *oldname, int newold, const char *newname);
```

## sys/time.h

```c
int utimes(const char *pathname, const struct timeval times[2]);
```

## dirent.h

```c
DIR *opendir(const char *pathname);
DIR *fopendir(int fd);
struct dirent *readdir(DIR *dp);
void rewinddir(DIR *dp);
int closedir(DIR *dp);
long telldir(DIR *dp);
void seekdir(DIR *dp, long loc);
```
