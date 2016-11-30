/******************************************************************************
 *
 *   Copyright (C) 2016  Intel Corporation. All rights reserved.
 *
 *   This program is free software;  you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; version 2 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY;  without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See
 *   the GNU General Public License for more details.
 *
 *   Author: Israel Cepeda <israel.a.cepeda.lopez@intel.com>
 *
 *****************************************************************************/
#include <linux/platform_device.h>
#include <linux/init.h>
#include <linux/module.h>
#include <linux/spi/spi.h>

#if (!defined(CONFIG_SPI_SPIDEV) && !defined(CONFIG_SPI_SPIDEV_MODULE))
	#error SPI_SPIDEV is missing
#endif

#define CAN_SPI_BUS	1
#define CAN_SPI_CS	0
#define CAN_SPI_MZ	8000000

struct spi_board_info can_device_info = {
		.modalias = "spidev",
		.max_speed_hz = CAN_SPI_MZ,
		.bus_num = CAN_SPI_BUS,
		.chip_select = CAN_SPI_CS,
};

static struct spi_device *spi_device;

static int __init can_spidev_init(void)
{
    struct spi_master *master;

    master = spi_busnum_to_master(CAN_SPI_BUS);
    if(!master){
        pr_err("SPI master driver not found...return value %d\n", master);
            return -ENODEV;
    }

    spi_device = spi_new_device(master, &can_device_info);

    if(!spi_device) {
        pr_err("CAN spidev register failed!!!\n");
        return -ENODEV;
    }
	pr_info("spidev CAN registered\n");
    return 0;
}


static void __exit can_spidev_exit(void)
{
    if(spi_device)
        spi_unregister_device(spi_device);
}

module_init(can_spidev_init);
module_exit(can_spidev_exit);

MODULE_LICENSE("GPL");
