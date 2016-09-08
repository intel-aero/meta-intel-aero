/*
 * GPL LICENSE SUMMARY
 *
 * Started by Venkat Jayaraman <venkat.jayaraman@intel.com>, Copyright(c) 2016 Intel Corporation.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of either version 2 or any later version of the GNU General Public License as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * Watchdog timer application to keep Aero platform alive.
 */

#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <linux/watchdog.h>
#include <unistd.h>

#define WDTDEV			"/dev/watchdog"
#define TIMEOUT			90
#define TRUE			1
#define FALSE			0

int main( int argc, char *argv[] ) {
	int fd;
	unsigned int timeout = TIMEOUT;
	unsigned int sleeptime = 10;
	int ret = 0;

	/* parse watch-dog timer configuration */
	if (argc == 2) 
		timeout = atoi(argv[1]);

	fd = open(WDTDEV, O_RDWR | O_NONBLOCK);

	if (fd == -1) {
		printf("ERROR: Aero watchdog daemon: Open watchdog device node failed\n");
		return -1;
	}

	/* Set watchdog timeout */
	if (ioctl(fd, WDIOC_SETTIMEOUT, &timeout)) {
		printf("ERROR: Aero watchdog daemon: Set time out failed\n");
		return -1;
	}

	while (1) {
		ret = write(fd, "\0", 1);
		if (ret != 1)  {
			printf("ERROR: Keep Alive write to watchdog device file failed!\n");
			ret = -1;
			break;
		}
		sleep(sleeptime);
	}

	close(fd);
	return ret;
}

