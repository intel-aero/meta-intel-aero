from SocketServer import TCPServer, StreamRequestHandler
import os
import socket
import logging
import SimpleHTTPServer

class Server(TCPServer):

    # The constant would be better initialized by a systemd module
    SYSTEMD_FIRST_SOCKET_FD = 3

    def __init__(self, server_address, handler_cls):
        # Invoke base but omit bind/listen steps (performed by systemd activation!)
        TCPServer.__init__(
            self, server_address, handler_cls, bind_and_activate=False)
        # Override socket
        self.socket = socket.fromfd(
            self.SYSTEMD_FIRST_SOCKET_FD, self.address_family, self.socket_type)

if __name__ == "__main__":
    logging.basicConfig(level=logging.INFO)
    HOST, PORT = "", 8000 # not really needed here
    Handler = SimpleHTTPServer.SimpleHTTPRequestHandler
    server = Server((HOST, PORT), Handler)
    server.server_activate()
    os.chdir('/var/http')
    server.serve_forever()

