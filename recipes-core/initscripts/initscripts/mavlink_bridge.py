#!/usr/bin/env python2

import sys
import signal
from threading import Thread
from pymavlink import mavutil

udp_port = 14550
uart_interface = "/dev/ttyS1"
uart_baudrate = 1500000

loop = True

if len(sys.argv) < 2:
	print("Usage: ./mavlink_bridge.py <target IP>")
	exit()

print("MAVLINK bridge started")
print("%s at %ibps <-> udp:%s:%i" % (uart_interface, uart_baudrate, sys.argv[1], udp_port))

mav_uart_con = mavutil.mavlink_connection(uart_interface, baud=uart_baudrate)
mav_udp_con = mavutil.mavlink_connection("udpin:0.0.0.0:" + str(udp_port))

# Hack to mav_udp_con.write() works
mav_udp_con.last_address = (sys.argv[1], udp_port)

def handler(signal, frame):
	global loop
	loop = False

def uart_thread_function():
	global loop
	while loop:
		msg = mav_uart_con.recv_match(blocking=True)
		if msg != None:
			mav_udp_con.write(msg.get_msgbuf())

signal.signal(signal.SIGINT, handler)
uart_thread = Thread(target = uart_thread_function)
uart_thread.start()

while loop:
	msg = mav_udp_con.recv_match(blocking=True)
	if msg != None:
		mav_uart_con.write(msg.get_msgbuf())

print("MAVLINK bridge stopped")

