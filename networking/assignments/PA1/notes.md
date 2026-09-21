```
(venv) cpetri@clouds:~/School/networking/assignments/PA1$ curl -v localhost:8080/index.html
* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:8080...
* connect to ::1 port 8080 from ::1 port 49038 failed: Connection refused
*   Trying 127.0.0.1:8080...
* Established connection to localhost (127.0.0.1 port 8080) from 127.0.0.1 port 35632 
* using HTTP/1.x
> GET /index.html HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.18.0
> Accept: */*
> 
* Request completely sent off
* Empty reply from server
* shutting down connection #0
curl: (52) Empty reply from server
```

```
(venv) cpetri@clouds:~/School/networking/assignments/PA1$ curl -v localhost:8080/index.html
* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:8080...
* connect to ::1 port 8080 from ::1 port 60624 failed: Connection refused
*   Trying 127.0.0.1:8080...
* Established connection to localhost (127.0.0.1 port 8080) from 127.0.0.1 port 46898 
* using HTTP/1.x
> GET /index.html HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.18.0
> Accept: */*
> 
* Request completely sent off
< HTTP/1.1 200 OK
< Content-Length: 412
< Date: Sat, 19 Sep 2026 23:08:15 GMT
< Server: PA1Server/1.0
< Content-Type: text/html
< Connection: keep-alive
< 
<!DOCTYPE html>
<html>
<head>
    <title>PA1 Index</title>
</head>
<body>
    <h1>PA1 Index Page</h1>
    <p>If you can see this page in your browser, your HTTP server is correctly
    parsing the request, finding this file, and building a valid response.</p>
    <p>Try <a href="/about.html">about.html</a> next, then
    <a href="/missing.html">missing.html</a> to check your 404 handling.</p>
</body>
</html>
* Connection #0 to host localhost:8080 left intact
```