# Read me

See https://medium.com/@nathanbcrocker/building-a-worker-queue-using-go-and-zeromq-98425bea78b3

# Single producer and multiple workers

One of the advantages of using the PUSH-PULL pattern is its scalability. To scale the system, you can run multiple
instances of the worker program. ZeroMQ will automatically balance the load by distributing tasks to the workers in a
round-robin fashion. This means that each worker will process different tasks, and no task will be processed more than
once.