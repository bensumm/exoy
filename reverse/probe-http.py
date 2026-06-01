import urllib.request, re

DEVICE_IP = '192.168.4.28'

# Fetch the full root page
resp = urllib.request.urlopen(f'http://{DEVICE_IP}/', timeout=5)
html = resp.read().decode('utf-8', errors='replace')
print('=== Full page HTML ===')
print(html[:3000])

# Extract any JS/CSS src references
scripts = re.findall(r'src=["\']([^"\']+)["\']', html)
links = re.findall(r'href=["\']([^"\']+)["\']', html)
print('\n=== Scripts ===', scripts)
print('=== Links ===', links)

# Fetch each JS file and dump first 500 chars
for src in scripts:
    url = f'http://{DEVICE_IP}{src}' if src.startswith('/') else f'http://{DEVICE_IP}/{src}'
    try:
        r = urllib.request.urlopen(url, timeout=5)
        body = r.read(2000).decode('utf-8', errors='replace')
        print(f'\n=== {src} ===\n{body[:500]}')
    except Exception as e:
        print(f'\n=== {src} ERROR: {e}')
