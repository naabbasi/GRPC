# GRPC

``` /bin/sh
grpcurl --plaintext localhost:9090 list

grpcurl --plaintext localhost:9090 list helloworld.Greeter

grpcurl --plaintext -d '{"name": "Noman Ali"}' localhost:9090 helloworld.Greeter.SayHello
```