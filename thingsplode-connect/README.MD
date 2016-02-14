*Thingsplode Connect - blazingly fast communication library for anti-fragile cloud architecture.*

*Work in progress*

# Main features
Connect hides the complexity of micro-service communication for you. It implements:
* load balancer with partitioning / fail-over
* circuit breaker (fast failing connection library)
* service registry
* rpc and publish/subscribe patterns
* reliable messaging
* error handling and resend

# Speed
Some architectural choices are supporting the performance of the library:
* non-blocking, reactive message processing (using LMAX disruptor)
* Unix domain sockets for micro-services within the same Docker container (or other host-only communication)
* memory mapped files for persistence

# Message patterns

## Request/Response

The client service sends a Request to the serving service and expects a Response;

## Event

The client can signal events towards the service service (server) in a fire and forget manner.

## Command/Result

The serving service sends commands to the client and expects a command execution result.

## PushNotification

A notification is sent by the serving service to the client.