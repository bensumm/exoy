import socket, json, time

DEVICE_IP = '192.168.4.28'
PORT = 8888

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.bind(('', PORT))
sock.settimeout(3)

def send(cmd):
    sock.sendto(json.dumps(cmd).encode('utf-8'), (DEVICE_IP, PORT))

def recv():
    try:
        data, _ = sock.recvfrom(4096)
        return json.loads(data)
    except socket.timeout:
        return None

# 1. Get current state
print('--- getData ---')
send({'getData': 1})
state = recv()
print(json.dumps(state, indent=2))

# 2. Test brightness
print('\n--- setBrightness 50 ---')
send({'setBrightness': 50})
time.sleep(0.3)
send({'getData': 1})
print(recv())

# 3. Check for unprompted pushes
print('\n--- Listening for unprompted pushes (3s) ---')
count = 0
deadline = time.time() + 3
while time.time() < deadline:
    r = recv()
    if r:
        count += 1
print(f'Received {count} unprompted messages')

sock.close()
