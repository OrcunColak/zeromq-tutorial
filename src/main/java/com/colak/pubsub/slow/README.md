This example creates a slow subscriber. 

From ZeroMQ docs
https://zguide.zeromq.org/docs/chapter5/

> **Queue messages on the subscriber.**   
> 
 > This is much better, and it’s what ZeroMQ does by default if the network can keep up with things.   
> If anyone’s going to run out of memory and crash, it’ll be the subscriber rather than the publisher, which is fair.   
> This is perfect for “peaky” streams where a subscriber can’t keep up for a while, but can catch up when the stream slows down.  
> However, it’s no answer to a subscriber that’s simply too slow in general.
