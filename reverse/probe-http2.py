import urllib.request, sys

DEVICE_IP = '192.168.4.28'
out = open('C:/dev/exoy/reverse/device-http-dump.txt', 'w', encoding='utf-8')

resp = urllib.request.urlopen(f'http://{DEVICE_IP}/', timeout=5)
html = resp.read().decode('utf-8', errors='replace')
out.write('=== FULL HTML ===\n')
out.write(html)

resp2 = urllib.request.urlopen(f'http://{DEVICE_IP}/gp_data/scripts.js?=3.6.6', timeout=5)
js = resp2.read().decode('utf-8', errors='replace')
out.write('\n\n=== scripts.js ===\n')
out.write(js)
out.close()
print('Done')
