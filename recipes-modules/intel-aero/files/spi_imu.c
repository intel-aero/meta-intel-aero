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
#include <linux/module.h>
#include <linux/spi/spi.h>

#if (!defined(CONFIG_SPI_SPIDEV) && !defined(CONFIG_SPI_SPIDEV_MODULE))
	#error SPI_SPIDEV is missing
#endif

#define pr_fmt(fmt) "spidev-imu: " fmt

#define SPIDEV_SPI_BUS	3
#define SPIDEV_SPI_CS	0
#define SPIDEV_SPI_HZ	1000000

static struct spi_board_info imu_board_info __initdata = {
	.modalias	= "spidev",
	.bus_num	= SPIDEV_SPI_BUS,
	.chip_select	= SPIDEV_SPI_CS,
	.max_speed_hz	= SPIDEV_SPI_HZ,
};

static struct spi_device *dev;

static int __init spidev_imu_init(void)
{
	struct spi_master *master;
	int err = -ENODEV;

	master = spi_busnum_to_master(SPIDEV_SPI_BUS);
	if (!master)
		goto out;

	dev = spi_new_device(master, &imu_board_info);
	if (!dev)
		goto out;

	pr_info("spidev registered\n");
	err = 0;

out:
	if (err)
		pr_err("spidev register failed\n");

	return err;
}

static void __exit spidev_imu_exit(void)
{
	if (dev)
		spi_unregister_device(dev);
}

module_init(spidev_imu_init);
module_exit(spidev_imu_exit);

MODULE_LICENSE("GPL");
