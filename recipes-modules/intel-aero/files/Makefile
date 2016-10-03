obj-m	+= spi_imu.o

SRC := $(shell pwd)

all:
	${MAKE} -C ${KERNEL_SRC} M=${SRC}

modules_install:
	${MAKE} -C ${KERNEL_SRC} M=${SRC} modules_install

clean:
	rm -rf *.o *.ko
