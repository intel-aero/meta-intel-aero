obj-m	+= spi_imu.o
obj-m	+= spi_fpga.o

SRC := $(shell pwd)

all:
	${MAKE} -C ${KERNEL_SRC} M=${SRC}

modules_install:
	${MAKE} -C ${KERNEL_SRC} M=${SRC} modules_install

clean:
	rm -rf *.o *.ko
