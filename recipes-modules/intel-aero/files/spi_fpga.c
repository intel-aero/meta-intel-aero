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
 *   Author: Venkat Jayaraman <venkat.jayaraman@intel.com>
 *
 *****************************************************************************/
#include <linux/platform_device.h>
#include <linux/init.h>
#include <linux/module.h>
#include <linux/spi/spi.h>

#if (!defined(CONFIG_SPI_SPIDEV) && !defined(CONFIG_SPI_SPIDEV_MODULE))
	#error SPI_SPIDEV is missing
#endif

#define FPGA_SPI_BUS	1
#define FPGA_SPI_CS	1
#define FPGA_SPI_MZ	4000000

struct spi_board_info fpga_device_info = {
    .modalias = "spidev",
    .max_speed_hz = FPGA_SPI_MZ,
    .bus_num = FPGA_SPI_BUS,
    .chip_select = FPGA_SPI_CS,
};

static struct spi_device *spi_device;

static int __init fpga_spidev_init(void)
{
    struct spi_master *master;

    master = spi_busnum_to_master(FPGA_SPI_BUS);
    if(!master){
        pr_err("SPI master driver not found...return value %d\n", master);
            return -ENODEV;
    }

    spi_device = spi_new_device(master, &fpga_device_info);

    if(!spi_device) {
        pr_err("FPGA spidev register failed!!!\n");
        return -ENODEV;
    }
	pr_info("spidev fpga registered\n");
    return 0;
}


static void __exit fpga_spidev_exit(void)
{
    if(spi_device)
        spi_unregister_device(spi_device);
}

module_init(fpga_spidev_init);
module_exit(fpga_spidev_exit);

MODULE_LICENSE("GPL");
