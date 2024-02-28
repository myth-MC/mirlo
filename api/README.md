<div align="center">
  <p>
    <h1>mirlo 🐦‍⬛</h1>
    A simple approach to <a href="https://web.archive.org/web/20220711204310/https://dinnerbone.com/blog/2012/01/13/minecraft-plugin-channels-messaging/">plugin messaging</a>
  </p>
</div>

## How to use the API
Before starting to actually use the API, you will have to add the [JitPack](https://jitpack.io/) repository and the dependency to your project.

```xml
	<repositories>
		<repository>
		    	<id>jitpack.io</id>
		    	<url>https://jitpack.io</url>
			</repository>
	</repositories>

  ...
	<dependencies>
		<dependency>
			<groupId>com.github.myth-MC.mirlo</groupId>
		    	<artifactId>mirlo-api</artifactId>
		    	<version>0.4.0b</version>
		</dependency>
	</dependencies>
```

You can access all the main API functions by using `Mirlo.get()`.

## Listening to events
**mirlo-api** provides an easy way to integrate events across different platforms by using its built-in event system. Let's start by implementing `MirloEventListener` into our `DemoListener`:

```java
import me.u8092.mirlo.api.event.MirloEvent;
import me.u8092.mirlo.api.event.MirloEventListener;

public final class DemoListener implements MirloEventListener {

  @Override
  public void handle(final MirloEvent event) {
    // This code will run every time a MirloEvent is triggered
  }
}
```

We can then check for the event we want to listen for:

```java
import me.u8092.mirlo.api.event.MirloEvent;
import me.u8092.mirlo.api.event.MirloEventListener;
import me.u8092.mirlo.api.event.impl.MirloMessageSentEvent;

public final class DemoListener implements MirloEventListener {

  @Override
  public void handle(final MirloEvent event) {
    // We want to check for MirloMessageSendEvent
    if(event instanceof MirloMessageSendEvent messageSendEvent) {
      // This code will run every time a MirloMessageSendEvent is triggered
      System.out.println("Sent a message through channel " + messageSendEvent.message().channel()
          + ": " + messageSendEvent.message().message());
    }
  }
}
```

Last but not least, we will register our `DemoListener` using the mirlo API:
```java
Mirlo.get().getEventManager().registerListener(new DemoListener());
```

## Sending a message

You can send a message by calling `MirloMessage.send()`:

```java
import me.u8092.mirlo.api.MirloMessage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        MirloMessage message = new MirloMessage(
                "test",                                 // Channel ID
                "JOIN," + event.getPlayer().getName()); // Message
        
        message.send();
    }
}

```

## Registering a channel

You can register a MirloChannel by calling `BasicMirloChannel.register()`:

```java
import java.util.List;

import me.u8092.mirlo.api.channel.BasicMirloChannel;

public final class ChannelsDemo {
    BasicMirloChannel channel = new BasicMirloChannel(
        "test",          // Channel ID
        List.of("JOIN"), // Events that will be sent through this channel
        List.of("JOIN")  // Events that will be received through this channel
    );

    private void registerChannels() {
        channel.register();
    }
}

```

And unregister it by calling `BasicMirloChannel.unregister()`:

```java
import java.util.List;

import me.u8092.mirlo.api.channel.BasicMirloChannel;

public final class ChannelsDemo {
    BasicMirloChannel channel = new BasicMirloChannel(
        "test",          // Channel ID
        List.of("JOIN"), // Events that will be sent through this channel
        List.of("JOIN")  // Events that will be received through this channel
    );

    private void unregisterChannels() {
        channel.unregister();
    }
}

```
