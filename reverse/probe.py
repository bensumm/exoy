import socket, urllib.request, urllib.error

DEVICE_IP = '192.168.4.28'

# TCP port scan
tcp_ports = {
    80: 'HTTP', 443: 'HTTPS', 8080: 'HTTP alt', 8000: 'HTTP alt',
    8888: 'Exoy UDP (known)', 21324: 'WLED realtime', 4048: 'DDP', 6454: 'Art-Net', 5568: 'sACN/E1.31',
}
print('=== TCP scan ===')
for port, label in tcp_ports.items():
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        s.settimeout(1)
        open_ = s.connect_ex((DEVICE_IP, port)) == 0
        s.close()
        print(f'  {port:5d}  {"OPEN" if open_ else "closed":6}  {label}')
    except Exception as e:
        print(f'  {port:5d}  error   {label}: {e}')

# UDP probe for realtime protocols
print('\n=== UDP realtime probe ===')
udp_probes = {
    21324: bytes([0x02, 0, 0, 0, 0, 1, 0, 0]),  # WLED DRGB header
    4048:  bytes([0x41, 0x01, 0x0c, 0x00, 0, 0, 0, 0, 0, 0, 0, 1]),  # DDP header
}
for port, pkt in udp_probes.items():
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s.settimeout(1.5)
        s.sendto(pkt, (DEVICE_IP, port))
        data, _ = s.recvfrom(4096)
        print(f'  {port}: REPLY ({len(data)}b): {data[:32].hex()}')
        s.close()
    except socket.timeout:
        print(f'  {port}: no reply')
    except Exception as e:
        print(f'  {port}: error - {e}')

# HTTP route discovery
print('\n=== HTTP routes ===')
paths = ['/', '/api', '/info', '/state', '/json', '/json/info', '/json/state',
         '/update', '/ota', '/wifi', '/config', '/settings', '/leds', '/pixels']
for path in paths:
    try:
        url = f'http://{DEVICE_IP}{path}'
        req = urllib.request.urlopen(url, timeout=2)
        body = req.read(200).decode('utf-8', errors='replace')
        print(f'  {req.status} {path}  -> {body[:80]}')
    except urllib.error.HTTPError as e:
        print(f'  {e.code} {path}')
    except Exception:
        print(f'  --- {path}  (no response)')
